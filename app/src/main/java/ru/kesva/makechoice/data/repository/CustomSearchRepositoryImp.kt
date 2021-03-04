package ru.kesva.makechoice.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import ru.kesva.makechoice.data.source.remote.CustomSearchGoogleApi
import ru.kesva.makechoice.domain.model.Items
import ru.kesva.makechoice.domain.repository.Repository

class CustomSearchRepositoryImp(
    private val api: CustomSearchGoogleApi,
    private val dispatcher: CoroutineDispatcher
) : BaseRepository(), Repository {

    private val key = "AIzaSyDhhbudfasaj9Nirm9rmmrEu7jb0ep6lWI"
    private val cx = "3f8ce6301b45b1e05"

    override suspend fun fetchUriOnRequest(query: String, color: String): Result<String> =
        safeApiCall(dispatcher) {
            val response = api.getResponse(
                key, cx,
                "image", 4, "ru", "photo", query
            )
            val itemsList: List<Items> = response.items
            val randomImageUriPicker = RandomImageUriPicker()
            val imageUri = randomImageUriPicker.getRandomImageUriCustomSearchApi(itemsList)
            imageUri
        }
}