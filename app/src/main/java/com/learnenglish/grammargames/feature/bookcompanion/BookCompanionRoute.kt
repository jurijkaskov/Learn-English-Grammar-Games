package com.learnenglish.grammargames.feature.bookcompanion

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun BookCompanionRoute(
    bookId: String?,
    editionId: String?,
    initialUnitNumber: Int?,
    onNavigateToTopic: (String) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: BookCompanionViewModel = viewModel()
) {
    LaunchedEffect(bookId, editionId, initialUnitNumber) {
        viewModel.initialize(bookId, editionId, initialUnitNumber)
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    BookCompanionScreen(
        state = uiState,
        onAction = viewModel::onAction,
        onNavigateToTopic = onNavigateToTopic,
        onBackClick = onBackClick,
        modifier = modifier
    )
}
