package ru.kesva.makechoice.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import ru.kesva.makechoice.domain.usecase.MakeChoiceUseCase
import ru.kesva.makechoice.domain.usecase.SaveCardToMemoryCacheUseCase
import ru.kesva.makechoice.ui.welcomefragment.WelcomeAdapterClickHandler
import javax.inject.Inject

class SharedViewModel @Inject constructor(
    private val saveCardToMemoryCacheUseCase: SaveCardToMemoryCacheUseCase,
    private val makeChoiceUseCase: MakeChoiceUseCase
) : ViewModel(), WelcomeAdapterClickHandler {

    override fun nextButtonClicked(recyclerView: RecyclerView) {

    }

}