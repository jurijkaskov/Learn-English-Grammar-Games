package com.learnenglish.grammargames.feature.learn

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LearnRoute(
    onTopicClick: (String) -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: LearnViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LearnScreen(
        uiState = uiState,
        onTopicClick = onTopicClick,
        onRetryClick = { viewModel.loadCurriculumTopics() },
        modifier = modifier
    )
}
