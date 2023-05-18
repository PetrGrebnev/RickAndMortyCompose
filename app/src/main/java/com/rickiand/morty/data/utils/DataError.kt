package com.rickiand.morty.data.utils

sealed class DataError {

    object BadRequest : DataError()

    object NotFound : DataError()

    data class MsgError(val msg: String) : DataError()

    data class ServerError(val code: Int) : DataError()

    data class ClientError(val code: Int) : DataError()

    object Unknown : DataError()

}
