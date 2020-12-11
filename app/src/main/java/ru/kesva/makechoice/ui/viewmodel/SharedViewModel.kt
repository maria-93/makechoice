package ru.kesva.makechoice.ui.viewmodel

import androidx.lifecycle.ViewModel
import ru.kesva.makechoice.domain.usecase.MakeChoiceUseCase
import ru.kesva.makechoice.domain.usecase.SaveCardToMemoryCacheUseCase
import javax.inject.Inject

class SharedViewModel @Inject constructor(
    private val saveCardToMemoryCacheUseCase: SaveCardToMemoryCacheUseCase,
    private val makeChoiceUseCase: MakeChoiceUseCase
) : ViewModel() {
}