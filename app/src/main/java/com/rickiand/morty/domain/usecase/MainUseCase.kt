package com.rickiand.morty.domain.usecase

import androidx.paging.PagingData
import com.rickiand.morty.data.model.PersonDto
import com.rickiand.morty.domain.model.ListPersonDomain
import com.rickiand.morty.domain.model.LocationDomain
import com.rickiand.morty.domain.model.PersonDomain
import com.rickiand.morty.domain.utils.DomainResult
import kotlinx.coroutines.flow.Flow

interface MainUseCase {
    val personFlow: Flow<ListPersonDomain>

    val locationFlow: Flow<List<LocationDomain>>

    fun getListPerson(): Flow<PagingData<PersonDto>>

    suspend fun getLocation(): DomainResult<Unit>

    suspend fun getPerson(url: String): DomainResult<PersonDomain>
}