package com.learnenglish.grammargames.domain.usecase

import com.learnenglish.grammargames.domain.repository.UserProgressRepository
import javax.inject.Inject

class AddUserXpUseCase @Inject constructor(
    private val userProgressRepository: UserProgressRepository
) {
    suspend operator fun invoke(amount: Long) {
        if (amount > 0) {
            userProgressRepository.addXp(amount)
        }
    }
}
