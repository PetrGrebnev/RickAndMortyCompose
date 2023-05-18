package com.rickiand.morty.data.model

import com.google.gson.annotations.SerializedName

data class CommonResponse(
    @SerializedName("info") val info: InfoDto,
    @SerializedName("results") val results: List<PersonDto>
)
