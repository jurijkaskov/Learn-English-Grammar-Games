package com.learnenglish.grammargames.feature.onboarding.presentation.book

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun BookSelectionRoute(
    onContinueClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: BookSelectionViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    BookSelectionScreen(
        state = uiState,
        onSelectBook = { bookId -> viewModel.onAction(BookSelectionUiAction.SelectBook(bookId)) },
        onContinueClick = onContinueClick,
        onBackClick = onBackClick,
        modifier = modifier
    )
}
