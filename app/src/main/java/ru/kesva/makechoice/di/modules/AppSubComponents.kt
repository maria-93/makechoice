package ru.kesva.makechoice.di.modules

import dagger.Module
import ru.kesva.makechoice.di.subcomponents.MainActivityComponent
import ru.kesva.makechoice.di.subcomponents.MakeChoiceComponent
import ru.kesva.makechoice.di.subcomponents.WelcomeComponent

@Module(subcomponents = [MainActivityComponent::class, WelcomeComponent::class, MakeChoiceComponent::class])
class AppSubComponents