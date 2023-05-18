package com.rickiand.morty.domain.usecase

import com.rickiand.morty.domain.model.ListPersonDomain
import com.rickiand.morty.domain.utils.DomainResult
import kotlinx.coroutines.flow.Flow

interface MainUseCase {
    val flow: Flow<ListPersonDomain>

    suspend fun getListPerson(): DomainResult<Unit>
}