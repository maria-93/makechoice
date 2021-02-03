package ru.kesva.makechoice.data.repository

import ru.kesva.makechoice.data.repository.FakeBaseRepository
import ru.kesva.makechoice.data.repository.Result
import ru.kesva.makechoice.domain.repository.Repository

class FakeRepository : FakeBaseRepository(), Repository {
    val uri = "testUri"

    override suspend fun fetchUriOnRequest(query: String): Result<String> {
        return getResult(uri)
    }
}