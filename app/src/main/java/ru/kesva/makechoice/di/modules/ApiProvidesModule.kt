package ru.kesva.makechoice.di.modules

import dagger.Module
import dagger.Provides
import ru.kesva.makechoice.data.source.remote.CustomSearchGoogleApi

@Module
class ApiProvidesModule {

    @Provides
    fun provideCustomSearchGoogleApi(): CustomSearchGoogleApi = CustomSearchGoogleApi.create()
}