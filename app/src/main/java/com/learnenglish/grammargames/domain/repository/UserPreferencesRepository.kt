package com.learnenglish.grammargames.domain.repository

import com.learnenglish.grammargames.domain.model.UserPreferences
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {
    fun observePreferences(): Flow<UserPreferences>
    suspend fun setOnboardingCompleted(completed: Boolean)
    suspend fun setSelectedCourseId(courseId: String)
    suspend fun setDailyGoalMinutes(minutes: Int)
    suspend fun setSelectedBook(bookId: String?, editionId: String?)
}
