package ru.kesva.makechoice.di.modules

import dagger.Module
import dagger.Provides
import ru.kesva.makechoice.data.source.remote.CustomSearchGoogleApi
import ru.kesva.makechoice.data.source.remote.PexelsApi

@Module
class CustomSearchApiProvidesModule {

    @Provides
    fun provideCustomSearchGoogleApi(): CustomSearchGoogleApi = CustomSearchGoogleApi.create()


}