package com.learnenglish.grammargames.data.repository

import com.learnenglish.grammargames.core.datastore.UserPreferencesDataSource
import com.learnenglish.grammargames.domain.model.UserPreferences
import com.learnenglish.grammargames.domain.repository.UserPreferencesRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow

@Singleton
class UserPreferencesRepositoryImpl @Inject constructor(
    private val userPreferencesDataSource: UserPreferencesDataSource
) : UserPreferencesRepository {

    override fun observePreferences(): Flow<UserPreferences> {
        return userPreferencesDataSource.preferences
    }

    override suspend fun setOnboardingCompleted(completed: Boolean) {
        userPreferencesDataSource.setOnboardingCompleted(completed)
    }

    override suspend fun setSelectedCourseId(courseId: String) {
        userPreferencesDataSource.setSelectedCourseId(courseId)
    }

    override suspend fun setDailyGoalMinutes(minutes: Int) {
        userPreferencesDataSource.setDailyGoalMinutes(minutes)
    }
}
