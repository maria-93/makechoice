package ru.kesva.makechoice.domain.repository
import ru.kesva.makechoice.data.repository.Result
import ru.kesva.makechoice.domain.model.Card

interface Repository {
 suspend fun fetchUriOnRequest(query: String): Result<String>
}