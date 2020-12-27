package ru.kesva.makechoice.ui.viewmodel

import android.view.View
import android.widget.Button
import android.widget.ProgressBar
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

    val adapter = WelcomeAdapter()

    val action: (RecyclerView.ViewHolder, Int) -> Unit = { viewHolder, _ ->
        if (adapter.cardList.isNotEmpty()) {
            adapter.cardList.removeAt(viewHolder.adapterPosition)
            adapter.notifyDataSetChanged()
        }
    }

    fun nextButtonClicked(fartherButton: Button, progressBar: ProgressBar) {
        if (adapter.cardList.isNotEmpty()) {
            fartherButton.visibility = View.GONE
            progressBar.visibility = ProgressBar.VISIBLE
        }
        val queries = adapter.cardList.map { it.query.get()!! }
        viewModelScope.launch {
            queries.forEach { query ->
                val result = saveCardToMemoryCacheUseCase.fetchData(query)
                if (result !is Result.Success) {
                    handleError(result)
                }
            }
        }.invokeOnCompletion {
            progressBar.visibility = ProgressBar.INVISIBLE
            _nextButtonClicked.value = Event(Unit)
        }
    }

    private fun <T> handleError(result: Result<T>) {
        when (result) {
            is Result.NetworkError -> {
            }
            is Result.HttpError -> {
            }
        }
    }

    fun getCardListFromCache(): List<Card> {
        return cache.cardList
    }

    fun startAnimation(animatedGridLayout: AnimatedGridLayout, startAnimationButton: Button) {
        animatedGridLayout.startViewAnimation(0)
        startAnimationButton.visibility = View.GONE
    }




}