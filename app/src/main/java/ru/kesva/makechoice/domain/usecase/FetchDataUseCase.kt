package ru.kesva.makechoice.domain.usecase

import ru.kesva.makechoice.data.model.LocalCache
import ru.kesva.makechoice.data.repository.Result
import javax.inject.Inject

class FetchDataUseCase @Inject constructor(
    private val localCache: LocalCache,
    private val saveUriToLocalCacheUseCase: SaveUriToLocalCacheUseCase,
) {
    suspend operator fun invoke(queryList: List<String>) {
        queryList.forEach { query ->
            if (localCache.isNeedToFetch(query)) {
                //do nothing
            } else {
                val result = saveUriToLocalCacheUseCase.fetchUriOnRequest(query)
                if (result !is Result.Success) {
                    handleError(result)
                }
            }
        }
    }
    private fun <T> handleError(result: Result<T>) {
        when (result) {
            is Result.NetworkError -> {
            }
            is Result.HttpError -> {
            }
            else -> Result.UndefinedError
        }
    }
}