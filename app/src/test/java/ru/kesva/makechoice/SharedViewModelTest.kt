package ru.kesva.makechoice

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import ru.kesva.makechoice.data.model.Cache
import ru.kesva.makechoice.data.model.Event
import ru.kesva.makechoice.data.repository.Result
import ru.kesva.makechoice.domain.model.Card
import ru.kesva.makechoice.domain.usecase.SaveCardToMemoryCacheUseCase
import ru.kesva.makechoice.ui.viewmodel.SharedViewModel

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class SharedViewModelTest {
    private lateinit var viewModel: SharedViewModel
    private lateinit var saveCardToMemoryCacheUseCase: SaveCardToMemoryCacheUseCase
    private lateinit var cache: Cache

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Before
    fun setUp() {
        cache = Cache(mutableListOf())
        saveCardToMemoryCacheUseCase = mock {
            onBlocking { this.fetchData(Mockito.anyString()) }.doReturn(
                Result.Success(
                    Card(
                        "1",
                        "2"
                    )
                )
            )
        }
    }

    private fun createViewModel() = SharedViewModel(saveCardToMemoryCacheUseCase, cache)

    @Test
    fun nextButtonClicked_setsNextButtonClickedEvent() {
        // Given a fresh SharedViewModel
        viewModel = createViewModel()

        val observer = Observer<Event<Unit>> {}

        try {
            viewModel.nextButtonClicked.observeForever(observer)
            // When clicking the next button
            viewModel.nextButtonClicked()
            // Then the nextButtonClicked event is triggered
            val value = viewModel.nextButtonClicked.value
            assertThat(value?.getContentIfNotHandled()).isNotNull()

        } finally {
            viewModel.nextButtonClicked.removeObserver(observer)
        }


    }

    @Test
    fun nextButtonClicked_cardListFromCacheIsNullOrEmpty_showToastMessage() {

    }

    @Test
    fun nextButtonClicked_cardListFromCacheIsNullOrEmpty_setsNextButtonVisible() {

    }

    @Test
    fun nextButtonClicked_cardListFromCacheIsNullOrEmpty_setsProgressBarInvisible() {

    }
}