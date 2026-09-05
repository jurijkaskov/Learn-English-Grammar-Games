package com.learnenglish.grammargames.domain.usecase

import com.learnenglish.grammargames.core.navigation.AppNavKey
import com.learnenglish.grammargames.domain.repository.UserPreferencesRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAppStartDestinationUseCase @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) {
    operator fun invoke(): Flow<AppNavKey> {
        return userPreferencesRepository.observePreferences().map { prefs ->
            if (prefs.onboardingCompleted) {
                AppNavKey.Home
            } else {
                AppNavKey.Welcome
            }
        }
    }
}
