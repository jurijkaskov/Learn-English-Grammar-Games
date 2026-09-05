package com.learnenglish.grammargames.feature.topic

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun TopicRoute(
    topicId: String,
    onStartLesson: (topicId: String, lessonId: String) -> Unit,
    onStartTest: (topicId: String) -> Unit,
    onOpenGames: (topicId: String) -> Unit,
    onOpenBookCompanion: (bookId: String?, editionId: String?, unitNumber: Int?) -> Unit = { _, _, _ -> },
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TopicViewModel = viewModel()
) {
    LaunchedEffect(topicId) {
        viewModel.setTopicId(topicId)
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    TopicScreen(
        state = uiState,
        onStartLesson = { lessonId -> onStartLesson(topicId, lessonId) },
        onStartTest = { onStartTest(topicId) },
        onOpenGames = { onOpenGames(topicId) },
        onOpenBookCompanion = onOpenBookCompanion,
        onBackClick = onBackClick,
        modifier = modifier
    )
}
