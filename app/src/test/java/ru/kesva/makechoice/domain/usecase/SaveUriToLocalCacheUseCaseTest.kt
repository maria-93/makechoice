package ru.kesva.makechoice.domain.usecase

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import ru.kesva.makechoice.data.model.LocalCache
import ru.kesva.makechoice.data.model.LocalCacheImp
import ru.kesva.makechoice.data.repository.FakeRepository
import ru.kesva.makechoice.data.repository.Result

@ExperimentalCoroutinesApi
class SaveUriToLocalCacheUseCaseTest {
    private lateinit var fakeRepository: FakeRepository
    private lateinit var useCase: SaveUriToLocalCacheUseCase
    private lateinit var localCache: LocalCache

    @Before
    fun create() {
        fakeRepository = FakeRepository()
        localCache = LocalCacheImp()
        useCase = SaveUriToLocalCacheUseCase(fakeRepository, localCache)
    }


    @Test
    fun getUriFromRepoAndSaveItToLocalCache_uriSavedSuccessfully() {
        fakeRepository.resultToReturn = Result.Success::class
        val query = "Кошка"
        runBlockingTest {
            useCase.fetchUriOnRequest(query)
        }
        assertThat(localCache.getQueriesFromMap().size).isEqualTo(1)
        assertThat(localCache.getQueriesFromMap()[0]).isEqualTo("Кошка")
        assertThat(localCache.getLinkForQuery("Кошка")).isNotNull()
    }

    @Test
    fun getUriFromRepoAndSaveItToLocalCache_savingUriFailedCauseHttpError() {
        fakeRepository.resultToReturn = Result.HttpError::class
        val query = "Кот"
        runBlockingTest {
            useCase.fetchUriOnRequest(query)
        }
        assertThat(localCache.getQueriesFromMap().size).isEqualTo(0)
    }

    @Test
    fun getUriFromRepoAndSaveItToLocalCache_savingUriFailedCauseNetworkError() {
        fakeRepository.resultToReturn = Result.NetworkError::class
        val query = "Кот"
        runBlockingTest {
            useCase.fetchUriOnRequest(query)
        }
        assertThat(localCache.getQueriesFromMap().size).isEqualTo(0)
    }

}