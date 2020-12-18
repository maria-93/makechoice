package ru.kesva.makechoice.di.modules

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import ru.kesva.makechoice.data.repository.PhotoRepositoryImp
import ru.kesva.makechoice.data.source.remote.CustomSearchGoogleApi
import ru.kesva.makechoice.domain.repository.PhotoRepository

@Module
class PhotoRepositoryProvidesModule {
    @Provides
    fun providePhotoRepository(api: CustomSearchGoogleApi): PhotoRepository =
        PhotoRepositoryImp(api, Dispatchers.IO)
}