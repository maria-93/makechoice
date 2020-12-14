package ru.kesva.makechoice.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.kesva.makechoice.MakeChoiceApplication
import ru.kesva.makechoice.data.model.Cache
import ru.kesva.makechoice.di.modules.ApiProvidesModule
import ru.kesva.makechoice.di.modules.AppSubComponents
import ru.kesva.makechoice.di.modules.PhotoRepositoryProvidesModule
import ru.kesva.makechoice.di.modules.ViewModelBindsModule
import ru.kesva.makechoice.di.subcomponents.MainActivityComponent
import ru.kesva.makechoice.di.subcomponents.MakeChoiceComponent
import ru.kesva.makechoice.di.subcomponents.WelcomeComponent
import javax.inject.Singleton

@Singleton
@Component(modules = [AppSubComponents::class, ApiProvidesModule::class, PhotoRepositoryProvidesModule::class,
ViewModelBindsModule::class, ViewModelBuilderModule::class])
interface AppComponent {

    fun provideDependenciesFor(makeChoiceApplication: MakeChoiceApplication)

    fun mainActivityComponent(): MainActivityComponent.Factory
    fun makeChoiceComponent(): MakeChoiceComponent.Factory
    fun welcomeComponent(): WelcomeComponent.Factory

    @Component.Factory
    interface Factory {
        fun create(@ApplicationContext @BindsInstance context: Context, @BindsInstance cache: Cache): AppComponent
    }
}