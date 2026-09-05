package com.learnenglish.grammargames.domain.repository

import com.learnenglish.grammargames.domain.model.mastery.CourseMastery
import com.learnenglish.grammargames.domain.model.mastery.MasteryExplanation
import com.learnenglish.grammargames.domain.model.mastery.MasterySkill
import com.learnenglish.grammargames.domain.model.mastery.QuestionAttempt
import com.learnenglish.grammargames.domain.model.mastery.SectionMastery
import com.learnenglish.grammargames.domain.model.mastery.TopicMastery
import kotlinx.coroutines.flow.Flow

interface MasteryRepository {
    fun observeTopicMastery(topicId: String): Flow<TopicMastery>
    fun observeAllTopicMasteries(): Flow<List<TopicMastery>>
    suspend fun getTopicMastery(topicId: String): TopicMastery
    suspend fun getSectionMastery(sectionId: String): SectionMastery
    suspend fun getCourseMastery(courseId: String): CourseMastery
    suspend fun getSkillsForTopic(topicId: String): List<MasterySkill>
    suspend fun recordAttempt(attempt: QuestionAttempt): TopicMastery
    suspend fun recordAttempts(attempts: List<QuestionAttempt>): Map<String, TopicMastery>
    suspend fun resetTopicMastery(topicId: String)
    suspend fun resetAllMastery()
    suspend fun explainSkill(skillId: String, topicId: String): MasteryExplanation
}
