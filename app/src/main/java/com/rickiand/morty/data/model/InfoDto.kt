package com.rickiand.morty.data.model

import com.google.gson.annotations.SerializedName

data class InfoDto (
    @SerializedName("count") val count: Int,
    @SerializedName("pages") val pages: String,
    @SerializedName("next") val nextPage: String?,
    @SerializedName("prev") val prevPage: String?,
)