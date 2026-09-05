package com.learnenglish.grammargames.domain.usecase.mastery

import com.learnenglish.grammargames.domain.repository.MasteryRepository
import javax.inject.Inject

class ResetMasteryUseCase @Inject constructor(
    private val masteryRepository: MasteryRepository
) {
    suspend operator fun invoke(topicId: String? = null) {
        if (topicId != null) {
            masteryRepository.resetTopicMastery(topicId)
        } else {
            masteryRepository.resetAllMastery()
        }
    }
}
