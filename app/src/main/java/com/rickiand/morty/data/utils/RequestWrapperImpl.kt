package com.rickiand.morty.data.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException
import java.lang.IllegalArgumentException
import java.net.HttpURLConnection

class RequestWrapperImpl(
    private val ioDispatcher: CoroutineDispatcher,
) : RequestWrapper {

    override suspend fun <T, R> wrap(
        request: suspend () -> Response<T>,
        mapper: (T) -> R,
    ): DataResult<R> =
        withContext(ioDispatcher) {
            try {
                handleResponse(request(), mapper)
            } catch (e: Throwable) {
                handleRequestExceprion(e)
            }
        }

    private fun <T, R> handleResponse(request: Response<T>, mapper: (T) -> R): DataResult<R> =
        try {
            if (request.isSuccessful) {
                request.body()?.let {
                    DataResult.Success(mapper(it))
                } ?: DataResult.Error(DataError.Unknown)
            } else {
                println("DEBUG:${request.code()}")
                handleError(request.code())
            }
        } catch (e: Throwable) {
            DataResult.Error(error = DataError.Unknown)
        }

    private fun handleError(code: Int): DataResult.Error {
        val error = when (code) {
            HttpURLConnection.HTTP_BAD_REQUEST -> DataError.BadRequest
            HttpURLConnection.HTTP_NOT_FOUND -> DataError.NotFound
            in 400..499 -> DataError.ClientError(code)
            in 500..526 -> DataError.ServerError(code)
            else -> DataError.Unknown
        }
        return DataResult.Error(error)
    }

    private fun handleRequestExceprion(e: Throwable): DataResult.Error =
        DataResult.Error(
            when (e) {
                is IOException -> DataError.MsgError("${e.message}")
                else -> DataError.Unknown
            }
        )


    override suspend fun <T> wrap(request: suspend () -> Response<T>): DataResult<T> =
        withContext(ioDispatcher) {
            try {
                handleResponse(request = request(), mapper = {it})
            } catch (e: Throwable) {
                handleRequestExceprion(e)
            }
        }
}