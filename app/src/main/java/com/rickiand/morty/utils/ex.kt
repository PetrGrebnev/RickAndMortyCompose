package com.rickiand.morty.utils

import com.rickiand.morty.data.model.PersonDto
import com.rickiand.morty.screen.model.PersonUiState

fun List<PersonDto>.toListUiState(): List<PersonUiState> {
    val list = mutableListOf<PersonUiState>()
    this.forEach {
        list.add(
            PersonUiState(
                id = it.id,
                name = it.name,
                status = it.status,
                species = it.species,
                location = it.location.name,
                gender = it.gender,
                image = it.image,
                type = it.type
            )
        )
    }
    return list
}