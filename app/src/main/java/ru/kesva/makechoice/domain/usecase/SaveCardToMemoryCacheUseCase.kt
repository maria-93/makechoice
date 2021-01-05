package ru.kesva.makechoice.domain.usecase

import android.util.Log
import ru.kesva.makechoice.data.model.Cache
import ru.kesva.makechoice.data.repository.Result
import ru.kesva.makechoice.domain.repository.PhotoRepository
import javax.inject.Inject

class SaveCardToMemoryCacheUseCase @Inject constructor(
    private val photoRepository: PhotoRepository,
    private val cache: Cache
) {
    suspend fun fetchData(query: String): Result<*> {
        val result = photoRepository.getCard(query)
        if (result is Result.Success) {
            val card = result.value
            cache.cardList.add(card)
        }
        return result
    }
}