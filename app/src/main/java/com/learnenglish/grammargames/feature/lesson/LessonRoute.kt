package com.learnenglish.grammargames.feature.lesson

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LessonRoute(
    topicId: String,
    lessonId: String,
    onCompleteLesson: (sessionId: String) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LessonViewModel = viewModel()
) {
    LaunchedEffect(topicId, lessonId) {
        viewModel.setLesson(topicId, lessonId)
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LessonScreen(
        state = uiState,
        onCompleteLesson = onCompleteLesson,
        onBackClick = onBackClick,
        modifier = modifier
    )
}
