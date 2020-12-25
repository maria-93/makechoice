package ru.kesva.makechoice.ui.welcomefragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.kesva.makechoice.data.model.EditTextItem
import ru.kesva.makechoice.databinding.LayoutForRvAddVariantBinding
import javax.inject.Inject

class WelcomeAdapter @Inject constructor(
) : RecyclerView.Adapter<WelcomeAdapter.WelcomeViewHolder>() {

    val cardList: MutableList<EditTextItem> = mutableListOf()

    fun removeAllEditTexts() {
        cardList.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WelcomeViewHolder =
        WelcomeViewHolder(
            LayoutForRvAddVariantBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: WelcomeViewHolder, position: Int) {
        holder.binding.editTextItem = cardList[position]
    }

    override fun getItemCount(): Int = cardList.size

    inner class WelcomeViewHolder(val binding: LayoutForRvAddVariantBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }
}
