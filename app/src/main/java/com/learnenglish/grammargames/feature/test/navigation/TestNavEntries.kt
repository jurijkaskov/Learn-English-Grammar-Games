package com.learnenglish.grammargames.feature.test.navigation

import androidx.navigation3.runtime.NavEntry
import com.learnenglish.grammargames.core.navigation.AppNavKey
import com.learnenglish.grammargames.core.navigation.AppNavigator
import com.learnenglish.grammargames.feature.test.TestRoute

fun testNavEntry(
    key: AppNavKey.Test,
    navigator: AppNavigator
): NavEntry<AppNavKey> = NavEntry(key) {
    TestRoute(
        topicId = key.topicId,
        onFinishTest = { sessionId -> navigator.navigateToResults(sessionId, "TEST") },
        onBackClick = { navigator.navigateBack() }
    )
}
