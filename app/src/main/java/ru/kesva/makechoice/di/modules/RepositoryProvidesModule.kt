package ru.kesva.makechoice.di.modules

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import ru.kesva.makechoice.data.repository.CustomSearchRepositoryImp
import ru.kesva.makechoice.data.repository.PexelsRepositoryImp
import ru.kesva.makechoice.data.source.remote.CustomSearchGoogleApi
import ru.kesva.makechoice.data.source.remote.PexelsApi
import ru.kesva.makechoice.domain.repository.Repository

@Module
class RepositoryProvidesModule {
    @Provides
    fun provideRepository(api: PexelsApi): Repository =
        PexelsRepositoryImp(api, Dispatchers.IO)

}