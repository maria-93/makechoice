package ru.kesva.makechoice.data.repository

import java.lang.UnsupportedOperationException
import kotlin.reflect.KClass

open class FakeBaseRepository {
    var resultToReturn: KClass<out Result<*>>? = null
        get() {
            if (field == null) {
                throw UnsupportedOperationException("Please define the class which to return")
            } else {
                return field
            }
        }

    protected open fun <T> getResult(value: T): Result<T> {
        return when (resultToReturn) {
            Result.Success::class -> Result.Success(value)
            Result.HttpError::class -> getHttpError(400)
            Result.NetworkError::class -> Result.NetworkError
            else -> Result.UndefinedError
        }
    }

    protected open fun getHttpError(code: Int) = Result.HttpError(code, ErrorResponse("Error description goes here, code: $code"))
}