package com.learnenglish.grammargames.feature.onboarding.presentation.dailygoal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learnenglish.grammargames.domain.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class DailyGoalViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DailyGoalUiState())
    val uiState: StateFlow<DailyGoalUiState> = _uiState.asStateFlow()

    fun onAction(action: DailyGoalUiAction) {
        when (action) {
            is DailyGoalUiAction.SelectGoal -> {
                _uiState.update { it.copy(selectedMinutes = action.minutes) }
                viewModelScope.launch {
                    userPreferencesRepository.setDailyGoalMinutes(action.minutes)
                }
            }
        }
    }
}
