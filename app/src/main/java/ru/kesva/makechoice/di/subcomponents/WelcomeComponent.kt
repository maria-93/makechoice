package ru.kesva.makechoice.di.subcomponents

import dagger.Subcomponent
import ru.kesva.makechoice.di.modules.ClickHandlersProvidesModule
import ru.kesva.makechoice.ui.welcomefragment.WelcomeAdapterClickHandler
import ru.kesva.makechoice.ui.welcomefragment.WelcomeFragment

@Subcomponent(modules = [ClickHandlersProvidesModule::class])
interface WelcomeComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(clickHandlersProvidesModule: ClickHandlersProvidesModule): WelcomeComponent
    }

    fun provideDependenciesFor(welcomeFragment: WelcomeFragment)
}