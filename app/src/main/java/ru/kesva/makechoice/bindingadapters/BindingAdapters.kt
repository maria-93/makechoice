package ru.kesva.makechoice.bindingadapters

import android.graphics.Canvas
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.fragment.app.findFragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.kesva.makechoice.R
import ru.kesva.makechoice.data.model.EditTextItem
import ru.kesva.makechoice.domain.model.Card
import ru.kesva.makechoice.extensions.runWhenReady
import ru.kesva.makechoice.extensions.showKeyboard
import ru.kesva.makechoice.ui.customlayout.AnimatedGridLayout
import ru.kesva.makechoice.ui.viewmodel.SharedViewModel
import ru.kesva.makechoice.ui.welcomefragment.WelcomeAdapter
import ru.kesva.makechoice.ui.welcomefragment.WelcomeFragment


@BindingAdapter("app:loadImage")
fun loadImage(imageView: ImageView, imageUri: String) {
    Log.d("asdf", "loadImage: $imageUri")
    Glide
        .with(imageView)
        .load(imageUri)
        .centerCrop()
        .into(imageView)
}

@BindingAdapter("app:setCardListFromCache")
fun retrieveCardListFromCache(animatedGridLayout: AnimatedGridLayout, list: List<Card>) {
    Log.d("Test", "retrieveCardListFromCache: ")
    animatedGridLayout.setCardList(list)
}

@BindingAdapter("app:setDismissHelper")
fun RecyclerView.setDismissHelper(action: (RecyclerView.ViewHolder, Int) -> Unit) {
    val dismissHelper = ItemTouchHelper(object :
        ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.START or ItemTouchHelper.END) {

        override fun isLongPressDragEnabled(): Boolean = false

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = false

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            action(viewHolder, direction)
            Toast.makeText(context, "Вариант удален", Toast.LENGTH_SHORT).show()
        }

        override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
            if (viewHolder != null) {
                val foregroundView: View = viewHolder.itemView.findViewById(R.id.textField)
                getDefaultUIUtil().onSelected(foregroundView)
            }
        }

        override fun onChildDrawOver(
            c: Canvas, recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float,
            actionState: Int, isCurrentlyActive: Boolean
        ) {
            val foregroundView: View = viewHolder.itemView.findViewById(R.id.textField)
            getDefaultUIUtil().onDrawOver(
                c, recyclerView, foregroundView, dX, dY,
                actionState, isCurrentlyActive
            )
        }

        override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
            val foregroundView: View = viewHolder.itemView.findViewById(R.id.textField)
            getDefaultUIUtil().clearView(foregroundView)
        }

        override fun onChildDraw(
            c: Canvas, recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float,
            actionState: Int, isCurrentlyActive: Boolean
        ) {
            val foregroundView: View = viewHolder.itemView.findViewById(R.id.textField)
            val deleteIconEnd: View = viewHolder.itemView.findViewById(R.id.deleteIconRight)
            val deleteIconStart: View = viewHolder.itemView.findViewById(R.id.deleteIconLeft)
            if (dX > 0) {
                deleteIconEnd.visibility = View.INVISIBLE
                deleteIconStart.visibility = View.VISIBLE
            } else {
                deleteIconEnd.visibility = View.VISIBLE
                deleteIconStart.visibility = View.INVISIBLE
            }
            getDefaultUIUtil().onDraw(
                c, recyclerView, foregroundView, dX, dY,
                actionState, isCurrentlyActive
            )
        }
    })
    dismissHelper.attachToRecyclerView(this)
}

@BindingAdapter("app:recyclerView", "app:viewModel")
fun FloatingActionButton.bindOnFabClickAction(recyclerView: RecyclerView, viewModel: SharedViewModel) {
    setOnClickListener {
        recyclerView.runWhenReady {
            val holder = recyclerView.findViewHolderForAdapterPosition(
                viewModel.adapter.itemCount - 1
            ) as WelcomeAdapter.WelcomeViewHolder
            holder.binding.textField.requestFocus()
            val activity = findFragment<WelcomeFragment>().requireActivity()
            activity.showKeyboard()
        }
        val editTextItem = EditTextItem()
        viewModel.adapter.cardList.add(editTextItem)
        viewModel.adapter.notifyItemInserted(viewModel.adapter.cardList.lastIndex)
    }
}



