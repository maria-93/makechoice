package ru.kesva.makechoice.di.modules

import dagger.Module
import dagger.Provides
import ru.kesva.makechoice.data.model.LocalCache
import ru.kesva.makechoice.data.model.LocalCacheImp
import javax.inject.Singleton

@Module
class LocalCacheProvidesModule {
    @Singleton
    @Provides
    fun provideLocalCache(): LocalCache = LocalCacheImp()
}