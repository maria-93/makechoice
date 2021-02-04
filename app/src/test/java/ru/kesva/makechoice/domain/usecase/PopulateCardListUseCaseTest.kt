package ru.kesva.makechoice.domain.usecase

import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import ru.kesva.makechoice.data.model.Cache
import ru.kesva.makechoice.data.model.LocalCache
import ru.kesva.makechoice.data.model.LocalCacheImp

class PopulateCardListUseCaseTest {
    private lateinit var useCase: PopulateCardListUseCase
    private lateinit var cache: Cache
    private lateinit var localCache: LocalCache
    private lateinit var userQueries: MutableList<String>

    @Before
    fun setUp() {
        userQueries = mutableListOf()
        userQueries.add("Кот")
        userQueries.add("кошка")
        userQueries.add("кот")
        cache = Cache(mutableListOf())
        localCache = LocalCacheImp()
        useCase = PopulateCardListUseCase(cache, localCache)
    }

    @After
    fun tearDown() {

    }

    @Test
    fun populateCardList_cardsSavedSuccessfully() {
        useCase(userQueries)

        assertThat(cache.cardList.size).isEqualTo(3)

        assertThat(cache.cardList[0].query).isEqualTo("Кот")
        assertThat(cache.cardList[1].query).isEqualTo("кошка")
        assertThat(cache.cardList[2].query).isEqualTo("кот")

    }
}