package com.learnenglish.grammargames.domain.usecase.mastery

import com.learnenglish.grammargames.domain.model.mastery.CourseMastery
import com.learnenglish.grammargames.domain.repository.MasteryRepository
import javax.inject.Inject

class GetCourseMasteryUseCase @Inject constructor(
    private val masteryRepository: MasteryRepository
) {
    suspend operator fun invoke(courseId: String): CourseMastery {
        return masteryRepository.getCourseMastery(courseId)
    }
}
