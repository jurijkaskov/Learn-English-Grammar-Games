package com.learnenglish.grammargames.data.repository

import com.learnenglish.grammargames.core.common.dispatcher.StandardDispatchers
import com.learnenglish.grammargames.core.database.dao.UserProgressDao
import com.learnenglish.grammargames.core.database.entity.UserProgressEntity
import com.learnenglish.grammargames.domain.model.UserProgress
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class UserProgressRepositoryTest {

    private class FakeUserProgressDao : UserProgressDao {
        val state = MutableStateFlow<UserProgressEntity?>(
            UserProgressEntity(
                id = 1,
                totalXp = 100L,
                level = 1,
                streakDays = 3,
                lastActiveTimestamp = 1000L
            )
        )

        override fun observeUserProgress(): Flow<UserProgressEntity?> = state

        override suspend fun upsertUserProgress(progress: UserProgressEntity) {
            state.value = progress
        }

        override suspend fun addXp(amount: Long) {
            val current = state.value ?: UserProgressEntity()
            state.value = current.copy(totalXp = current.totalXp + amount)
        }
    }

    @Test
    fun observeUserProgress_mapsEntityToDomain() = runTest {
        val dao = FakeUserProgressDao()
        val dispatchers = StandardDispatchers()
        val repository = UserProgressRepositoryImpl(dao, dispatchers)

        val progress = repository.observeUserProgress().first()
        assertEquals(100L, progress.totalXp)
        assertEquals(1, progress.level)
        assertEquals(3, progress.streakDays)
    }

    @Test
    fun addXp_invokesDaoAddXp() = runTest {
        val dao = FakeUserProgressDao()
        val dispatchers = StandardDispatchers()
        val repository = UserProgressRepositoryImpl(dao, dispatchers)

        repository.addXp(120L)
        assertEquals(220L, dao.state.value?.totalXp)
    }

    @Test
    fun updateUserProgress_invokesDaoUpsert() = runTest {
        val dao = FakeUserProgressDao()
        val dispatchers = StandardDispatchers()
        val repository = UserProgressRepositoryImpl(dao, dispatchers)

        repository.updateUserProgress(UserProgress(totalXp = 500L, level = 5, streakDays = 7))
        assertEquals(500L, dao.state.value?.totalXp)
        assertEquals(5, dao.state.value?.level)
        assertEquals(7, dao.state.value?.streakDays)
    }
}
