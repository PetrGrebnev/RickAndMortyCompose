package com.rickiand.morty.domain.model

import com.rickiand.morty.data.model.PersonDto

data class ListPersonDomain(
    val persons: List<PersonDto>
)