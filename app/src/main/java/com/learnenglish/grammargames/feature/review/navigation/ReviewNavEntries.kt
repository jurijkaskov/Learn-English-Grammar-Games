package com.learnenglish.grammargames.feature.review.navigation

import androidx.navigation3.runtime.NavEntry
import com.learnenglish.grammargames.core.navigation.AppNavKey
import com.learnenglish.grammargames.core.navigation.AppNavigator
import com.learnenglish.grammargames.core.navigation.DemoNavigationFixtures
import com.learnenglish.grammargames.feature.review.ReviewRoute

fun reviewNavEntry(
    key: AppNavKey.Review,
    navigator: AppNavigator
): NavEntry<AppNavKey> = NavEntry(key) {
    ReviewRoute(
        onStartReviewSession = { navigator.navigateToGameSession("speed_challenge", DemoNavigationFixtures.DEMO_TOPIC_ID) },
        onMistakesClick = { navigator.navigateToMistakes() }
    )
}
