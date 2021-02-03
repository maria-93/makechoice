package ru.kesva.makechoice.di.modules

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.kesva.makechoice.di.ViewModelKey
import ru.kesva.makechoice.ui.viewmodel.WelcomeViewModel

@Module
abstract class WelcomeViewModelBindsModule {
    @Binds
    @IntoMap
    @ViewModelKey(WelcomeViewModel::class)
    abstract fun bindWelcomeViewModel(viewModel: WelcomeViewModel): ViewModel

}