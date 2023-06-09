package com.rickiand.morty.data.model

import com.google.gson.annotations.SerializedName

data class LocationADto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("type") val type: String,
    @SerializedName("dimension") val dimension: String,
    @SerializedName("residents") val urlPerson: List<String>,
)
