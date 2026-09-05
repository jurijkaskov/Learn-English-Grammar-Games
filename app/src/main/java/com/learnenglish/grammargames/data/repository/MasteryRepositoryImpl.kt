package com.learnenglish.grammargames.data.repository

import com.learnenglish.grammargames.core.database.dao.MasteryDao
import com.learnenglish.grammargames.core.database.entity.QuestionAttemptEntity
import com.learnenglish.grammargames.core.database.entity.SkillMasteryEntity
import com.learnenglish.grammargames.core.database.entity.TopicMasteryEntity
import com.learnenglish.grammargames.domain.engine.MasteryEngine
import com.learnenglish.grammargames.domain.model.curriculum.CourseId
import com.learnenglish.grammargames.domain.model.curriculum.DifficultyLevel
import com.learnenglish.grammargames.domain.model.curriculum.QuestionId
import com.learnenglish.grammargames.domain.model.curriculum.SectionId
import com.learnenglish.grammargames.domain.model.curriculum.TopicId
import com.learnenglish.grammargames.domain.model.mastery.CourseMastery
import com.learnenglish.grammargames.domain.model.mastery.MasteryExplanation
import com.learnenglish.grammargames.domain.model.mastery.MasteryScore
import com.learnenglish.grammargames.domain.model.mastery.MasterySkill
import com.learnenglish.grammargames.domain.model.mastery.MasteryStatus
import com.learnenglish.grammargames.domain.model.mastery.QuestionAttempt
import com.learnenglish.grammargames.domain.model.mastery.SectionMastery
import com.learnenglish.grammargames.domain.model.mastery.TopicMastery
import com.learnenglish.grammargames.domain.repository.CurriculumRepository
import com.learnenglish.grammargames.domain.repository.MasteryRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

@Singleton
class MasteryRepositoryImpl @Inject constructor(
    private val masteryDao: MasteryDao,
    private val curriculumRepository: CurriculumRepository
) : MasteryRepository {

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    override fun observeTopicMastery(topicId: String): Flow<TopicMastery> {
        val topicMasteryFlow = masteryDao.observeTopicMastery(topicId)
        val skillMasteriesFlow = masteryDao.observeSkillMasteriesForTopic(topicId)

        return combine(topicMasteryFlow, skillMasteriesFlow) { topicEntity, skillEntities ->
            val baseSkills = getSkillsForTopicInternal(topicId)
            val skillsMap = skillEntities.associateBy { it.skillId }

            val mergedSkills = baseSkills.map { base ->
                val entity = skillsMap[base.id]
                if (entity != null) {
                    mapEntityToSkill(entity, base)
                } else {
                    base
                }
            }

            if (topicEntity != null) {
                val status = runCatching { MasteryStatus.valueOf(topicEntity.status) }
                    .getOrDefault(MasteryStatus.NOT_STARTED)
                TopicMastery(
                    topicId = topicId,
                    score = topicEntity.score,
                    status = status,
                    skillsMastery = mergedSkills,
                    lastPracticedTimestamp = topicEntity.lastPracticedTimestamp
                )
            } else {
                MasteryEngine.calculateTopicMastery(topicId, mergedSkills)
            }
        }.flowOn(ioDispatcher)
    }

    override fun observeAllTopicMasteries(): Flow<List<TopicMastery>> {
        return masteryDao.observeAllTopicMasteries().map { entities ->
            entities.map { entity ->
                val status = runCatching { MasteryStatus.valueOf(entity.status) }
                    .getOrDefault(MasteryStatus.NOT_STARTED)
                TopicMastery(
                    topicId = entity.topicId,
                    score = entity.score,
                    status = status,
                    skillsMastery = emptyList(),
                    lastPracticedTimestamp = entity.lastPracticedTimestamp
                )
            }
        }.flowOn(ioDispatcher)
    }

    override suspend fun getTopicMastery(topicId: String): TopicMastery = withContext(ioDispatcher) {
        val baseSkills = getSkillsForTopicInternal(topicId)
        val skillEntities = masteryDao.getSkillMasteriesForTopic(topicId).associateBy { it.skillId }

        val mergedSkills = baseSkills.map { base ->
            val entity = skillEntities[base.id]
            if (entity != null) {
                mapEntityToSkill(entity, base)
            } else {
                base
            }
        }

        val topicEntity = masteryDao.getTopicMastery(topicId)
        if (topicEntity != null) {
            val status = runCatching { MasteryStatus.valueOf(topicEntity.status) }
                .getOrDefault(MasteryStatus.NOT_STARTED)
            TopicMastery(
                topicId = topicId,
                score = topicEntity.score,
                status = status,
                skillsMastery = mergedSkills,
                lastPracticedTimestamp = topicEntity.lastPracticedTimestamp
            )
        } else {
            MasteryEngine.calculateTopicMastery(topicId, mergedSkills)
        }
    }

    override suspend fun getSectionMastery(sectionId: String): SectionMastery = withContext(ioDispatcher) {
        val section = curriculumRepository.getSection(SectionId(sectionId))
        if (section == null) {
            return@withContext SectionMastery(sectionId, 0, MasteryStatus.NOT_STARTED, emptyList())
        }

        val topics = section.topicIds.map { getTopicMastery(it.value) }
        MasteryEngine.calculateSectionMastery(sectionId, topics)
    }

    override suspend fun getCourseMastery(courseId: String): CourseMastery = withContext(ioDispatcher) {
        val sections = curriculumRepository.getSectionsForCourse(CourseId(courseId))
        val sectionsMastery = sections.map { getSectionMastery(it.id.value) }
        MasteryEngine.calculateCourseMastery(courseId, sectionsMastery)
    }

    override suspend fun getSkillsForTopic(topicId: String): List<MasterySkill> = withContext(ioDispatcher) {
        val baseSkills = getSkillsForTopicInternal(topicId)
        val skillEntities = masteryDao.getSkillMasteriesForTopic(topicId).associateBy { it.skillId }

        baseSkills.map { base ->
            val entity = skillEntities[base.id]
            if (entity != null) {
                mapEntityToSkill(entity, base)
            } else {
                base
            }
        }
    }

    override suspend fun recordAttempt(attempt: QuestionAttempt): TopicMastery = withContext(ioDispatcher) {
        recordAttempts(listOf(attempt)).values.firstOrNull() ?: getTopicMastery(attempt.topicId)
    }

    override suspend fun recordAttempts(attempts: List<QuestionAttempt>): Map<String, TopicMastery> = withContext(ioDispatcher) {
        if (attempts.isEmpty()) return@withContext emptyMap()

        val normalizedAttempts = mutableListOf<QuestionAttemptEntity>()
        val topicsToUpdate = attempts.map { it.topicId }.distinct()

        for (att in attempts) {
            val resolvedSkillId = resolveSkillIdForAttempt(att)
            normalizedAttempts.add(
                QuestionAttemptEntity(
                    id = att.id,
                    questionId = att.questionId,
                    topicId = att.topicId,
                    skillId = resolvedSkillId,
                    isCorrect = att.isCorrect,
                    difficulty = att.difficulty.name,
                    hintsUsed = att.hintsUsed,
                    timeSpentMs = att.timeSpentMs,
                    timestamp = att.timestamp
                )
            )
        }

        masteryDao.insertAttempts(normalizedAttempts)

        val resultMap = mutableMapOf<String, TopicMastery>()
        val currentTime = System.currentTimeMillis()

        for (topicId in topicsToUpdate) {
            val allTopicAttempts = masteryDao.getAttemptsForTopic(topicId).map { entity ->
                val diff = runCatching { DifficultyLevel.valueOf(entity.difficulty) }
                    .getOrDefault(DifficultyLevel.NORMAL)
                QuestionAttempt(
                    id = entity.id,
                    questionId = entity.questionId,
                    topicId = entity.topicId,
                    skillId = entity.skillId,
                    isCorrect = entity.isCorrect,
                    difficulty = diff,
                    hintsUsed = entity.hintsUsed,
                    timeSpentMs = entity.timeSpentMs,
                    timestamp = entity.timestamp
                )
            }

            val baseSkills = getSkillsForTopicInternal(topicId)
            val attemptsBySkill = allTopicAttempts.groupBy { it.skillId ?: baseSkills.firstOrNull()?.id ?: "" }

            val updatedSkills = mutableListOf<MasterySkill>()
            val skillEntitiesToUpsert = mutableListOf<SkillMasteryEntity>()

            for (base in baseSkills) {
                val skillAttempts = attemptsBySkill[base.id] ?: emptyList()
                val score = MasteryEngine.calculateSkillMastery(base.id, topicId, skillAttempts, currentTime)
                val updatedSkill = base.copy(score = score)
                updatedSkills.add(updatedSkill)

                skillEntitiesToUpsert.add(
                    SkillMasteryEntity(
                        skillId = base.id,
                        topicId = topicId,
                        score = score.score,
                        rawAccuracy = score.rawAccuracy,
                        totalAttempts = score.totalAttempts,
                        successfulAttempts = score.successfulAttempts,
                        confidence = score.confidence,
                        lastPracticedTimestamp = score.lastPracticedTimestamp,
                        status = score.status.name,
                        decayFactor = score.decayFactor,
                        algorithmVersion = MasteryEngine.ALGORITHM_VERSION
                    )
                )
            }

            masteryDao.upsertSkillMasteries(skillEntitiesToUpsert)

            val topicMastery = MasteryEngine.calculateTopicMastery(topicId, updatedSkills)
            masteryDao.upsertTopicMastery(
                TopicMasteryEntity(
                    topicId = topicId,
                    score = topicMastery.score,
                    status = topicMastery.status.name,
                    skillsCount = topicMastery.totalSkillsCount,
                    masteredSkillsCount = topicMastery.masteredSkillsCount,
                    lastPracticedTimestamp = topicMastery.lastPracticedTimestamp
                )
            )

            resultMap[topicId] = topicMastery
        }

        resultMap
    }

    override suspend fun resetTopicMastery(topicId: String) = withContext(ioDispatcher) {
        masteryDao.resetTopicMastery(topicId)
    }

    override suspend fun resetAllMastery() = withContext(ioDispatcher) {
        masteryDao.resetAllMastery()
    }

    override suspend fun explainSkill(skillId: String, topicId: String): MasteryExplanation = withContext(ioDispatcher) {
        val skillAttempts = masteryDao.getAttemptsForSkill(skillId).map { entity ->
            val diff = runCatching { DifficultyLevel.valueOf(entity.difficulty) }
                .getOrDefault(DifficultyLevel.NORMAL)
            QuestionAttempt(
                id = entity.id,
                questionId = entity.questionId,
                topicId = entity.topicId,
                skillId = entity.skillId,
                isCorrect = entity.isCorrect,
                difficulty = diff,
                hintsUsed = entity.hintsUsed,
                timeSpentMs = entity.timeSpentMs,
                timestamp = entity.timestamp
            )
        }

        val skills = getSkillsForTopic(topicId)
        val targetSkill = skills.find { it.id == skillId }
            ?: MasterySkill(
                id = skillId,
                topicId = topicId,
                title = "Grammar Skill",
                weight = 1.0f
            )

        MasteryEngine.explainSkillMastery(targetSkill, skillAttempts)
    }

    private suspend fun resolveSkillIdForAttempt(attempt: QuestionAttempt): String {
        if (!attempt.skillId.isNullOrBlank()) {
            return attempt.skillId
        }

        val question = curriculumRepository.getQuestion(QuestionId(attempt.questionId))
        if (question != null && question.learningObjectiveIds.isNotEmpty()) {
            return question.learningObjectiveIds.first().value
        }

        val topicSkills = getSkillsForTopicInternal(attempt.topicId)
        return topicSkills.firstOrNull()?.id ?: "skill_${attempt.topicId}_general"
    }

    private suspend fun getSkillsForTopicInternal(topicId: String): List<MasterySkill> {
        val lessons = curriculumRepository.getLessonsForTopic(TopicId(topicId))
        val objectives = lessons.flatMap { it.learningObjectives }.distinctBy { it.id.value }

        if (objectives.isEmpty()) {
            return listOf(
                MasterySkill(
                    id = "skill_${topicId}_core",
                    topicId = topicId,
                    title = "Core Concept & Rules",
                    description = "Accurate usage of primary grammatical forms.",
                    order = 1,
                    weight = 1.0f,
                    learningObjectiveIds = emptyList()
                )
            )
        }

        return objectives.mapIndexed { index, obj ->
            MasterySkill(
                id = obj.id.value,
                topicId = topicId,
                title = formatSkillTitle(obj.description),
                description = obj.description,
                order = index + 1,
                weight = 1.0f,
                learningObjectiveIds = listOf(obj.id.value)
            )
        }
    }

    private fun formatSkillTitle(desc: String): String {
        val trimmed = desc.trim().removeSuffix(".")
        return when {
            trimmed.startsWith("Master core grammatical contrasts and rules of ", ignoreCase = true) -> {
                val sub = trimmed.removePrefix("Master core grammatical contrasts and rules of ")
                if (sub.length > 32) sub.take(30) + "..." else sub
            }
            trimmed.startsWith("Demonstrate accurate communicative usage in ", ignoreCase = true) -> {
                "Communicative Accuracy"
            }
            trimmed.length <= 36 -> trimmed
            else -> trimmed.take(33) + "..."
        }
    }

    private fun mapEntityToSkill(entity: SkillMasteryEntity, base: MasterySkill): MasterySkill {
        val status = runCatching { MasteryStatus.valueOf(entity.status) }
            .getOrDefault(MasteryStatus.NOT_STARTED)
        return base.copy(
            score = MasteryScore(
                score = entity.score,
                rawAccuracy = entity.rawAccuracy,
                confidence = entity.confidence,
                totalAttempts = entity.totalAttempts,
                successfulAttempts = entity.successfulAttempts,
                decayFactor = entity.decayFactor,
                status = status,
                lastPracticedTimestamp = entity.lastPracticedTimestamp
            )
        )
    }
}
