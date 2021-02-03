package ru.kesva.makechoice.domain.repository
import ru.kesva.makechoice.data.repository.Result

interface Repository {
 suspend fun fetchUriOnRequest(query: String): Result<String>
}