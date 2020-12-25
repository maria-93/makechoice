package ru.kesva.makechoice.ui.viewmodel

import android.app.Activity
import android.view.View
import android.widget.Button
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import ru.kesva.makechoice.data.model.Cache
import ru.kesva.makechoice.data.model.EditTextItem
import ru.kesva.makechoice.data.model.Event
import ru.kesva.makechoice.data.repository.Result
import ru.kesva.makechoice.domain.model.Card
import ru.kesva.makechoice.domain.usecase.MakeChoiceUseCase
import ru.kesva.makechoice.domain.usecase.SaveCardToMemoryCacheUseCase
import ru.kesva.makechoice.extensions.runWhenReady
import ru.kesva.makechoice.extensions.showKeyboard
import ru.kesva.makechoice.ui.customlayout.AnimatedGridLayout
import ru.kesva.makechoice.ui.welcomefragment.WelcomeAdapter
import javax.inject.Inject

class SharedViewModel @Inject constructor(
    private val makeChoiceUseCase: MakeChoiceUseCase,
    private val saveCardToMemoryCacheUseCase: SaveCardToMemoryCacheUseCase,
    private val cache: Cache
) : ViewModel() {
    private val _nextButtonClicked: MutableLiveData<Event<Unit>> = MutableLiveData()
    val nextButtonClicked: LiveData<Event<Unit>> = _nextButtonClicked

    val adapter = WelcomeAdapter()

    fun nextButtonClicked() {
        val queries = adapter.cardList.map { it.query.get()!! }
        viewModelScope.launch {
            queries.forEach { query ->
                val result = saveCardToMemoryCacheUseCase.fetchData(query)
                if (result !is Result.Success) {
                    handleError(result)
                }
            }
        }.invokeOnCompletion {
            _nextButtonClicked.value = Event(Unit)
        }
    }

    val action: (RecyclerView.ViewHolder, Int) -> Unit = { viewHolder, _ ->
        if (adapter.cardList.isNotEmpty()) {
            adapter.cardList.removeAt(viewHolder.adapterPosition)
            adapter.notifyDataSetChanged()
        }
    }

    fun getCardListFromCache(): List<Card> {
        return cache.cardList
    }

    fun startAnimation(animatedGridLayout: AnimatedGridLayout, startAnimationButton: Button) {
        animatedGridLayout.startViewAnimation(0)
        startAnimationButton.visibility = View.GONE
    }

    private fun <T> handleError(result: Result<T>) {
        when (result) {
            is Result.NetworkError -> {
            }
            is Result.HttpError -> {
            }
        }
    }

    fun addButtonClicked(recyclerView: RecyclerView, activity: Activity) {
        recyclerView.runWhenReady {
            val holder = recyclerView.findViewHolderForAdapterPosition(
                adapter.itemCount - 1
            ) as WelcomeAdapter.WelcomeViewHolder
            holder.binding.textField.requestFocus()
            activity.showKeyboard()
        }
        val editTextItem = EditTextItem()
        adapter.cardList.add(editTextItem)
        adapter.notifyItemInserted(adapter.cardList.lastIndex)

    }
}