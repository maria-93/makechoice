package ru.kesva.makechoice.di.subcomponents

import dagger.Subcomponent
import ru.kesva.makechoice.ui.WelcomeFragment

@Subcomponent
interface WelcomeComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): WelcomeComponent
    }

    fun provideDependenciesFor(welcomeFragment: WelcomeFragment)
}