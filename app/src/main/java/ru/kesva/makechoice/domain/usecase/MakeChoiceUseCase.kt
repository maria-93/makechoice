package ru.kesva.makechoice.domain.usecase

import ru.kesva.makechoice.data.model.Cache
import javax.inject.Inject
import kotlin.random.Random

class MakeChoiceUseCase @Inject constructor(
    private val cache: Cache
) {
    fun makeChoice(): Int {
        val cardList = cache.cardList
        return Random.nextInt(0, cardList.size)
    }
}