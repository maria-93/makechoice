package ru.kesva.makechoice.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import ru.kesva.makechoice.data.source.remote.CustomSearchGoogleApi
import ru.kesva.makechoice.domain.model.Card
import ru.kesva.makechoice.domain.repository.PhotoRepository

class PhotoRepositoryImp(
    private val api: CustomSearchGoogleApi,
    private val dispatcher: CoroutineDispatcher
) : BaseRepository(), PhotoRepository {
    private val cardList: MutableList<Card> = mutableListOf()

    private val key = "AIzaSyDhhbudfasaj9Nirm9rmmrEu7jb0ep6lWI"
    private val cx = "3f8ce6301b45b1e05"

    override suspend fun getCard(query: String): Result<Card> =
        safeApiCall(dispatcher) {
            val photoResponse = api.getPhotoResponse(
                key, cx,
                "image", 1, "ru", "photo", query
            )
            val itemsList = photoResponse.items
            val imageItem = itemsList[itemsList.lastIndex]
            val imageUri = imageItem.image.imageUri
            Card(query, imageUri)
        }

    override fun getListOfCards(): List<Card> {
        return cardList
    }
}