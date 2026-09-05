package com.learnenglish.grammargames.feature.profile.character

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun CharacterRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CharacterViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CharacterScreen(
        state = uiState,
        onBackClick = onBackClick,
        modifier = modifier
    )
}
