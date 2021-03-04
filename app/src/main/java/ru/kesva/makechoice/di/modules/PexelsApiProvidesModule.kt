package ru.kesva.makechoice.di.modules

import dagger.Module
import dagger.Provides
import ru.kesva.makechoice.data.source.remote.PexelsApi

@Module
class PexelsApiProvidesModule {

    @Provides
    fun providePexelsApi(): PexelsApi = PexelsApi.create()
}