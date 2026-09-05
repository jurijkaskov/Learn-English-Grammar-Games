package com.learnenglish.grammargames.feature.bookcompanion.navigation

import androidx.navigation3.runtime.NavEntry
import com.learnenglish.grammargames.core.navigation.AppNavKey
import com.learnenglish.grammargames.core.navigation.AppNavigator
import com.learnenglish.grammargames.feature.bookcompanion.BookCompanionRoute

fun bookCompanionNavEntry(
    key: AppNavKey.BookCompanion,
    navigator: AppNavigator
): NavEntry<AppNavKey> = NavEntry(key) {
    BookCompanionRoute(
        bookId = key.bookId,
        editionId = key.editionId,
        initialUnitNumber = key.initialUnitNumber,
        onNavigateToTopic = { topicId -> navigator.navigateToTopic(topicId) },
        onBackClick = { navigator.navigateBack() }
    )
}
