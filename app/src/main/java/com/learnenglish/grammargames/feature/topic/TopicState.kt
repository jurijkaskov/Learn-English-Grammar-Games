package com.learnenglish.grammargames.feature.topic

import com.learnenglish.grammargames.domain.model.book.TopicBookCompanionReference
import com.learnenglish.grammargames.domain.model.mastery.MasterySkill
import com.learnenglish.grammargames.domain.model.mastery.MasteryStatus

data class TopicLessonItem(
    val id: String,
    val title: String,
    val description: String,
    val isCompleted: Boolean = false
)

data class TopicUiState(
    val topicId: String = "present_simple",
    val title: String = "Present Simple vs Present Continuous",
    val description: String = "Distinguish habitual actions from ongoing events. Master key time signal markers.",
    val referenceBook: String = "English Grammar in Use (Units 1-4)",
    val masteryPercentage: Int = 0,
    val masteryStatus: MasteryStatus = MasteryStatus.NOT_STARTED,
    val starsEarned: Int = 0,
    val skillsMastery: List<MasterySkill> = emptyList(),
    val lessons: List<TopicLessonItem> = listOf(
        TopicLessonItem("lesson_1", "1. Habitual & Stative Verbs", "Learn why 'I know' is right and 'I am knowing' is wrong", true),
        TopicLessonItem("lesson_2", "2. Present Continuous in Action", "Temporary actions happening around the present moment", false),
        TopicLessonItem("lesson_3", "3. Frequency Adverbs & Nuances", "Always, usually, rarely, and emotional continuous", false)
    ),
    val bookCompanionInfo: TopicBookCompanionReference? = null
)

sealed interface TopicUiAction {
    data class StartLesson(val lessonId: String) : TopicUiAction
    data object StartTest : TopicUiAction
    data object OpenGames : TopicUiAction
}
