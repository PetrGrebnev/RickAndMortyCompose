package com.rickiand.morty.data.service

import com.rickiand.morty.data.model.CommonResponse
import com.rickiand.morty.data.model.DetailPersonDto
import com.rickiand.morty.data.model.LocationADto
import com.rickiand.morty.data.model.PersonDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ServiceApi {

    @GET("character")
    suspend fun getList(
        @Query("page") pager: Int
    ): Response<CommonResponse<List<PersonDto>>>

    @GET("location")
    suspend fun getLocation(): Response<CommonResponse<List<LocationADto>>>

    @GET
    suspend fun getPerson(
        @Url url: String
    ): Response<DetailPersonDto>
}