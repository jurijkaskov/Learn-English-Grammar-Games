package com.learnenglish.grammargames.feature.onboarding.presentation.placement

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun PlacementTestRoute(
    onContinueClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PlacementTestViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    PlacementTestScreen(
        state = uiState,
        onSelectOption = { index -> viewModel.onAction(PlacementTestUiAction.SelectOption(index)) },
        onNextQuestion = { viewModel.onAction(PlacementTestUiAction.NextQuestion) },
        onContinueClick = onContinueClick,
        onBackClick = onBackClick,
        modifier = modifier
    )
}
