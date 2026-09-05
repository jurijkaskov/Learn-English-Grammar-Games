package com.learnenglish.grammargames.domain.model

data class UserPreferences(
    val onboardingCompleted: Boolean = false,
    val selectedCourseId: String = "general_english",
    val dailyGoalMinutes: Int = 15
)
