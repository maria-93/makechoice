package ru.kesva.makechoice.di.modules

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.kesva.makechoice.di.ViewModelKey
import ru.kesva.makechoice.ui.viewmodel.MakeChoiceViewModel

@Module
abstract class MakeChoiceViewModelBindsModule {
    @Binds
    @IntoMap
    @ViewModelKey(MakeChoiceViewModel::class)
    abstract fun bindMakeChoiceViewModel(viewModel: MakeChoiceViewModel): ViewModel
}