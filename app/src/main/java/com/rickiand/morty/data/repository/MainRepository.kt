package com.rickiand.morty.data.repository

import com.rickiand.morty.data.model.CommonResponse
import com.rickiand.morty.data.model.DetailPersonDto
import com.rickiand.morty.data.model.LocationADto
import com.rickiand.morty.data.model.PersonDto
import com.rickiand.morty.data.utils.DataResult
import com.rickiand.morty.screen.model.PersonsUiState

interface MainRepository {

    suspend fun getListPerson(page: Int): DataResult<CommonResponse<List<PersonDto>>>

    suspend fun getListLocation(): DataResult<CommonResponse<List<LocationADto>>>

    suspend fun getPerson(url: String): DataResult<DetailPersonDto>
}