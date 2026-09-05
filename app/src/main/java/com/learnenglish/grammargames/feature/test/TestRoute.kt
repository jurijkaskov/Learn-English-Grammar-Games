package com.learnenglish.grammargames.feature.test

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun TestRoute(
    topicId: String,
    onFinishTest: (sessionId: String) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TestViewModel = viewModel()
) {
    LaunchedEffect(topicId) {
        viewModel.setTopicId(topicId)
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    TestScreen(
        state = uiState,
        onSelectOption = { index -> viewModel.onAction(TestUiAction.SelectOption(index)) },
        onNextOrSubmit = { viewModel.onAction(TestUiAction.NextOrSubmit) },
        onFinishTest = onFinishTest,
        onBackClick = onBackClick,
        modifier = modifier
    )
}
