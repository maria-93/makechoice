package ru.kesva.makechoice.data.repository

import ru.kesva.makechoice.domain.model.Image
import ru.kesva.makechoice.domain.model.Items
import kotlin.random.Random

class RandomImageUriPicker {

    fun getRandomImageUri(itemsList: List<Items>): String {
        val randomIndex = Random.nextInt(itemsList.size)
        val item = itemsList[randomIndex]
        val image: Image = item.image
        return image.imageUri
    }
}