package ru.kesva.makechoice.bindingadapters

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import ru.kesva.makechoice.domain.model.Card
import ru.kesva.makechoice.ui.customlayout.AnimatedGridLayout


@BindingAdapter("loadImage")
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
