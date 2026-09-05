package com.learnenglish.grammargames.feature.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeRoute(
    onContinueLearning: (String) -> Unit = {},
    onDailyChallenge: () -> Unit = {},
    onGamesClick: () -> Unit = {},
    onMistakesClick: () -> Unit = {},
    onAchievementsClick: () -> Unit = {},
    onCharacterClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        uiState = uiState,
        onAction = viewModel::onAction,
        onContinueLearning = onContinueLearning,
        onDailyChallenge = onDailyChallenge,
        onGamesClick = onGamesClick,
        onMistakesClick = onMistakesClick,
        onAchievementsClick = onAchievementsClick,
        onCharacterClick = onCharacterClick,
        modifier = modifier
    )
}
