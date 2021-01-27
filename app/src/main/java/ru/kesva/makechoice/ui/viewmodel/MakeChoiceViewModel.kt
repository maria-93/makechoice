package ru.kesva.makechoice.ui.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import ru.kesva.makechoice.data.model.Cache
import ru.kesva.makechoice.domain.model.Card
import ru.kesva.makechoice.ui.customlayout.AnimatedGridLayout
import javax.inject.Inject

class MakeChoiceViewModel @Inject constructor(private val cache: Cache) : ViewModel() {

    val isStartAnimationButtonVisible = ObservableBoolean(true)

    fun startAnimation(animatedGridLayout: AnimatedGridLayout) {
        animatedGridLayout.startViewAnimation(0)
        isStartAnimationButtonVisible.set(false)
    }

    fun getCardListFromCache(): List<Card> {
        return cache.cardList
    }
}