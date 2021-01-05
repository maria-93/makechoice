package ru.kesva.makechoice

import ru.kesva.makechoice.data.repository.Result
import ru.kesva.makechoice.domain.model.Card
import ru.kesva.makechoice.domain.repository.PhotoRepository

class FakeRepository : BaseFakeRepository(), PhotoRepository {
    val uri = "testUri"

    override suspend fun getCard(query: String): Result<Card> {
        return getResult(Card(query,uri))
    }
}