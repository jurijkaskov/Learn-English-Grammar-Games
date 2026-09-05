package com.learnenglish.grammargames.domain.usecase

import com.learnenglish.grammargames.domain.model.UserPreferences
import com.learnenglish.grammargames.domain.repository.UserPreferencesRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class ObserveUserPreferencesUseCase @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) {
    operator fun invoke(): Flow<UserPreferences> = userPreferencesRepository.observePreferences()
}
