package com.learnenglish.grammargames.feature.home

import com.learnenglish.grammargames.domain.model.Course
import com.learnenglish.grammargames.domain.model.UserPreferences
import com.learnenglish.grammargames.domain.model.UserProgress

data class HomeUiState(
    val isLoading: Boolean = true,
    val courses: List<Course> = emptyList(),
    val progress: UserProgress = UserProgress(),
    val preferences: UserPreferences = UserPreferences(),
    val errorMessage: String? = null
)
