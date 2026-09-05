package com.learnenglish.grammargames.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.learnenglish.grammargames.domain.model.UserPreferences
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

val Context.userPreferencesDataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

@Singleton
class UserPreferencesDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    private object Keys {
        val ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding_completed")
        val SELECTED_COURSE_ID = stringPreferencesKey("selected_course_id")
        val DAILY_GOAL_MINUTES = intPreferencesKey("daily_goal_minutes")
        val SELECTED_BOOK_ID = stringPreferencesKey("selected_book_id")
        val SELECTED_EDITION_ID = stringPreferencesKey("selected_edition_id")
    }

    val preferences: Flow<UserPreferences> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { prefs ->
            UserPreferences(
                onboardingCompleted = prefs[Keys.ONBOARDING_COMPLETED] ?: false,
                selectedCourseId = prefs[Keys.SELECTED_COURSE_ID] ?: "course_beginner",
                dailyGoalMinutes = prefs[Keys.DAILY_GOAL_MINUTES] ?: 15,
                selectedBookId = prefs[Keys.SELECTED_BOOK_ID] ?: "english_grammar_in_use",
                selectedEditionId = prefs[Keys.SELECTED_EDITION_ID] ?: "english_grammar_in_use_5"
            )
        }

    suspend fun setOnboardingCompleted(completed: Boolean) {
        dataStore.edit { prefs ->
            prefs[Keys.ONBOARDING_COMPLETED] = completed
        }
    }

    suspend fun setSelectedCourseId(courseId: String) {
        dataStore.edit { prefs ->
            prefs[Keys.SELECTED_COURSE_ID] = courseId
        }
    }

    suspend fun setDailyGoalMinutes(minutes: Int) {
        dataStore.edit { prefs ->
            prefs[Keys.DAILY_GOAL_MINUTES] = minutes
        }
    }

    suspend fun setSelectedBook(bookId: String?, editionId: String?) {
        dataStore.edit { prefs ->
            if (bookId != null) {
                prefs[Keys.SELECTED_BOOK_ID] = bookId
            } else {
                prefs.remove(Keys.SELECTED_BOOK_ID)
            }
            if (editionId != null) {
                prefs[Keys.SELECTED_EDITION_ID] = editionId
            } else {
                prefs.remove(Keys.SELECTED_EDITION_ID)
            }
        }
    }
}
