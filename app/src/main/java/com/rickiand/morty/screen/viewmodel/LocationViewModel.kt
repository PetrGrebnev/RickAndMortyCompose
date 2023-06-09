package com.rickiand.morty.screen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rickiand.morty.domain.usecase.MainUseCase
import com.rickiand.morty.screen.model.LocationUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val mainUseCase: MainUseCase
) : ViewModel() {

    val uiState: StateFlow<LocationUiState>
        get() = _uiState
    private val _uiState = MutableStateFlow(LocationUiState())

    init {
        viewModelScope.launch {
            _uiState.value = uiState.value.copy(
                loading = true
            )
            mainUseCase.locationFlow.collectLatest {
                _uiState.value = uiState.value.copy(
                    loading = false,
                    list = it
                )
            }
        }
    }

    fun getLocation(){
        viewModelScope.launch {
            mainUseCase.getLocation()
        }
    }
}