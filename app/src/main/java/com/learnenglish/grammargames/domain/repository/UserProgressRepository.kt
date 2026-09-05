package com.learnenglish.grammargames.domain.repository

import com.learnenglish.grammargames.domain.model.UserProgress
import kotlinx.coroutines.flow.Flow

interface UserProgressRepository {
    fun observeUserProgress(): Flow<UserProgress>
    suspend fun updateUserProgress(progress: UserProgress)
    suspend fun addXp(amount: Long)
}
