package com.rickiand.morty.data.model

import com.google.gson.annotations.SerializedName

data class CommonResponse<T>(
    @SerializedName("info") val info: InfoDto,
    @SerializedName("results") val results: T
)
