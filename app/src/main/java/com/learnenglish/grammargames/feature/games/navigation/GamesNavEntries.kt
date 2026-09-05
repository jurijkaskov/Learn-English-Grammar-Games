package com.learnenglish.grammargames.feature.games.navigation

import androidx.navigation3.runtime.NavEntry
import com.learnenglish.grammargames.core.navigation.AppNavKey
import com.learnenglish.grammargames.core.navigation.AppNavigator
import com.learnenglish.grammargames.feature.games.GamesRoute
import com.learnenglish.grammargames.feature.games.session.GameSessionRoute

fun gamesHubNavEntry(
    key: AppNavKey.Games,
    navigator: AppNavigator
): NavEntry<AppNavKey> = NavEntry(key) {
    GamesRoute(
        onPlayClick = { gameType -> navigator.navigateToGameSession(gameType) }
    )
}

fun gameSessionNavEntry(
    key: AppNavKey.GameSession,
    navigator: AppNavigator
): NavEntry<AppNavKey> = NavEntry(key) {
    GameSessionRoute(
        gameType = key.gameType,
        topicId = key.topicId,
        onFinishGame = { sessionId -> navigator.navigateToResults(sessionId, "GAME") },
        onBackClick = { navigator.navigateBack() }
    )
}
