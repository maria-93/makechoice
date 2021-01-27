package ru.kesva.makechoice.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import ru.kesva.makechoice.data.source.remote.CustomSearchGoogleApi
import ru.kesva.makechoice.domain.model.Items
import ru.kesva.makechoice.domain.repository.Repository

class RepositoryImp(
    private val api: CustomSearchGoogleApi,
    private val dispatcher: CoroutineDispatcher
) : BaseRepository(), Repository {

    private val key = "AIzaSyDhhbudfasaj9Nirm9rmmrEu7jb0ep6lWI"
    private val cx = "3f8ce6301b45b1e05"

    override suspend fun fetchUriOnRequest(query: String): Result<String> =
        safeApiCall(dispatcher) {
            val photoResponse = api.getPhotoResponse(
                key, cx,
                "image", 4, "ru", "photo", query
            )
            val itemsList: List<Items> = photoResponse.items
            val randomImageUriPicker = RandomImageUriPicker()
            val imageUri = randomImageUriPicker.getRandomImageUri(itemsList)
            imageUri
        }
}