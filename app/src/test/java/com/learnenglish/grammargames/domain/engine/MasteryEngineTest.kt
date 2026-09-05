package com.learnenglish.grammargames.domain.engine

import com.learnenglish.grammargames.domain.model.curriculum.DifficultyLevel
import com.learnenglish.grammargames.domain.model.mastery.MasteryScore
import com.learnenglish.grammargames.domain.model.mastery.MasterySkill
import com.learnenglish.grammargames.domain.model.mastery.MasteryStatus
import com.learnenglish.grammargames.domain.model.mastery.QuestionAttempt
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class MasteryEngineTest {

    @Test
    fun calculateSkillMastery_emptyAttempts_returnsNotStarted() {
        val score = MasteryEngine.calculateSkillMastery(
            skillId = "obj_1",
            topicId = "topic_1",
            attempts = emptyList()
        )

        assertEquals(0, score.score)
        assertEquals(MasteryStatus.NOT_STARTED, score.status)
        assertEquals(0, score.totalAttempts)
        assertEquals(0f, score.confidence, 0.001f)
    }

    @Test
    fun calculateSkillMastery_singleAttempt_returnsIntroduced() {
        val attempts = listOf(
            QuestionAttempt(
                id = "1",
                questionId = "q1",
                topicId = "topic_1",
                isCorrect = true,
                difficulty = DifficultyLevel.NORMAL,
                timestamp = System.currentTimeMillis()
            )
        )

        val score = MasteryEngine.calculateSkillMastery(
            skillId = "obj_1",
            topicId = "topic_1",
            attempts = attempts
        )

        assertEquals(1, score.totalAttempts)
        assertEquals(MasteryStatus.INTRODUCED, score.status)
        assertTrue(score.score in 1..49)
    }

    @Test
    fun calculateSkillMastery_consistentCorrectAnswers_reachesMastered() {
        val now = System.currentTimeMillis()
        val attempts = (1..6).map { idx ->
            QuestionAttempt(
                id = "att_$idx",
                questionId = "q_$idx",
                topicId = "topic_1",
                isCorrect = true,
                difficulty = DifficultyLevel.NORMAL,
                timestamp = now - ((6 - idx) * 60_000L)
            )
        }

        val score = MasteryEngine.calculateSkillMastery(
            skillId = "obj_1",
            topicId = "topic_1",
            attempts = attempts
        )

        assertEquals(6, score.totalAttempts)
        assertEquals(MasteryStatus.MASTERED, score.status)
        assertTrue("Score should be >= 90, but was ${score.score}", score.score >= 90)
    }

    @Test
    fun calculateSkillMastery_frequentMistakes_returnsNeedsReview() {
        val now = System.currentTimeMillis()
        val attempts = (1..6).map { idx ->
            QuestionAttempt(
                id = "att_$idx",
                questionId = "q_$idx",
                topicId = "topic_1",
                isCorrect = idx == 1, // Only 1 correct, 5 wrong
                difficulty = DifficultyLevel.NORMAL,
                timestamp = now - ((6 - idx) * 60_000L)
            )
        }

        val score = MasteryEngine.calculateSkillMastery(
            skillId = "obj_1",
            topicId = "topic_1",
            attempts = attempts
        )

        assertEquals(6, score.totalAttempts)
        assertEquals(MasteryStatus.NEEDS_REVIEW, score.status)
        assertTrue("Score should be < 50, but was ${score.score}", score.score < 50)
    }

    @Test
    fun calculateSkillMastery_hintPenaltyReducesScore() {
        val now = System.currentTimeMillis()
        val noHintAttempts = (1..5).map { idx ->
            QuestionAttempt(
                id = "att_nohint_$idx",
                questionId = "q_$idx",
                topicId = "topic_1",
                isCorrect = true,
                hintsUsed = 0,
                difficulty = DifficultyLevel.NORMAL,
                timestamp = now - ((5 - idx) * 60_000L)
            )
        }

        val withHintAttempts = (1..5).map { idx ->
            QuestionAttempt(
                id = "att_hint_$idx",
                questionId = "q_$idx",
                topicId = "topic_1",
                isCorrect = true,
                hintsUsed = 2,
                difficulty = DifficultyLevel.NORMAL,
                timestamp = now - ((5 - idx) * 60_000L)
            )
        }

        val noHintScore = MasteryEngine.calculateSkillMastery(
            skillId = "obj_1",
            topicId = "topic_1",
            attempts = noHintAttempts
        )
        val withHintScore = MasteryEngine.calculateSkillMastery(
            skillId = "obj_1",
            topicId = "topic_1",
            attempts = withHintAttempts
        )

        assertTrue(
            "Attempts without hints (${noHintScore.score}) should score higher than attempts with hints (${withHintScore.score})",
            noHintScore.score > withHintScore.score
        )
    }

    @Test
    fun calculateSkillMastery_timeDecayReducesScore() {
        val now = System.currentTimeMillis()
        val recentAttempts = (1..5).map { idx ->
            QuestionAttempt(
                id = "att_rec_$idx",
                questionId = "q_$idx",
                topicId = "topic_1",
                isCorrect = true,
                difficulty = DifficultyLevel.NORMAL,
                timestamp = now - ((5 - idx) * 60_000L)
            )
        }

        val thirtyDaysAgo = now - (30L * 24 * 60 * 60 * 1000)
        val oldAttempts = (1..5).map { idx ->
            QuestionAttempt(
                id = "att_old_$idx",
                questionId = "q_$idx",
                topicId = "topic_1",
                isCorrect = true,
                difficulty = DifficultyLevel.NORMAL,
                timestamp = thirtyDaysAgo - ((5 - idx) * 60_000L)
            )
        }

        val recentScore = MasteryEngine.calculateSkillMastery(
            skillId = "obj_1",
            topicId = "topic_1",
            attempts = recentAttempts,
            currentTime = now
        )
        val decayedScore = MasteryEngine.calculateSkillMastery(
            skillId = "obj_1",
            topicId = "topic_1",
            attempts = oldAttempts,
            currentTime = now
        )

        assertTrue(
            "Recent score (${recentScore.score}) should be higher than decayed score (${decayedScore.score})",
            recentScore.score > decayedScore.score
        )
        assertTrue("Decay factor should be less than 1.0", decayedScore.decayFactor < 1.0f)
    }

    @Test
    fun calculateTopicMastery_weightedAggregationIsAccurate() {
        val skill1 = MasterySkill(
            id = "obj_1",
            topicId = "topic_present_simple",
            title = "Forms",
            weight = 0.5f,
            score = MasteryScore(
                score = 80,
                rawAccuracy = 0.8f,
                confidence = 1.0f,
                totalAttempts = 5,
                successfulAttempts = 4,
                status = MasteryStatus.PROFICIENT
            )
        )
        val skill2 = MasterySkill(
            id = "obj_2",
            topicId = "topic_present_simple",
            title = "Usage",
            weight = 0.5f,
            score = MasteryScore(
                score = 60,
                rawAccuracy = 0.6f,
                confidence = 1.0f,
                totalAttempts = 5,
                successfulAttempts = 3,
                status = MasteryStatus.PRACTICING
            )
        )

        val topicMastery = MasteryEngine.calculateTopicMastery(
            topicId = "topic_present_simple",
            skills = listOf(skill1, skill2)
        )

        assertEquals(70, topicMastery.score)
        assertEquals(MasteryStatus.PROFICIENT, topicMastery.status)
        assertEquals(2, topicMastery.skillsMastery.size)
    }

    @Test
    fun explainSkillMastery_providesDiagnostics() {
        val now = System.currentTimeMillis()
        val attempts = (1..5).map { idx ->
            QuestionAttempt(
                id = "att_$idx",
                questionId = "q_$idx",
                topicId = "topic_1",
                isCorrect = true,
                difficulty = DifficultyLevel.NORMAL,
                timestamp = now - ((5 - idx) * 60_000L)
            )
        }

        val skill = MasterySkill(
            id = "skill_1",
            topicId = "topic_1",
            title = "Third person singular -s",
            weight = 1.0f,
            score = MasteryScore(score = 85, status = MasteryStatus.PROFICIENT)
        )

        val explanation = MasteryEngine.explainSkillMastery(
            skill = skill,
            attempts = attempts,
            currentTime = now
        )

        assertNotNull(explanation)
        assertTrue(explanation.summaryText.isNotEmpty())
        assertEquals(1.0f, explanation.baseAccuracy, 0.01f)
        assertTrue(explanation.confidenceFactor > 0.8f)
    }
}
