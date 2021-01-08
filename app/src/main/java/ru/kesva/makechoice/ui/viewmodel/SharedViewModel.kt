package ru.kesva.makechoice.ui.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import ru.kesva.makechoice.data.model.Cache
import ru.kesva.makechoice.data.model.Event
import ru.kesva.makechoice.data.repository.Result
import ru.kesva.makechoice.domain.model.Card
import ru.kesva.makechoice.domain.usecase.SaveCardToMemoryCacheUseCase
import ru.kesva.makechoice.ui.customlayout.AnimatedGridLayout
import ru.kesva.makechoice.ui.welcomefragment.WelcomeAdapter
import javax.inject.Inject

class SharedViewModel @Inject constructor(
    private val saveCardToMemoryCacheUseCase: SaveCardToMemoryCacheUseCase,
    private val cache: Cache
) : ViewModel() {
    private val _nextButtonClicked: MutableLiveData<Event<Unit>> = MutableLiveData()
    val nextButtonClicked: LiveData<Event<Unit>> = _nextButtonClicked

    val isNextButtonVisible = ObservableBoolean()
    val isProgressBarVisible = ObservableBoolean(false)
    val isStartAnimationButtonVisible = ObservableBoolean()

    val adapter = WelcomeAdapter()

    val action: (RecyclerView.ViewHolder, Int) -> Unit = { viewHolder, _ ->
        if (adapter.cardList.isNotEmpty()) {
            adapter.cardList.removeAt(viewHolder.adapterPosition)
            adapter.notifyDataSetChanged()
        }
    }

    fun nextButtonClicked() {
        val queries = adapter.cardList.map { it.query.get()!! }
        if (!adapter.cardList.isNullOrEmpty()) {
            isNextButtonVisible.set(false)
            isProgressBarVisible.set(true)
        }
        viewModelScope.launch {
            queries.forEach { query ->
                val result = saveCardToMemoryCacheUseCase.fetchData(query)
                if (result !is Result.Success) {
                    handleError(result)
                }
            }
        }.invokeOnCompletion {
            isProgressBarVisible.set(false)
            _nextButtonClicked.value = Event(Unit)
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

    fun getCardListFromCache(): List<Card> {
        return cache.cardList
    }

    fun clearCardListFromCache() {
        cache.cardList.clear()
    }

    fun startAnimation(animatedGridLayout: AnimatedGridLayout) {
        animatedGridLayout.startViewAnimation(0)
        isStartAnimationButtonVisible.set(false)
    }


}