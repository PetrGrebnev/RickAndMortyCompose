package com.rickiand.morty.data.repository

import com.rickiand.morty.data.model.CommonResponse
import com.rickiand.morty.data.model.DetailPersonDto
import com.rickiand.morty.data.model.LocationADto
import com.rickiand.morty.data.model.PersonDto
import com.rickiand.morty.data.service.ServiceApi
import com.rickiand.morty.data.utils.DataResult
import com.rickiand.morty.data.utils.RequestWrapper
import com.rickiand.morty.data.utils.RequestWrapperImpl
import com.rickiand.morty.screen.model.PersonsUiState
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val service: ServiceApi,
    dispatcher: CoroutineDispatcher,
) : MainRepository, RequestWrapper by RequestWrapperImpl(
    ioDispatcher = dispatcher
) {

    override suspend fun getListPerson(page: Int): DataResult<CommonResponse<List<PersonDto>>> =
        wrap { service.getList(page) }

    override suspend fun getListLocation(): DataResult<CommonResponse<List<LocationADto>>> =
        wrap { service.getLocation() }

    override suspend fun getPerson(url: String): DataResult<DetailPersonDto> =
        wrap { service.getPerson(url) }
}