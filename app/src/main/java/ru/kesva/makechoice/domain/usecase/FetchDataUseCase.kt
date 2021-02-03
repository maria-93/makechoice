package ru.kesva.makechoice.domain.usecase

import kotlinx.coroutines.*
import kotlinx.coroutines.selects.select
import ru.kesva.makechoice.data.model.LocalCache
import ru.kesva.makechoice.data.repository.Result
import javax.inject.Inject

class FetchDataUseCase @Inject constructor(
    private val localCache: LocalCache,
    private val saveUriToLocalCacheUseCase: SaveUriToLocalCacheUseCase,
) {
    suspend operator fun invoke(): Result<*> = coroutineScope {
        val deferredResults = mutableListOf<Deferred<Result<*>>>()
        localCache.getQueriesFromMap().forEach { query ->
            if (localCache.isNeedToFetch(query)) {
                val deferred = async {
                    saveUriToLocalCacheUseCase.fetchUriOnRequest(query)
                }
                deferredResults.add(deferred)
            }
        }

        while (deferredResults.isNotEmpty()) {
            var indexToDelete = -1
            val firstCompletedResult = select<Result<*>> {
                deferredResults.forEachIndexed {index, deferred ->
                    deferred.onAwait {
                        indexToDelete = index
                        it
                    }
                }
            }
            if (firstCompletedResult !is Result.Success) {
                deferredResults.forEach { it.cancel() }
                return@coroutineScope firstCompletedResult
            }
                deferredResults.removeAt(indexToDelete)
        }
        Result.Success(Unit)
    }
}