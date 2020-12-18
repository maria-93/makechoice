package ru.kesva.makechoice.ui.welcomefragment

import android.content.Context
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import ru.kesva.makechoice.data.model.EditTextItem
import ru.kesva.makechoice.databinding.LayoutForRvAddVariantBinding
import javax.inject.Inject

class WelcomeAdapter @Inject constructor(
    private val welcomeAdapterClickHandler: WelcomeAdapterClickHandler
) : RecyclerView.Adapter<WelcomeAdapter.WelcomeViewHolder>() {

    val cardList: MutableList<EditTextItem> = mutableListOf()

    fun addNewEditText() {
        val editTextItem = EditTextItem()
        cardList.add(editTextItem)
        notifyDataSetChanged()
    }

    fun removeEditText() {
        if (cardList.isNotEmpty()) {
            cardList.removeLast()
            notifyDataSetChanged()
        }
    }

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
        holder.bind(position, welcomeAdapterClickHandler)
    }

    override fun getItemCount(): Int = cardList.size


    inner class WelcomeViewHolder(private val binding: LayoutForRvAddVariantBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            position: Int,
            welcomeAdapterClickHandler: WelcomeAdapterClickHandler
        ) {
            binding.editTextItem = cardList[position]
            binding.welcomeAdapterClickHandler = welcomeAdapterClickHandler
        }

    }
}

interface WelcomeAdapterClickHandler {
    fun nextButtonClicked(recyclerView: RecyclerView)
}