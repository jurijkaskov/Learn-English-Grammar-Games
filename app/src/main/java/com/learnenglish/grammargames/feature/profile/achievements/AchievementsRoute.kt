package com.learnenglish.grammargames.feature.profile.achievements

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AchievementsRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AchievementsViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    AchievementsScreen(
        state = uiState,
        onBackClick = onBackClick,
        modifier = modifier
    )
}
