package com.learnenglish.grammargames.feature.topic.navigation

import androidx.navigation3.runtime.NavEntry
import com.learnenglish.grammargames.core.navigation.AppNavKey
import com.learnenglish.grammargames.core.navigation.AppNavigator
import com.learnenglish.grammargames.feature.topic.TopicRoute

fun topicNavEntry(
    key: AppNavKey.Topic,
    navigator: AppNavigator
): NavEntry<AppNavKey> = NavEntry(key) {
    TopicRoute(
        topicId = key.topicId,
        onStartLesson = { topicId, lessonId -> navigator.navigateToLesson(topicId, lessonId) },
        onStartTest = { topicId -> navigator.navigateToTest(topicId) },
        onOpenGames = { topicId -> navigator.navigateToGameSession("speed_challenge", topicId) },
        onBackClick = { navigator.navigateBack() }
    )
}
