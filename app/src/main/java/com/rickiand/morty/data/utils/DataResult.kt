package com.rickiand.morty.data.utils

sealed class DataResult<out T>{

    data class Success<out T>(val data: T): DataResult<T>()

    data class Error(val error: DataError): DataResult<Nothing>()

}

inline fun <reified T> DataResult<T>.onSuccess(block: (value: T) -> Unit): DataResult<T> {
    if (this is DataResult.Success) {
        block(data)
    }
    return this
}

inline fun <reified T> DataResult<T>.onError(block: (error: DataError) -> Unit): DataResult<T> {
    if (this is  DataResult.Error){
        block(error)
    }
    return this
}
