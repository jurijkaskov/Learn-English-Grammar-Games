package com.learnenglish.grammargames.domain.usecase.mastery

import com.learnenglish.grammargames.domain.model.mastery.TopicMastery
import com.learnenglish.grammargames.domain.repository.MasteryRepository
import javax.inject.Inject

class GetTopicMasteryUseCase @Inject constructor(
    private val masteryRepository: MasteryRepository
) {
    suspend operator fun invoke(topicId: String): TopicMastery {
        return masteryRepository.getTopicMastery(topicId)
    }
}
