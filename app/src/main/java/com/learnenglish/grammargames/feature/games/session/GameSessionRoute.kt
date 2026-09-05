package com.learnenglish.grammargames.feature.games.session

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun GameSessionRoute(
    gameType: String,
    topicId: String?,
    onFinishGame: (sessionId: String) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: GameSessionViewModel = viewModel()
) {
    LaunchedEffect(gameType, topicId) {
        viewModel.initSession(gameType, topicId)
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    GameSessionScreen(
        state = uiState,
        onSelectOption = { index -> viewModel.onAction(GameSessionUiAction.SelectOption(index)) },
        onFinishGame = onFinishGame,
        onBackClick = onBackClick,
        modifier = modifier
    )
}
