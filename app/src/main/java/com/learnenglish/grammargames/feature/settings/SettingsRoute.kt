package com.learnenglish.grammargames.feature.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SettingsRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    onOpenShowcase: () -> Unit = {},
    onOpenCurriculumInspector: () -> Unit = {},
    viewModel: SettingsViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SettingsScreen(
        state = uiState,
        onAction = viewModel::onAction,
        onBackClick = onBackClick,
        onOpenShowcase = onOpenShowcase,
        onOpenCurriculumInspector = onOpenCurriculumInspector,
        modifier = modifier
    )
}
