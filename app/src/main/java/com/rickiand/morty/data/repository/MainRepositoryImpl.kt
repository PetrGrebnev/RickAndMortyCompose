package com.rickiand.morty.data.repository

import com.rickiand.morty.data.model.CommonResponse
import com.rickiand.morty.data.service.ServiceApi
import com.rickiand.morty.data.utils.DataResult
import com.rickiand.morty.data.utils.RequestWrapper
import com.rickiand.morty.data.utils.RequestWrapperImpl
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val service: ServiceApi,
    dispatcher: CoroutineDispatcher,
) : MainRepository, RequestWrapper by RequestWrapperImpl(
    ioDispatcher = dispatcher
) {

    override suspend fun getListPerson(): DataResult<CommonResponse> =
        wrap { service.getList() }

}