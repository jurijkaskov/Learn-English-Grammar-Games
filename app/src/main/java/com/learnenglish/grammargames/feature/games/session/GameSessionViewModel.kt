package com.learnenglish.grammargames.feature.games.session

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class GameSessionViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(GameSessionUiState())
    val uiState: StateFlow<GameSessionUiState> = _uiState.asStateFlow()

    fun initSession(gameType: String, topicId: String?) {
        val title = when (gameType) {
            "sentence_builder" -> "Sentence Scramble"
            "error_hunter" -> "Error Detective"
            else -> "Speed Tenses Arena"
        }
        _uiState.update { it.copy(gameType = gameType, topicId = topicId, title = title) }
    }

    fun onAction(action: GameSessionUiAction) {
        when (action) {
            is GameSessionUiAction.SelectOption -> {
                _uiState.update { it.copy(selectedOptionIndex = action.index, score = it.score + 20) }
            }
            GameSessionUiAction.FinishGame -> {
                // Navigates via callback
            }
        }
    }
}
