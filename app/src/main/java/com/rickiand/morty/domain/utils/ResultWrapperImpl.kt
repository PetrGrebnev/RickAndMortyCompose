package com.rickiand.morty.domain.utils

import com.rickiand.morty.data.utils.DataError
import com.rickiand.morty.data.utils.DataResult

class ResultWrapperImpl: ResultWrapper {
    override val unknownError: DomainError
        get() = DomainError.NetworkError("error Network")
    override val emptyResult: DomainResult<Unit>
        get() = DomainResult.Success(Unit)

    override suspend fun <T, R> wrap(
        block: suspend () -> DataResult<T>,
        error: (error: DataError) -> DomainError,
        mapper: suspend (data: T) -> R,
    ): DomainResult<R> = when(val result = block()) {
        is DataResult.Success -> DomainResult.Success(mapper(result.data))
        is DataResult.Error -> DomainResult.Error(error(result.error))
    }

    override fun defaultError(error: DataError): DomainError =
        when (error) {
            is DataError.MsgError -> DomainError.NetworkError(error.msg)
            else -> unknownError
        }
}