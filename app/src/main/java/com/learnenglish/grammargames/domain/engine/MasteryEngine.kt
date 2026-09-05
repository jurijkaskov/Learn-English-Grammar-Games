package com.learnenglish.grammargames.domain.engine

import com.learnenglish.grammargames.domain.model.curriculum.DifficultyLevel
import com.learnenglish.grammargames.domain.model.mastery.CourseMastery
import com.learnenglish.grammargames.domain.model.mastery.MasteryExplanation
import com.learnenglish.grammargames.domain.model.mastery.MasteryScore
import com.learnenglish.grammargames.domain.model.mastery.MasterySkill
import com.learnenglish.grammargames.domain.model.mastery.MasteryStatus
import com.learnenglish.grammargames.domain.model.mastery.QuestionAttempt
import com.learnenglish.grammargames.domain.model.mastery.SectionMastery
import com.learnenglish.grammargames.domain.model.mastery.TopicMastery
import kotlin.math.exp
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt

object MasteryEngine {

    const val ALGORITHM_VERSION: Int = 1
    const val CONFIDENCE_ATTEMPTS_THRESHOLD: Float = 6.0f
    const val DECAY_GRACE_PERIOD_DAYS: Double = 7.0
    const val DECAY_HALF_LIFE_DAYS: Double = 28.0
    const val MIN_RETENTION_FLOOR: Float = 0.50f
    const val RECENCY_DECAY_RATE: Double = 0.92
    const val MAX_EVALUATION_ATTEMPTS_WINDOW: Int = 20

    fun calculateSkillMastery(
        skillId: String,
        topicId: String,
        attempts: List<QuestionAttempt>,
        currentTime: Long = System.currentTimeMillis()
    ): MasteryScore {
        if (attempts.isEmpty()) {
            return MasteryScore(
                score = 0,
                rawAccuracy = 0.0f,
                confidence = 0.0f,
                totalAttempts = 0,
                successfulAttempts = 0,
                decayFactor = 1.0f,
                status = MasteryStatus.NOT_STARTED,
                lastPracticedTimestamp = null
            )
        }

        val totalAttempts = attempts.size
        val successfulAttempts = attempts.count { it.isCorrect }
        val rawAccuracy = successfulAttempts.toFloat() / totalAttempts
        val lastPracticedTimestamp = attempts.maxOf { it.timestamp }

        val recentAttempts = attempts.takeLast(MAX_EVALUATION_ATTEMPTS_WINDOW)
        var weightedNumerator = 0.0
        var weightedDenominator = 0.0

        for (i in recentAttempts.indices) {
            val attempt = recentAttempts[i]
            val recencyMultiplier = RECENCY_DECAY_RATE.pow((recentAttempts.size - 1 - i).toDouble())

            val difficultyWeight = when (attempt.difficulty) {
                DifficultyLevel.EASY -> 0.8
                DifficultyLevel.NORMAL -> 1.0
                DifficultyLevel.HARD -> 1.25
                DifficultyLevel.MASTERY -> 1.4
            }

            val hintMultiplier = when {
                attempt.hintsUsed == 0 -> 1.0
                attempt.hintsUsed == 1 -> 0.75
                else -> 0.50
            }

            val earnedValue = if (attempt.isCorrect) {
                1.0 * difficultyWeight * hintMultiplier
            } else {
                0.0
            }
            val maxPossibleValue = 1.0 * difficultyWeight

            weightedNumerator += earnedValue * recencyMultiplier
            weightedDenominator += maxPossibleValue * recencyMultiplier
        }

        val weightedPerformance = if (weightedDenominator > 0.0) {
            (weightedNumerator / weightedDenominator).toFloat().coerceIn(0.0f, 1.0f)
        } else {
            rawAccuracy
        }

        val confidence = min(1.0f, sqrt(totalAttempts.toFloat() / CONFIDENCE_ATTEMPTS_THRESHOLD))
        val decayFactor = calculateDecayFactor(lastPracticedTimestamp, currentTime)

        val rawScore = weightedPerformance * confidence * 100.0f
        val finalScore = (rawScore * decayFactor).roundToInt().coerceIn(0, 100)

        val status = determineStatus(
            finalScore = finalScore,
            rawScore = rawScore,
            decayFactor = decayFactor,
            totalAttempts = totalAttempts,
            rawAccuracy = rawAccuracy
        )

        return MasteryScore(
            score = finalScore,
            rawAccuracy = rawAccuracy,
            confidence = confidence,
            totalAttempts = totalAttempts,
            successfulAttempts = successfulAttempts,
            decayFactor = decayFactor,
            status = status,
            lastPracticedTimestamp = lastPracticedTimestamp
        )
    }

    fun calculateDecayFactor(lastPracticedTimestamp: Long?, currentTime: Long): Float {
        if (lastPracticedTimestamp == null) return 1.0f
        val elapsedMs = max(0L, currentTime - lastPracticedTimestamp)
        val elapsedDays = elapsedMs.toDouble() / (1000.0 * 60.0 * 60.0 * 24.0)

        if (elapsedDays <= DECAY_GRACE_PERIOD_DAYS) {
            return 1.0f
        }

        val inactiveDaysPastGrace = elapsedDays - DECAY_GRACE_PERIOD_DAYS
        val lambda = 0.693147 / DECAY_HALF_LIFE_DAYS // ln(2) / half_life
        val decayedFraction = exp(-lambda * inactiveDaysPastGrace)
        val decayFactor = MIN_RETENTION_FLOOR + (1.0f - MIN_RETENTION_FLOOR) * decayedFraction.toFloat()

        return decayFactor.coerceIn(MIN_RETENTION_FLOOR, 1.0f)
    }

    private fun determineStatus(
        finalScore: Int,
        rawScore: Float,
        decayFactor: Float,
        totalAttempts: Int,
        rawAccuracy: Float
    ): MasteryStatus {
        if (totalAttempts == 0) return MasteryStatus.NOT_STARTED

        // Poor performance with sufficient history signals needs review
        if (totalAttempts >= 3 && rawAccuracy < 0.40f) {
            return MasteryStatus.NEEDS_REVIEW
        }

        // Check if decayed from proficient / mastered
        if (rawScore >= 60.0f && decayFactor < 0.95f && finalScore < 70) {
            return MasteryStatus.NEEDS_REVIEW
        }

        return when {
            finalScore >= 85 && totalAttempts >= 6 -> MasteryStatus.MASTERED
            finalScore >= 70 && totalAttempts >= 4 -> MasteryStatus.PROFICIENT
            finalScore >= 40 && totalAttempts >= 3 -> MasteryStatus.PRACTICING
            else -> MasteryStatus.INTRODUCED
        }
    }

    fun calculateTopicMastery(
        topicId: String,
        skills: List<MasterySkill>
    ): TopicMastery {
        if (skills.isEmpty()) {
            return TopicMastery(
                topicId = topicId,
                score = 0,
                status = MasteryStatus.NOT_STARTED,
                skillsMastery = emptyList(),
                lastPracticedTimestamp = null
            )
        }

        val totalWeight = skills.sumOf { max(0.0001, it.weight.toDouble()) }
        var weightedScoreSum = 0.0
        var hasNeedsReview = false
        var allNotStarted = true
        var maxPracticeTimestamp: Long? = null

        for (skill in skills) {
            val normalizedWeight = max(0.0001, skill.weight.toDouble()) / totalWeight
            weightedScoreSum += skill.score.score * normalizedWeight

            if (skill.score.status != MasteryStatus.NOT_STARTED) {
                allNotStarted = false
            }
            if (skill.score.status == MasteryStatus.NEEDS_REVIEW) {
                hasNeedsReview = true
            }
            val skillTs = skill.score.lastPracticedTimestamp
            if (skillTs != null && (maxPracticeTimestamp == null || skillTs > maxPracticeTimestamp)) {
                maxPracticeTimestamp = skillTs
            }
        }

        val topicScore = weightedScoreSum.roundToInt().coerceIn(0, 100)
        val status = when {
            allNotStarted -> MasteryStatus.NOT_STARTED
            hasNeedsReview && topicScore < 70 -> MasteryStatus.NEEDS_REVIEW
            topicScore >= 85 -> MasteryStatus.MASTERED
            topicScore >= 70 -> MasteryStatus.PROFICIENT
            topicScore >= 40 -> MasteryStatus.PRACTICING
            else -> MasteryStatus.INTRODUCED
        }

        return TopicMastery(
            topicId = topicId,
            score = topicScore,
            status = status,
            skillsMastery = skills,
            lastPracticedTimestamp = maxPracticeTimestamp
        )
    }

    fun calculateSectionMastery(
        sectionId: String,
        topics: List<TopicMastery>
    ): SectionMastery {
        if (topics.isEmpty()) {
            return SectionMastery(
                sectionId = sectionId,
                score = 0,
                status = MasteryStatus.NOT_STARTED,
                topicsMastery = emptyList()
            )
        }

        val activeTopics = topics.filter { it.status != MasteryStatus.NOT_STARTED }
        val avgScore = if (activeTopics.isEmpty()) {
            0
        } else {
            (activeTopics.sumOf { it.score }.toDouble() / activeTopics.size).roundToInt().coerceIn(0, 100)
        }

        val status = when {
            topics.all { it.status == MasteryStatus.NOT_STARTED } -> MasteryStatus.NOT_STARTED
            topics.any { it.status == MasteryStatus.NEEDS_REVIEW } && avgScore < 70 -> MasteryStatus.NEEDS_REVIEW
            avgScore >= 85 -> MasteryStatus.MASTERED
            avgScore >= 70 -> MasteryStatus.PROFICIENT
            avgScore >= 40 -> MasteryStatus.PRACTICING
            else -> MasteryStatus.INTRODUCED
        }

        return SectionMastery(
            sectionId = sectionId,
            score = avgScore,
            status = status,
            topicsMastery = topics
        )
    }

    fun calculateCourseMastery(
        courseId: String,
        sections: List<SectionMastery>
    ): CourseMastery {
        if (sections.isEmpty()) {
            return CourseMastery(
                courseId = courseId,
                score = 0,
                status = MasteryStatus.NOT_STARTED,
                sectionsMastery = emptyList()
            )
        }

        val activeSections = sections.filter { it.status != MasteryStatus.NOT_STARTED }
        val avgScore = if (activeSections.isEmpty()) {
            0
        } else {
            (activeSections.sumOf { it.score }.toDouble() / activeSections.size).roundToInt().coerceIn(0, 100)
        }

        val status = when {
            sections.all { it.status == MasteryStatus.NOT_STARTED } -> MasteryStatus.NOT_STARTED
            sections.any { it.status == MasteryStatus.NEEDS_REVIEW } && avgScore < 70 -> MasteryStatus.NEEDS_REVIEW
            avgScore >= 85 -> MasteryStatus.MASTERED
            avgScore >= 70 -> MasteryStatus.PROFICIENT
            avgScore >= 40 -> MasteryStatus.PRACTICING
            else -> MasteryStatus.INTRODUCED
        }

        return CourseMastery(
            courseId = courseId,
            score = avgScore,
            status = status,
            sectionsMastery = sections
        )
    }

    fun explainSkillMastery(
        skill: MasterySkill,
        attempts: List<QuestionAttempt>,
        currentTime: Long = System.currentTimeMillis()
    ): MasteryExplanation {
        if (attempts.isEmpty()) {
            return MasteryExplanation(
                baseAccuracy = 0.0f,
                confidenceFactor = 0.0f,
                averageDifficultyWeight = 1.0f,
                hintPenaltyFactor = 1.0f,
                decayFactor = 1.0f,
                finalScore = 0,
                summaryText = "No practice attempts recorded yet. Score is 0% (Not Started)."
            )
        }

        val score = calculateSkillMastery(skill.id, skill.topicId, attempts, currentTime)
        val avgDiff = attempts.map {
            when (it.difficulty) {
                DifficultyLevel.EASY -> 0.8f
                DifficultyLevel.NORMAL -> 1.0f
                DifficultyLevel.HARD -> 1.25f
                DifficultyLevel.MASTERY -> 1.4f
            }
        }.average().toFloat()

        val avgHintPenalty = attempts.map {
            when {
                it.hintsUsed == 0 -> 1.0f
                it.hintsUsed == 1 -> 0.75f
                else -> 0.50f
            }
        }.average().toFloat()

        val decayPercent = ((1.0f - score.decayFactor) * 100).roundToInt()
        val accuracyPercent = (score.rawAccuracy * 100).roundToInt()
        val confidencePercent = (score.confidence * 100).roundToInt()

        val summary = buildString {
            append("Mastery Score: ${score.score}% (${score.status.name}). ")
            append("Based on ${score.totalAttempts} attempts (${score.successfulAttempts} correct, $accuracyPercent% accuracy). ")
            append("Confidence factor is $confidencePercent% (scaled for sample size). ")
            if (decayPercent > 0) {
                append("Time decay of -$decayPercent% applied due to inactivity. ")
            }
        }

        return MasteryExplanation(
            baseAccuracy = score.rawAccuracy,
            confidenceFactor = score.confidence,
            averageDifficultyWeight = avgDiff,
            hintPenaltyFactor = avgHintPenalty,
            decayFactor = score.decayFactor,
            finalScore = score.score,
            summaryText = summary
        )
    }
}
