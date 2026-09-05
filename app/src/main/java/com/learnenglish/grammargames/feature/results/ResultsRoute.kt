package com.learnenglish.grammargames.feature.results

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ResultsRoute(
    sessionId: String,
    sessionType: String,
    onReviewMistakesClick: () -> Unit,
    onDoneClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ResultsViewModel = viewModel()
) {
    LaunchedEffect(sessionId, sessionType) {
        viewModel.initResults(sessionId, sessionType)
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ResultsScreen(
        state = uiState,
        onReviewMistakesClick = onReviewMistakesClick,
        onDoneClick = onDoneClick,
        modifier = modifier
    )
}
