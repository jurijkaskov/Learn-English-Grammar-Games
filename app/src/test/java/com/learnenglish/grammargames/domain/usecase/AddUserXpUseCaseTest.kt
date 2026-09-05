package com.learnenglish.grammargames.domain.usecase

import com.learnenglish.grammargames.domain.model.UserProgress
import com.learnenglish.grammargames.domain.repository.UserProgressRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class AddUserXpUseCaseTest {

    private class FakeUserProgressRepository : UserProgressRepository {
        var currentProgress = UserProgress(totalXp = 100L, level = 1)
        val addedXpCalls = mutableListOf<Long>()

        override fun observeUserProgress(): Flow<UserProgress> = flowOf(currentProgress)

        override suspend fun updateUserProgress(progress: UserProgress) {
            currentProgress = progress
        }

        override suspend fun addXp(amount: Long) {
            addedXpCalls.add(amount)
            currentProgress = currentProgress.copy(totalXp = currentProgress.totalXp + amount)
        }
    }

    @Test
    fun addUserXp_invokesRepositoryWhenPositive() = runTest {
        val fakeRepo = FakeUserProgressRepository()
        val useCase = AddUserXpUseCase(fakeRepo)

        useCase(50L)

        assertEquals(listOf(50L), fakeRepo.addedXpCalls)
    }

    @Test
    fun addUserXp_ignoresNonPositiveAmount() = runTest {
        val fakeRepo = FakeUserProgressRepository()
        val useCase = AddUserXpUseCase(fakeRepo)

        useCase(0L)
        useCase(-10L)

        assertEquals(0, fakeRepo.addedXpCalls.size)
    }
}
