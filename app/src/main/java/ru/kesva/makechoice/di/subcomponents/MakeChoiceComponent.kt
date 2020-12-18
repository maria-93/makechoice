package ru.kesva.makechoice.di.subcomponents

import dagger.Subcomponent
import ru.kesva.makechoice.ui.makechoicefragment.MakeChoiceFragment

@Subcomponent
interface MakeChoiceComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): MakeChoiceComponent
    }

    fun provideDependenciesFor(makeChoiceFragment: MakeChoiceFragment)


}