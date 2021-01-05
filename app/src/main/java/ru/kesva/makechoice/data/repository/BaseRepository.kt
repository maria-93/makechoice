package ru.kesva.makechoice.data.repository

import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception

data class ErrorResponse(
    val errorDescription: String,
    val causes: Map<String, String> = emptyMap()
)
sealed class Result<out T> {
    data class Success<out T>(val value: T): Result<T>()
    data class HttpError(val code: Int? = null, val error: ErrorResponse?): Result<Nothing>()
    object NetworkError : Result<Nothing>()
    object UndefinedError : Result<Nothing>()
}

open class BaseRepository {
    suspend fun <T> safeApiCall(
        dispatcher: CoroutineDispatcher,
        apiCall: suspend () -> T
    ): Result<T> {
        return withContext(dispatcher) {
            try {
                Result.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is IOException -> Result.NetworkError
                    is HttpException -> {
                        val code = throwable.code()
                        val errorResponse = convertErrorBody(throwable)
                        Result.HttpError(code, errorResponse)
                    }
                    else -> {
                        Result.HttpError(null, null)
                    }
                }
            }
        }
    }

    private fun convertErrorBody(throwable: HttpException): ErrorResponse? {
        return try {
            throwable.response()?.errorBody()?.source()?.let {
                val moshiAdapter = Moshi.Builder().build().adapter(ErrorResponse::class.java)
                moshiAdapter.fromJson(it)
            }
        } catch (exception: Exception) {
            null
        }
    }
}