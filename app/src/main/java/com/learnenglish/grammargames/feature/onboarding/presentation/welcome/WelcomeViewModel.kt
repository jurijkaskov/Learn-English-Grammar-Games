package com.learnenglish.grammargames.feature.onboarding.presentation.welcome

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class WelcomeViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(WelcomeUiState())
    val uiState: StateFlow<WelcomeUiState> = _uiState.asStateFlow()

    fun onAction(action: WelcomeUiAction) {
        when (action) {
            WelcomeUiAction.GetStarted -> {
                // Handled via navigation callback in Route
            }
        }
    }
}
