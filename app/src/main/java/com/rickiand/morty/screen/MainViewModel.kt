package com.rickiand.morty.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rickiand.morty.domain.usecase.MainUseCase
import com.rickiand.morty.screen.model.PersonsUiState
import com.rickiand.morty.utils.toListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainUseCase: MainUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(PersonsUiState())
    val uiState : StateFlow<PersonsUiState> = _uiState

    init {
        viewModelScope.launch {
            mainUseCase.flow.collectLatest {
                _uiState.value = uiState.value.copy(
                    persons = it.persons.toListUiState()
                )
            }
        }
    }

    fun getList(){
        viewModelScope.launch{
            mainUseCase.getListPerson()
        }
    }
}