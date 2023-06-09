package com.rickiand.morty.data.model

import com.google.gson.annotations.SerializedName

data class DetailPersonDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("image") val image: String
)
