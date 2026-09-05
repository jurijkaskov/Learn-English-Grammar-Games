package com.learnenglish.grammargames.feature.onboarding.presentation.level

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LevelSelectionRoute(
    onContinueClick: () -> Unit,
    onTakePlacementTestClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LevelSelectionViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LevelSelectionScreen(
        state = uiState,
        onSelectLevel = { levelId -> viewModel.onAction(LevelSelectionUiAction.SelectLevel(levelId)) },
        onTakePlacementTestClick = onTakePlacementTestClick,
        onContinueClick = onContinueClick,
        onBackClick = onBackClick,
        modifier = modifier
    )
}
