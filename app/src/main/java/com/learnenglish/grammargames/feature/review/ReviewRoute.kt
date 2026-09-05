package com.learnenglish.grammargames.feature.review

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ReviewRoute(
    onStartReviewSession: () -> Unit = {},
    onMistakesClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    ReviewScreen(
        onStartReviewSession = onStartReviewSession,
        onMistakesClick = onMistakesClick,
        modifier = modifier
    )
}
