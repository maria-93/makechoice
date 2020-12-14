package ru.kesva.makechoice.di.modules

import dagger.Module
import dagger.Provides
import ru.kesva.makechoice.MainActivity
import ru.kesva.makechoice.di.ViewModelFactory
import ru.kesva.makechoice.extensions.getViewModel
import ru.kesva.makechoice.ui.viewmodel.SharedViewModel
import ru.kesva.makechoice.ui.welcomefragment.WelcomeAdapterClickHandler

@Module
class ClickHandlersProvidesModule(private val mainActivity: MainActivity) {

    @Provides
    fun provideWelcomeAdapterClickHandler(factory: ViewModelFactory) =
        mainActivity.getViewModel<SharedViewModel>(factory) as WelcomeAdapterClickHandler
}