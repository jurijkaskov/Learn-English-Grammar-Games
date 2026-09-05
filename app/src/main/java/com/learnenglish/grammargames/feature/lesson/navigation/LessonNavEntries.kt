package com.learnenglish.grammargames.feature.lesson.navigation

import androidx.navigation3.runtime.NavEntry
import com.learnenglish.grammargames.core.navigation.AppNavKey
import com.learnenglish.grammargames.core.navigation.AppNavigator
import com.learnenglish.grammargames.feature.lesson.LessonRoute

fun lessonNavEntry(
    key: AppNavKey.Lesson,
    navigator: AppNavigator
): NavEntry<AppNavKey> = NavEntry(key) {
    LessonRoute(
        topicId = key.topicId,
        lessonId = key.lessonId,
        onCompleteLesson = { sessionId -> navigator.navigateToResults(sessionId, "LESSON") },
        onBackClick = { navigator.navigateBack() }
    )
}
