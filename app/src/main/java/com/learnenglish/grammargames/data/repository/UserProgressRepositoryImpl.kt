package com.learnenglish.grammargames.data.repository

import com.learnenglish.grammargames.core.common.dispatcher.AppDispatchers
import com.learnenglish.grammargames.core.database.dao.UserProgressDao
import com.learnenglish.grammargames.data.mapper.toDomain
import com.learnenglish.grammargames.data.mapper.toEntity
import com.learnenglish.grammargames.domain.model.UserProgress
import com.learnenglish.grammargames.domain.repository.UserProgressRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

@Singleton
class UserProgressRepositoryImpl @Inject constructor(
    private val userProgressDao: UserProgressDao,
    private val dispatchers: AppDispatchers
) : UserProgressRepository {

    override fun observeUserProgress(): Flow<UserProgress> {
        return userProgressDao.observeUserProgress()
            .map { it.toDomain() }
            .flowOn(dispatchers.io)
    }

    override suspend fun updateUserProgress(progress: UserProgress) {
        withContext(dispatchers.io) {
            userProgressDao.upsertUserProgress(progress.toEntity())
        }
    }

    override suspend fun addXp(amount: Long) {
        withContext(dispatchers.io) {
            userProgressDao.addXp(amount)
        }
    }
}
