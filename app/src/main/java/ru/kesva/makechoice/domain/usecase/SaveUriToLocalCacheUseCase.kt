package ru.kesva.makechoice.domain.usecase

import ru.kesva.makechoice.data.model.LocalCache
import ru.kesva.makechoice.data.repository.Result
import ru.kesva.makechoice.domain.repository.Repository
import javax.inject.Inject

class SaveUriToLocalCacheUseCase @Inject constructor(
    private val repository: Repository,
    private val localCache: LocalCache
) {
    suspend fun fetchUriOnRequest(query: String): Result<*> {
        val result = repository.fetchUriOnRequest(query, "#b51630")
        if (result is Result.Success) {
            val uri = result.value
            localCache.saveFetchedUriToMap(query, uri)
        }
        return result
    }
}