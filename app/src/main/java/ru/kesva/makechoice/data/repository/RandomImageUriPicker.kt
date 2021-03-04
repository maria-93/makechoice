package ru.kesva.makechoice.data.repository

import ru.kesva.makechoice.domain.model.Image
import ru.kesva.makechoice.domain.model.Items
import ru.kesva.makechoice.domain.model.Photo
import kotlin.random.Random

class RandomImageUriPicker {

    fun getRandomImageUriCustomSearchApi(itemsList: List<Items>): String {
        val randomIndex = Random.nextInt(itemsList.size)
        val item = itemsList[randomIndex]
        val image: Image = item.image
        return image.imageUri
    }

    fun getRandomImageUriPexelsApi(photos: List<Photo>): String {
        val randomIndex = Random.nextInt(photos.size)
        val photo = photos[randomIndex]
        val imageSource = photo.source
        return imageSource.imageUri
    }

}
