package com.learnenglish.grammargames.feature.bootstrap

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.learnenglish.grammargames.core.navigation.AppNavKey

@Composable
fun BootstrapRoute(
    onDestinationResolved: (AppNavKey) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: BootstrapViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState) {
        if (uiState is BootstrapUiState.Ready) {
            onDestinationResolved((uiState as BootstrapUiState.Ready).startDestination)
        }
    }

    BootstrapScreen(
        modifier = modifier
    )
}
