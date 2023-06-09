package com.rickiand.morty.screen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.rickiand.morty.data.model.PersonDto
import com.rickiand.morty.domain.usecase.MainUseCase
import com.rickiand.morty.domain.utils.onSuccess
import com.rickiand.morty.screen.model.PersonUiState
import com.rickiand.morty.screen.model.PersonsUiState
import com.rickiand.morty.utils.toListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
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
            _uiState.value = uiState.value.copy(
                loading = true
            )
            mainUseCase.personFlow.collectLatest {
                _uiState.value = uiState.value.copy(
                    loading = false,
                    persons = it.persons.toListUiState()
                )
            }
        }
    }

    private var pagingData : Flow<PagingData<PersonDto>>? = null

    fun getList(): Flow<PagingData<PersonDto>> =
        if (pagingData != null) {
            pagingData!!
        } else {
            pagingData = mainUseCase.getListPerson().cachedIn(viewModelScope)
            pagingData!!
        }


    private val _uiStateDetails = MutableStateFlow(PersonUiStateDetail())
    val uiStateDetail :StateFlow<PersonUiStateDetail> = _uiStateDetails

    fun getPerson(id: String){
        viewModelScope.launch {
            _uiStateDetails.value = uiStateDetail.value.copy(
                loading = true,
            )
            mainUseCase.getPerson(id).onSuccess {
                _uiStateDetails.value = uiStateDetail.value.copy(
                    loading = false,
                    person = PersonUiState(
                        id = it.id,
                        name = it.name,
                        image = it.avatar
                    )
                )
            }
        }
    }
}

data class PersonUiStateDetail(
    val loading: Boolean = false,
    val person: PersonUiState = PersonUiState()
)