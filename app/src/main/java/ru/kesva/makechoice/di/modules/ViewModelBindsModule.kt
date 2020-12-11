package ru.kesva.makechoice.di.modules

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.kesva.makechoice.di.ViewModelKey
import ru.kesva.makechoice.ui.viewmodel.SharedViewModel

@Module
abstract class ViewModelBindsModule {
    @Binds
    @IntoMap
    @ViewModelKey(SharedViewModel::class)
    abstract fun bindSharedViewModel(viewModel: SharedViewModel): ViewModel
}