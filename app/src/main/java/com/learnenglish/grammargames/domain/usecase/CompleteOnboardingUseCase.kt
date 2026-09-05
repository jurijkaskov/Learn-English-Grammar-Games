package com.learnenglish.grammargames.domain.usecase

import com.learnenglish.grammargames.domain.repository.UserPreferencesRepository
import javax.inject.Inject

class CompleteOnboardingUseCase @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) {
    suspend operator fun invoke() {
        userPreferencesRepository.setOnboardingCompleted(true)
    }
}
