package com.learnenglish.grammargames.feature.games.session

data class GameSessionUiState(
    val gameType: String = "speed_tenses",
    val topicId: String? = null,
    val title: String = "Speed Tenses Arena",
    val currentRound: Int = 1,
    val totalRounds: Int = 5,
    val score: Int = 120,
    val secondsRemaining: Int = 24,
    val questionPrompt: String = "She _____ already finished her thesis before noon.",
    val options: List<String> = listOf("has", "had", "have", "having"),
    val selectedOptionIndex: Int? = null
)

sealed interface GameSessionUiAction {
    data class SelectOption(val index: Int) : GameSessionUiAction
    data object FinishGame : GameSessionUiAction
}
