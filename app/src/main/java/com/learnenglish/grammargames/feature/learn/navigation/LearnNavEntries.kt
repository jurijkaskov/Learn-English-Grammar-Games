package com.learnenglish.grammargames.feature.learn.navigation

import androidx.navigation3.runtime.NavEntry
import com.learnenglish.grammargames.core.navigation.AppNavKey
import com.learnenglish.grammargames.core.navigation.AppNavigator
import com.learnenglish.grammargames.feature.learn.LearnRoute

fun learnNavEntry(
    key: AppNavKey.Learn,
    navigator: AppNavigator
): NavEntry<AppNavKey> = NavEntry(key) {
    LearnRoute(
        onTopicClick = { topicId -> navigator.navigateToTopic(topicId) }
    )
}
