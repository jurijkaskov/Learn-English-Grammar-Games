package com.learnenglish.grammargames.feature.onboarding.presentation.complete

data class OnboardingCompleteUiState(
    val title: String = "You're All Set!",
    val subtitle: String = "Your personalized grammar adventure is configured. A dragon companion has hatched to join your journey!",
    val isCompleting: Boolean = false
)

sealed interface OnboardingCompleteUiAction {
    data object FinishOnboarding : OnboardingCompleteUiAction
}
