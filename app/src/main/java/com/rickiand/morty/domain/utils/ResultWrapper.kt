package com.rickiand.morty.domain.utils

import com.rickiand.morty.data.utils.DataError
import com.rickiand.morty.data.utils.DataResult

interface ResultWrapper {
    val unknownError: DomainError
    val emptyResult: DomainResult<Unit>

    suspend fun <T, R> wrap(
        block: suspend () -> DataResult<T>,
        error: (error: DataError) -> DomainError = this::defaultError,
        mapper: suspend (data: T) -> R
    ): DomainResult<R>

    fun defaultError(error: DataError): DomainError
}