package com.learnenglish.grammargames.feature.onboarding.presentation.dailygoal

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun DailyGoalRoute(
    onContinueClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DailyGoalViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    DailyGoalScreen(
        state = uiState,
        onSelectGoal = { minutes -> viewModel.onAction(DailyGoalUiAction.SelectGoal(minutes)) },
        onContinueClick = onContinueClick,
        onBackClick = onBackClick,
        modifier = modifier
    )
}
