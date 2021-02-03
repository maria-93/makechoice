package ru.kesva.makechoice.ui.welcomefragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.kesva.makechoice.data.model.EditTextItem
import ru.kesva.makechoice.databinding.LayoutForRvAddVariantBinding
import javax.inject.Inject

class WelcomeAdapter @Inject constructor(
) : RecyclerView.Adapter<WelcomeAdapter.WelcomeViewHolder>() {

    val editTextList: MutableList<EditTextItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WelcomeViewHolder =
        WelcomeViewHolder(
            LayoutForRvAddVariantBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: WelcomeViewHolder, position: Int) {
        holder.binding.editTextItem = editTextList[position]
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int = editTextList.size

    inner class WelcomeViewHolder(val binding: LayoutForRvAddVariantBinding) :
        RecyclerView.ViewHolder(binding.root)
}
