package com.rickiand.morty.screen.model

data class PersonsUiState(
    val persons: List<PersonUiState> = emptyList()
)

data class PersonUiState(
    val id: Int = 0,
    val name: String = "name",
    val status: String = "Default",
    val species: String = "not",
    val type: String = "default",
    val gender: String = "man",
    val location: String = "Earth",
    val image: String = ""
)
