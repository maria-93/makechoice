package ru.kesva.makechoice.data.repository

import ru.kesva.makechoice.domain.model.Card
import ru.kesva.makechoice.domain.repository.PhotoRepository

class PhotoRepositoryImp : PhotoRepository {
    override fun getCard(query: String): Result<Card> {
        TODO("Not yet implemented")
    }
}