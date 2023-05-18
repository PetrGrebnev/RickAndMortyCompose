package com.rickiand.morty.domain.utils

sealed interface DomainError{
    val message: String

    data class NetworkError(override val message: String) : DomainError
}
