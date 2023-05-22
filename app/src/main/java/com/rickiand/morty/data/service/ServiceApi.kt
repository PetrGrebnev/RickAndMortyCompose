package com.rickiand.morty.data.service

import com.rickiand.morty.data.model.CommonResponse
import retrofit2.Response
import retrofit2.http.GET

interface ServiceApi {

    @GET("character")
    suspend fun getList(): Response<CommonResponse>
}