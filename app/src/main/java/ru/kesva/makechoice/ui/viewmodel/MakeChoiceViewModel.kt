package ru.kesva.makechoice.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.kesva.makechoice.data.model.Cache
import ru.kesva.makechoice.data.model.Event
import ru.kesva.makechoice.domain.model.Card
import ru.kesva.makechoice.ui.customlayout.AnimatedGridLayout
import javax.inject.Inject

class MakeChoiceViewModel @Inject constructor(private val cache: Cache) : ViewModel() {
    private val _playAgainButtonClicked: MutableLiveData<Event<Unit>> = MutableLiveData()
    val playAgainButtonClicked: LiveData<Event<Unit>> = _playAgainButtonClicked

    private val _startOverButtonClicked: MutableLiveData<Event<Unit>> = MutableLiveData()
    val startOverButtonClicked: LiveData<Event<Unit>> = _startOverButtonClicked

    private val _animationStartedEvent = MutableLiveData<Event<Unit>>()
    val animationStartedEvent: LiveData<Event<Unit>> = _animationStartedEvent


    fun startAnimation(animatedGridLayout: AnimatedGridLayout) {
        animatedGridLayout.startViewAnimation(0)
        _animationStartedEvent.value = Event(Unit)
    }

    fun getCardListFromCache(): List<Card> {
        return cache.cardList
    }

    fun createNewList() {
        _startOverButtonClicked.value = Event(Unit)
    }

}