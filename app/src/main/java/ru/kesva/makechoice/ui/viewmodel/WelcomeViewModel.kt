package ru.kesva.makechoice.ui.viewmodel

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import okhttp3.internal.filterList
import ru.kesva.makechoice.data.model.Cache
import ru.kesva.makechoice.data.model.Event
import ru.kesva.makechoice.data.model.LocalCache
import ru.kesva.makechoice.data.repository.Result
import ru.kesva.makechoice.domain.usecase.DeleteCardFromMemoryCacheUseCase
import ru.kesva.makechoice.domain.usecase.FetchDataUseCase
import ru.kesva.makechoice.domain.usecase.PopulateCardListUseCase
import ru.kesva.makechoice.ui.welcomefragment.WelcomeAdapter
import javax.inject.Inject

class WelcomeViewModel @Inject constructor(
    private val deleteCardFromMemoryCacheUseCase: DeleteCardFromMemoryCacheUseCase,
    private val fetchDataUseCase: FetchDataUseCase,
    private val populateCardListUseCase: PopulateCardListUseCase,
    private val cache: Cache,
    private val localCache: LocalCache
) : ViewModel() {
    private val _nextButtonClicked: MutableLiveData<Event<Unit>> = MutableLiveData()
    val nextButtonClicked: LiveData<Event<Unit>> = _nextButtonClicked

    private val _toastLiveDataCardListNullOrEmpty: MutableLiveData<Event<Unit>> = MutableLiveData()
    val toastLiveDataCardListNullOrEmpty: LiveData<Event<Unit>> = _toastLiveDataCardListNullOrEmpty

    private val _toastLiveDataCardListLessThanTwo: MutableLiveData<Event<Unit>> = MutableLiveData()
    val toastLiveDataCardListLessThanTwo: LiveData<Event<Unit>> = _toastLiveDataCardListLessThanTwo

    private val _toastNetworkError = MutableLiveData<Event<Unit>>()
    val toastNetworkError: LiveData<Event<Unit>> = _toastNetworkError

    val isNextButtonVisible = ObservableBoolean(true)
    val isProgressBarVisible = ObservableBoolean(false)

    val adapter = WelcomeAdapter()

    val action: (RecyclerView.ViewHolder, Int) -> Unit = { viewHolder, _ ->
        if (adapter.editTextList.isNotEmpty()) {
            adapter.editTextList.removeAt(viewHolder.adapterPosition)
            adapter.notifyDataSetChanged()

            if (viewHolder.adapterPosition >= 0) {
                deleteCardFromMemoryCacheUseCase(viewHolder.adapterPosition)
            }
        }
    }

    fun nextButtonClicked() {
        if (adapter.editTextList.isNullOrEmpty()) {
            isNextButtonVisible.set(true)
            _toastLiveDataCardListNullOrEmpty.postValue(Event(Unit))
            return
        } else {
            isNextButtonVisible.set(false)
        }

        if (adapter.editTextList.size == 1) {
            isNextButtonVisible.set(true)
            _toastLiveDataCardListLessThanTwo.postValue(Event(Unit))
            return
        }
        //формируем список запросов пользователя для поиска
        val userQueries = adapter.editTextList.map {
            it.query.get()!!
        }
        //сохраняем в мапу все запросы, приведенные к нижнему регистру, получаем уникальные запросы,
        //которые будут служить ключами в мапе. Значения в мапе остаются пустыми.
        localCache.saveQueriesToMapWithoutValues(userQueries)

        //Фетчим запросы-ключи из мапы и получаем к каждому из них значение - uri. Сохраняем их в мапе.
        viewModelScope.launch {
            isProgressBarVisible.set(true)
            val result = fetchDataUseCase()
            if (result !is Result.Success) {
                handleError(result)
            }
        }.invokeOnCompletion {
            //в этом юзкейсе сравниваются запросы пользователя и ключи из мапы. Если запросы пользователя
            // повторяются, то по ключу в мапе выдается url, который будет одинаковым для таких запросов.
            // За счет этого снижается количество запросов к api за одну сессию использования приложения
            // пользователем.
            populateCardListUseCase(userQueries)
            isProgressBarVisible.set(false)
            _nextButtonClicked.value = Event(Unit)
        }
    }

    private fun <T> handleError(result: Result<T>) {
        when (result) {
            is Result.NetworkError -> {
                _toastNetworkError.value = Event(Unit)
            }
            is Result.HttpError -> {
            }
            else -> Result.UndefinedError
        }
    }

}