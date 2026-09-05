package com.learnenglish.grammargames.feature.settings

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class SettingsViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    fun onAction(action: SettingsUiAction) {
        when (action) {
            is SettingsUiAction.ToggleDarkTheme -> _uiState.update { it.copy(darkThemeEnabled = action.enabled) }
            is SettingsUiAction.ToggleSound -> _uiState.update { it.copy(soundEffectsEnabled = action.enabled) }
            is SettingsUiAction.ToggleHaptic -> _uiState.update { it.copy(hapticFeedbackEnabled = action.enabled) }
            is SettingsUiAction.ToggleReminder -> _uiState.update { it.copy(dailyReminderEnabled = action.enabled) }
        }
    }
}
