package com.learnenglish.grammargames.feature.bootstrap

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learnenglish.grammargames.core.navigation.AppNavKey
import com.learnenglish.grammargames.domain.usecase.GetAppStartDestinationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

sealed interface BootstrapUiState {
    data object Loading : BootstrapUiState
    data class Ready(val startDestination: AppNavKey) : BootstrapUiState
}

@HiltViewModel
class BootstrapViewModel @Inject constructor(
    private val getAppStartDestinationUseCase: GetAppStartDestinationUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<BootstrapUiState>(BootstrapUiState.Loading)
    val uiState: StateFlow<BootstrapUiState> = _uiState.asStateFlow()

    init {
        determineStartDestination()
    }

    private fun determineStartDestination() {
        viewModelScope.launch {
            val destination = getAppStartDestinationUseCase().first()
            _uiState.value = BootstrapUiState.Ready(destination)
        }
    }
}
