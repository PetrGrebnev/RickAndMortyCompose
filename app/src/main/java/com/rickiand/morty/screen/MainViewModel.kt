package com.rickiand.morty.screen

import androidx.lifecycle.ViewModel
import com.rickiand.morty.domain.usecase.MainUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainUseCase: MainUseCase
): ViewModel() {

}