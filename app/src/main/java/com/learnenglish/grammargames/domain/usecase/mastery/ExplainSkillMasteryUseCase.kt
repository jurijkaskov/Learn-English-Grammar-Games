package com.learnenglish.grammargames.domain.usecase.mastery

import com.learnenglish.grammargames.domain.model.mastery.MasteryExplanation
import com.learnenglish.grammargames.domain.repository.MasteryRepository
import javax.inject.Inject

class ExplainSkillMasteryUseCase @Inject constructor(
    private val masteryRepository: MasteryRepository
) {
    suspend operator fun invoke(skillId: String, topicId: String): MasteryExplanation {
        return masteryRepository.explainSkill(skillId, topicId)
    }
}
