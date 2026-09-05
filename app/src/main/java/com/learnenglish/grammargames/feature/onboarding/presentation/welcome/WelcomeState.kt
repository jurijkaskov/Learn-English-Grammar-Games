package com.learnenglish.grammargames.feature.onboarding.presentation.welcome

data class WelcomeUiState(
    val title: String = "Master English Grammar Through Games",
    val subtitle: String = "Interactive grammar intuition, spaced repetition, and classical pedagogy combined into an engaging adventure."
)

sealed interface WelcomeUiAction {
    data object GetStarted : WelcomeUiAction
}
