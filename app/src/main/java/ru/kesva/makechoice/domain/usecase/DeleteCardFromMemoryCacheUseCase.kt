package ru.kesva.makechoice.domain.usecase

import ru.kesva.makechoice.data.model.Cache
import javax.inject.Inject

class DeleteCardFromMemoryCacheUseCase @Inject constructor(private val cache: Cache) {
    operator fun invoke(position: Int) {
        cache.cardList.removeAt(position)
    }
}