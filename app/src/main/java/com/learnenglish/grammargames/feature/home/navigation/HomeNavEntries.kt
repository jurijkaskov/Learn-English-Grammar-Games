package com.learnenglish.grammargames.feature.home.navigation

import androidx.navigation3.runtime.NavEntry
import com.learnenglish.grammargames.core.navigation.AppNavKey
import com.learnenglish.grammargames.core.navigation.AppNavigator
import com.learnenglish.grammargames.core.navigation.DemoNavigationFixtures
import com.learnenglish.grammargames.feature.home.HomeRoute

fun homeNavEntry(
    key: AppNavKey.Home,
    navigator: AppNavigator
): NavEntry<AppNavKey> = NavEntry(key) {
    HomeRoute(
        onContinueLearning = { topicId -> navigator.navigateToTopic(topicId) },
        onDailyChallenge = { navigator.navigateToGameSession(DemoNavigationFixtures.DEMO_GAME_TYPE, DemoNavigationFixtures.DEMO_TOPIC_ID) },
        onGamesClick = { navigator.navigateToRoot(AppNavKey.Games) },
        onMistakesClick = { navigator.navigateToMistakes() },
        onAchievementsClick = { navigator.navigateToAchievements() },
        onCharacterClick = { navigator.navigateToCharacter() }
    )
}
