package ru.kesva.makechoice.di.subcomponents

import dagger.Subcomponent
import ru.kesva.makechoice.MainActivity

@Subcomponent
interface MainActivityComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): MainActivityComponent
    }

    fun provideDependenciesFor(mainActivity: MainActivity)
}