package com.rickiand.morty.data.model

import com.google.gson.annotations.SerializedName

data class PersonDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("status") val status: String,
    @SerializedName("species") val species: String,
    @SerializedName("type") val type: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("location") val location: LocationDto,
    @SerializedName("image") val image: String
)
