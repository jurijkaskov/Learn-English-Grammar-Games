package com.learnenglish.grammargames.feature.onboarding.presentation.goal

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun GoalSelectionRoute(
    onContinueClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: GoalSelectionViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    GoalSelectionScreen(
        state = uiState,
        onSelectGoal = { goalId -> viewModel.onAction(GoalSelectionUiAction.SelectGoal(goalId)) },
        onContinueClick = onContinueClick,
        onBackClick = onBackClick,
        modifier = modifier
    )
}
