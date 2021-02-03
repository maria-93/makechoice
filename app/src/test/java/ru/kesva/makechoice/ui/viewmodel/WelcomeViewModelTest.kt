package ru.kesva.makechoice.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import ru.kesva.makechoice.TestCoroutineRule
import ru.kesva.makechoice.data.model.*
import ru.kesva.makechoice.data.repository.Result
import ru.kesva.makechoice.domain.usecase.DeleteCardFromMemoryCacheUseCase
import ru.kesva.makechoice.domain.usecase.FetchDataUseCase
import ru.kesva.makechoice.domain.usecase.PopulateCardListUseCase
import ru.kesva.makechoice.domain.usecase.SaveUriToLocalCacheUseCase

@ExperimentalCoroutinesApi
class WelcomeViewModelTest {
    private lateinit var viewModel: WelcomeViewModel
    private lateinit var saveUriToLocalCacheUseCase: SaveUriToLocalCacheUseCase
    private lateinit var deleteCardFromMemoryCacheUseCase: DeleteCardFromMemoryCacheUseCase
    private lateinit var fetchDataUseCase: FetchDataUseCase
    private lateinit var populateCardListUseCase: PopulateCardListUseCase
    private lateinit var cache: Cache
    private lateinit var localCache: LocalCache

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Before
    fun setUp() {
        cache = Cache(mutableListOf())
        saveUriToLocalCacheUseCase = mock {
            onBlocking { this.fetchUriOnRequest(Mockito.anyString()) }.doReturn(
                Result.Success("some uri")
                )
        }
        deleteCardFromMemoryCacheUseCase = mock()
        fetchDataUseCase = mock()
        populateCardListUseCase = mock()
        localCache = mock()
    }

    private fun createViewModel() =
        WelcomeViewModel(
            deleteCardFromMemoryCacheUseCase,
            fetchDataUseCase,
            populateCardListUseCase,
            cache,
            localCache
        )

    @Test
    fun nextButtonClicked_setsNextButtonClickedEvent() {
        // Given a fresh WelcomeViewModel
        viewModel = createViewModel()

        viewModel.adapter.editTextList.add(EditTextItem())
        viewModel.adapter.editTextList.add(EditTextItem())
        viewModel.adapter.editTextList.add(EditTextItem())

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
}