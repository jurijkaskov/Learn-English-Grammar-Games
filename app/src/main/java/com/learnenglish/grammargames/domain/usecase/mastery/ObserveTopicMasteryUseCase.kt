package com.learnenglish.grammargames.domain.usecase.mastery

import com.learnenglish.grammargames.domain.model.mastery.TopicMastery
import com.learnenglish.grammargames.domain.repository.MasteryRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class ObserveTopicMasteryUseCase @Inject constructor(
    private val masteryRepository: MasteryRepository
) {
    operator fun invoke(topicId: String): Flow<TopicMastery> {
        return masteryRepository.observeTopicMastery(topicId)
    }
}
