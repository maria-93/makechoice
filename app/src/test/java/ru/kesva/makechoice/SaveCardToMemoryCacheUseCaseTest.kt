package ru.kesva.makechoice

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import ru.kesva.makechoice.data.model.Cache
import ru.kesva.makechoice.data.repository.Result
import ru.kesva.makechoice.domain.model.Card
import ru.kesva.makechoice.domain.usecase.SaveCardToMemoryCacheUseCase
@ExperimentalCoroutinesApi
class SaveCardToMemoryCacheUseCaseTest {
    private lateinit var fakeRepository: FakeRepository
    private lateinit var useCase: SaveCardToMemoryCacheUseCase
    private lateinit var cache: Cache

    @Before
    fun create() {
        fakeRepository = FakeRepository()
        cache = Cache(mutableListOf())
        useCase = SaveCardToMemoryCacheUseCase(fakeRepository, cache)
    }


    @Test
    fun getCardFromRepoAndSaveItToCache_cardSavedSuccessfully() {
        fakeRepository.resultToReturn = Result.Success::class
        val query = "Кошка"
        runBlockingTest {
            useCase.fetchData(query)
        }
        assertThat(cache.cardList.size).isEqualTo(1)

        val card = cache.cardList[0]
        assertThat(card.query).isEqualTo(query)
        assertThat(card.uri).isEqualTo(fakeRepository.uri)
    }

    @Test
    fun getCardFromRepoAndSaveItToCache_cardSavingFailedCauseHttpError() {
        fakeRepository.resultToReturn = Result.HttpError::class
        val query = "Кот"
        runBlockingTest {
            useCase.fetchData(query)
        }
        assertThat(cache.cardList.size).isEqualTo(0)
    }

    @Test
    fun getCardFromRepoAndSaveItToCache_cardSavingFailedCauseNetworkError() {
        fakeRepository.resultToReturn = Result.NetworkError::class
        val query = "Котенок"
        runBlockingTest {
            useCase.fetchData(query)
        }
        assertThat(cache.cardList.size).isEqualTo(0)
    }

}