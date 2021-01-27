package ru.kesva.makechoice.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.kesva.makechoice.MakeChoiceApplication
import ru.kesva.makechoice.data.model.Cache
import ru.kesva.makechoice.data.model.LocalCacheImp
import ru.kesva.makechoice.di.modules.*
import ru.kesva.makechoice.di.subcomponents.MainActivityComponent
import ru.kesva.makechoice.di.subcomponents.MakeChoiceComponent
import ru.kesva.makechoice.di.subcomponents.WelcomeComponent
import javax.inject.Singleton


@Component(modules = [AppSubComponents::class, ApiProvidesModule::class, PhotoRepositoryProvidesModule::class,
WelcomeViewModelBindsModule::class, LocalCacheProvidesModule::class, MakeChoiceViewModelBindsModule::class, ViewModelBuilderModule::class])
@Singleton
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