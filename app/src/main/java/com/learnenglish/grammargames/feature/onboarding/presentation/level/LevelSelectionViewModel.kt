package com.learnenglish.grammargames.feature.onboarding.presentation.level

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class LevelSelectionViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(LevelSelectionUiState())
    val uiState: StateFlow<LevelSelectionUiState> = _uiState.asStateFlow()

    fun onAction(action: LevelSelectionUiAction) {
        when (action) {
            is LevelSelectionUiAction.SelectLevel -> {
                _uiState.update { it.copy(selectedLevelId = action.levelId) }
            }
        }
    }
}
