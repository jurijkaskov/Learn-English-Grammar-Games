package com.learnenglish.grammargames.feature.results.navigation

import androidx.navigation3.runtime.NavEntry
import com.learnenglish.grammargames.core.navigation.AppNavKey
import com.learnenglish.grammargames.core.navigation.AppNavigator
import com.learnenglish.grammargames.feature.results.ResultsRoute

fun resultsNavEntry(
    key: AppNavKey.Results,
    navigator: AppNavigator
): NavEntry<AppNavKey> = NavEntry(key) {
    ResultsRoute(
        sessionId = key.sessionId,
        sessionType = key.resultType,
        onReviewMistakesClick = { navigator.navigateToMistakes() },
        onDoneClick = { navigator.navigateToRoot(AppNavKey.Home) }
    )
}
