package com.rickiand.morty.data.repository

import com.rickiand.morty.data.model.CommonResponse
import com.rickiand.morty.data.utils.DataResult

interface MainRepository {

    suspend fun getListPerson(): DataResult<CommonResponse>
}