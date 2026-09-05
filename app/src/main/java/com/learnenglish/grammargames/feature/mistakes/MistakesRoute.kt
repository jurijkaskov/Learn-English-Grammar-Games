package com.learnenglish.grammargames.feature.mistakes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MistakesRoute(
    onPracticeMistake: (mistakeId: String) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MistakesViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MistakesScreen(
        state = uiState,
        onPracticeMistake = onPracticeMistake,
        onBackClick = onBackClick,
        modifier = modifier
    )
}
