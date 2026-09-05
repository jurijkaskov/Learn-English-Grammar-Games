package com.learnenglish.grammargames.feature.onboarding.presentation.complete

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun OnboardingCompleteRoute(
    onStartLearningClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: OnboardingCompleteViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    OnboardingCompleteScreen(
        state = uiState,
        onStartLearningClick = {
            viewModel.onAction(
                OnboardingCompleteUiAction.FinishOnboarding,
                onFinished = onStartLearningClick
            )
        },
        modifier = modifier
    )
}
