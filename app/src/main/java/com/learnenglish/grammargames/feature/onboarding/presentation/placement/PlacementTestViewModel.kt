package com.learnenglish.grammargames.feature.onboarding.presentation.placement

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class PlacementTestViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(PlacementTestUiState())
    val uiState: StateFlow<PlacementTestUiState> = _uiState.asStateFlow()

    fun onAction(action: PlacementTestUiAction) {
        when (action) {
            is PlacementTestUiAction.SelectOption -> {
                _uiState.update { it.copy(selectedOptionIndex = action.optionIndex) }
            }
            PlacementTestUiAction.NextQuestion -> {
                val current = _uiState.value
                if (current.currentQuestionIndex + 1 < current.questions.size) {
                    _uiState.update {
                        it.copy(
                            currentQuestionIndex = it.currentQuestionIndex + 1,
                            selectedOptionIndex = null
                        )
                    }
                } else {
                    _uiState.update { it.copy(isCompleted = true) }
                }
            }
        }
    }
}
