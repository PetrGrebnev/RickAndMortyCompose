package com.rickiand.morty.screen.model

import com.rickiand.morty.domain.model.LocationDomain

data class LocationUiState(
    val loading: Boolean = false,
    val list: List<LocationDomain> = emptyList()
)
