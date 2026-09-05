package com.learnenglish.grammargames.feature.lesson

data class LessonRuleSection(
    val title: String,
    val explanation: String,
    val examples: List<Pair<String, String>> // English to Note
)

data class LessonUiState(
    val topicId: String = "present_simple",
    val lessonId: String = "lesson_1",
    val title: String = "1. Habitual & Stative Verbs",
    val ruleSummary: String = "We use the Present Simple for things that are true in general, or for things that happen repeatedly.",
    val sections: List<LessonRuleSection> = listOf(
        LessonRuleSection(
            title = "1. Habits and Routines",
            explanation = "Notice the third person singular -s or -es ending on verbs.",
            examples = listOf(
                "I work in a lab." to "Regular base form",
                "She works in a hospital." to "Third person -s rule"
            )
        ),
        LessonRuleSection(
            title = "2. Stative Verbs (No Continuous)",
            explanation = "Verbs of thinking, emotion, and senses are rarely used in continuous forms.",
            examples = listOf(
                "I know the answer." to "NOT: I am knowing",
                "She believes you." to "NOT: She is believing"
            )
        )
    )
)

sealed interface LessonUiAction {
    data object CompleteLesson : LessonUiAction
}
