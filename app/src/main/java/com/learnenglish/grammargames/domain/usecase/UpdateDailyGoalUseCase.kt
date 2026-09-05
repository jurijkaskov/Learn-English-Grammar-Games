package com.learnenglish.grammargames.domain.usecase

import com.learnenglish.grammargames.domain.repository.UserPreferencesRepository
import javax.inject.Inject

class UpdateDailyGoalUseCase @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) {
    suspend operator fun invoke(minutes: Int) {
        if (minutes in 5..120) {
            userPreferencesRepository.setDailyGoalMinutes(minutes)
        }
    }
}
