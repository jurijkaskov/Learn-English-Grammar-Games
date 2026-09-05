package com.learnenglish.grammargames.feature.onboarding.presentation.goal

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class GoalSelectionViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(GoalSelectionUiState())
    val uiState: StateFlow<GoalSelectionUiState> = _uiState.asStateFlow()

    fun onAction(action: GoalSelectionUiAction) {
        when (action) {
            is GoalSelectionUiAction.SelectGoal -> {
                _uiState.update { it.copy(selectedGoalId = action.goalId) }
            }
        }
    }
}
