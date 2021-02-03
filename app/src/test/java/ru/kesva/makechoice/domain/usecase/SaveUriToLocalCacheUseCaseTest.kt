package ru.kesva.makechoice.domain.usecase

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import ru.kesva.makechoice.data.repository.FakeRepository
import ru.kesva.makechoice.data.model.LocalCache
import ru.kesva.makechoice.data.model.LocalCacheImp
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
        assertThat(localCache.getQueriesFromMap()).isEqualTo(1)

        /*assertThat(card.query).isEqualTo(query)
        assertThat(card.uri).isEqualTo(fakeRepository.uri)*/
    }

    @Test
    fun getCardFromRepoAndSaveItToCache_savingUriFailedCauseHttpError() {
        fakeRepository.resultToReturn = Result.HttpError::class
        val query = "Кот"
        runBlockingTest {
            useCase.fetchUriOnRequest(query)
        }
        assertThat(localCache.getQueriesFromMap()).isEqualTo(0)
    }

    @Test
    fun getCardFromRepoAndSaveItToCache_savingUriFailedCauseNetworkError() {
        fakeRepository.resultToReturn = Result.NetworkError::class
        val query = "Котенок"
        runBlockingTest {
            useCase.fetchUriOnRequest(query)
        }
        assertThat(localCache.getQueriesFromMap()).isEqualTo(0)
    }

}