package com.learnenglish.grammargames.domain.model

data class UserPreferences(
    val onboardingCompleted: Boolean = false,
    val selectedCourseId: String = "general_english",
    val dailyGoalMinutes: Int = 15,
    val selectedBookId: String? = "english_grammar_in_use",
    val selectedEditionId: String? = "english_grammar_in_use_5"
)
