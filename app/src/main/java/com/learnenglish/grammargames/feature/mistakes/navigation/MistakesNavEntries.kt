package com.learnenglish.grammargames.feature.mistakes.navigation

import androidx.navigation3.runtime.NavEntry
import com.learnenglish.grammargames.core.navigation.AppNavKey
import com.learnenglish.grammargames.core.navigation.AppNavigator
import com.learnenglish.grammargames.core.navigation.DemoNavigationFixtures
import com.learnenglish.grammargames.feature.mistakes.MistakesRoute

fun mistakesNavEntry(
    key: AppNavKey.Mistakes,
    navigator: AppNavigator
): NavEntry<AppNavKey> = NavEntry(key) {
    MistakesRoute(
        onPracticeMistake = { navigator.navigateToGameSession("speed_challenge", DemoNavigationFixtures.DEMO_TOPIC_ID) },
        onBackClick = { navigator.navigateBack() }
    )
}
