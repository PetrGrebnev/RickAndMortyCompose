package com.rickiand.morty.domain.model

import com.rickiand.morty.data.model.DetailPersonDto

data class LocationDomain(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val imagePerson: List<DetailPersonDto>
)
