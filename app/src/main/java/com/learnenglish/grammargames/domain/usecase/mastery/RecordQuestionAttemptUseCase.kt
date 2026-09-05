package com.learnenglish.grammargames.domain.usecase.mastery

import com.learnenglish.grammargames.domain.model.mastery.QuestionAttempt
import com.learnenglish.grammargames.domain.model.mastery.TopicMastery
import com.learnenglish.grammargames.domain.repository.MasteryRepository
import javax.inject.Inject

class RecordQuestionAttemptUseCase @Inject constructor(
    private val masteryRepository: MasteryRepository
) {
    suspend operator fun invoke(attempt: QuestionAttempt): TopicMastery {
        return masteryRepository.recordAttempt(attempt)
    }

    suspend fun recordMultiple(attempts: List<QuestionAttempt>): Map<String, TopicMastery> {
        return masteryRepository.recordAttempts(attempts)
    }
}
