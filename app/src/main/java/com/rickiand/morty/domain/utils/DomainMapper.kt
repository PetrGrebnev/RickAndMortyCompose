package com.rickiand.morty.domain.utils

import com.rickiand.morty.data.model.DetailPersonDto
import com.rickiand.morty.data.model.LocationADto
import com.rickiand.morty.domain.model.LocationDomain

fun LocationADto.toDomain(list: List<DetailPersonDto>) = LocationDomain(
    id = id,
    name = name,
    type = type,
    dimension = dimension,
    imagePerson = list
)