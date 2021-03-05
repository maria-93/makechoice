package ru.kesva.makechoice.data.repository

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import ru.kesva.makechoice.data.source.remote.PexelsApi
import ru.kesva.makechoice.domain.model.Photo
import ru.kesva.makechoice.domain.repository.Repository
import java.util.*

class PexelsRepositoryImp(
    private val api: PexelsApi,
    private val dispatcher: CoroutineDispatcher
) : BaseRepository(), Repository {

    private val key = "563492ad6f917000010000016ca31ac031d546758e7f880b07d48730"

    override suspend fun fetchUriOnRequest(query: String): Result<String> =
        safeApiCall(dispatcher) {
            val language = Locale.getDefault().language
            val country = Locale.getDefault().country
            val locale = "$language-$country"
            val photoResponse = api.getPhotoResponse(
                key, locale, query
            )
            val photos: List<Photo> = photoResponse.photos
            val randomImageUriPicker = RandomImageUriPicker()
            val imageUri = randomImageUriPicker.getRandomImageUriPexelsApi(photos)
            Log.d("Fetch", "fetchUriOnRequest: $imageUri")
            imageUri
        }
}
