package com.learnenglish.grammargames.feature.games

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun GamesRoute(
    onPlayClick: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    GamesScreen(
        onPlayClick = onPlayClick,
        modifier = modifier
    )
}
