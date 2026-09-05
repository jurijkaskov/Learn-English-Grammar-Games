package com.learnenglish.grammargames.domain.usecase

import com.learnenglish.grammargames.domain.model.UserProgress
import com.learnenglish.grammargames.domain.repository.UserProgressRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class ObserveUserProgressUseCase @Inject constructor(
    private val userProgressRepository: UserProgressRepository
) {
    operator fun invoke(): Flow<UserProgress> = userProgressRepository.observeUserProgress()
}
