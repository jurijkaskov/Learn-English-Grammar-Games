package com.learnenglish.grammargames.feature.results

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class ResultsViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(ResultsUiState())
    val uiState: StateFlow<ResultsUiState> = _uiState.asStateFlow()

    fun initResults(sessionId: String, sessionType: String) {
        _uiState.update {
            it.copy(
                sessionId = sessionId,
                sessionType = sessionType,
                title = when (sessionType.uppercase()) {
                    "GAME" -> "Game Complete!"
                    "TEST" -> "Test Evaluated!"
                    else -> "Session Complete!"
                }
            )
        }
    }
}
