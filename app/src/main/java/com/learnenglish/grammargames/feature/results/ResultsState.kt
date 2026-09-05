package com.learnenglish.grammargames.feature.results

data class ResultsUiState(
    val sessionId: String = "",
    val sessionType: String = "LESSON",
    val title: String = "Great Work!",
    val xpEarned: Int = 45,
    val accuracy: Int = 92,
    val companionBonusXp: Int = 15,
    val companionMessage: String = "Your dragon gained +60 XP! Only 40 XP to next evolution level."
)

sealed interface ResultsUiAction {
    data object Done : ResultsUiAction
    data object ReviewMistakes : ResultsUiAction
}
