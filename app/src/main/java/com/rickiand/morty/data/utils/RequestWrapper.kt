package com.rickiand.morty.data.utils

import retrofit2.Response

interface RequestWrapper {

    suspend fun <T, R> wrap(
        request: suspend () -> Response<T>,
        mapper: (T) -> R,
    ): DataResult<R>

    suspend fun <T> wrap(
        request: suspend () -> Response<T>,
    ): DataResult<T>
}