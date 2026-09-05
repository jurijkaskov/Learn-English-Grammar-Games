package com.learnenglish.grammargames.core.content.curriculum

import com.learnenglish.grammargames.core.content.curriculum.dto.QuestionDto
import com.learnenglish.grammargames.core.content.curriculum.mapper.CurriculumMapper
import com.learnenglish.grammargames.core.content.curriculum.validator.CurriculumValidator
import com.learnenglish.grammargames.domain.model.curriculum.AnswerOption
import com.learnenglish.grammargames.domain.model.curriculum.CefrLevel
import com.learnenglish.grammargames.domain.model.curriculum.ContentStatus
import com.learnenglish.grammargames.domain.model.curriculum.Course
import com.learnenglish.grammargames.domain.model.curriculum.CourseId
import com.learnenglish.grammargames.domain.model.CourseLevel
import com.learnenglish.grammargames.domain.model.curriculum.DifficultyLevel
import com.learnenglish.grammargames.domain.model.curriculum.FindMistakeQuestion
import com.learnenglish.grammargames.domain.model.curriculum.GapFillQuestion
import com.learnenglish.grammargames.domain.model.curriculum.GrammarSection
import com.learnenglish.grammargames.domain.model.curriculum.GrammarTopic
import com.learnenglish.grammargames.domain.model.curriculum.Lesson
import com.learnenglish.grammargames.domain.model.curriculum.LessonId
import com.learnenglish.grammargames.domain.model.curriculum.MultipleChoiceQuestion
import com.learnenglish.grammargames.domain.model.curriculum.QuestionId
import com.learnenglish.grammargames.domain.model.curriculum.QuestionTag
import com.learnenglish.grammargames.domain.model.curriculum.SectionId
import com.learnenglish.grammargames.domain.model.curriculum.SentenceBuilderQuestion
import com.learnenglish.grammargames.domain.model.curriculum.TopicId
import com.learnenglish.grammargames.domain.model.curriculum.TrueFalseQuestion
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class CurriculumEngineTest {

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    @Test
    fun `test polymorphic question deserialization and mapping`() {
        val rawJson = """
            [
              {
                "type": "multiple_choice",
                "id": "q1",
                "topicId": "t1",
                "difficulty": "EASY",
                "prompt": "Choose the correct form",
                "explanation": "He takes has",
                "hint": "He/she/it takes has",
                "tags": ["present_perfect"],
                "options": [
                  { "id": "o1", "text": "has" },
                  { "id": "o2", "text": "have" }
                ],
                "correctOptionId": "o1"
              },
              {
                "type": "gap_fill",
                "id": "q2",
                "topicId": "t1",
                "difficulty": "NORMAL",
                "prompt": "Fill in the blank",
                "sentenceWithGaps": "She [has visited] Paris.",
                "correctAnswers": ["has visited"],
                "optionsPool": ["has visited", "have visited"]
              }
            ]
        """.trimIndent()

        val dtos = json.decodeFromString<List<QuestionDto>>(rawJson)
        assertEquals(2, dtos.size)

        val q1 = CurriculumMapper.mapQuestion(dtos[0])
        assertTrue(q1 is MultipleChoiceQuestion)
        val mc = q1 as MultipleChoiceQuestion
        assertEquals("o1", mc.correctOptionId)
        assertEquals(2, mc.options.size)

        val q2 = CurriculumMapper.mapQuestion(dtos[1])
        assertTrue(q2 is GapFillQuestion)
        val gap = q2 as GapFillQuestion
        assertEquals("has visited", gap.correctAnswers.first())
    }

    @Test
    fun `test curriculum validator catches broken topic prerequisites and invalid questions`() {
        val course = Course(
            id = CourseId("c1"),
            title = "Course 1",
            level = CourseLevel.BEGINNER,
            sectionIds = listOf(SectionId("s1"))
        )
        val section = GrammarSection(
            id = SectionId("s1"),
            courseId = CourseId("c1"),
            title = "Section 1",
            order = 1,
            topicIds = listOf(TopicId("t1"))
        )
        val topic = GrammarTopic(
            id = TopicId("t1"),
            sectionId = SectionId("s1"),
            title = "Topic 1",
            order = 1,
            lessonIds = listOf(LessonId("l1")),
            prerequisites = listOf(TopicId("non_existent_topic"))
        )
        val lesson = Lesson(
            id = LessonId("l1"),
            topicId = TopicId("t1"),
            title = "Lesson 1",
            order = 1,
            activityIds = emptyList()
        )
        val badQuestion = MultipleChoiceQuestion(
            id = QuestionId("q_bad"),
            topicId = TopicId("t1"),
            difficulty = DifficultyLevel.NORMAL,
            prompt = "Select correct",
            explanation = null,
            hint = null,
            tags = setOf(QuestionTag("tag")),
            options = listOf(AnswerOption("opt_1", "One")),
            correctOptionId = "opt_invalid"
        )

        val report = CurriculumValidator.validate(
            courses = listOf(course),
            sections = listOf(section),
            topics = listOf(topic),
            lessons = listOf(lesson),
            activities = emptyList(),
            questions = listOf(badQuestion)
        )

        assertFalse("Report should have validation errors", report.isValid)
        assertTrue(report.errors.any { it.message.contains("non_existent_topic") })
        assertTrue(report.errors.any { it.message.contains("at least 2 options") })
        assertTrue(report.errors.any { it.message.contains("not found in options") })
    }

    @Test
    fun `test sentence builder permutation validation`() {
        val validQuestion = SentenceBuilderQuestion(
            id = QuestionId("q_sb_valid"),
            topicId = TopicId("t1"),
            difficulty = DifficultyLevel.NORMAL,
            prompt = "Build sentence",
            explanation = null,
            hint = null,
            tags = emptySet(),
            segments = listOf("She", "has", "arrived"),
            correctOrder = listOf(0, 1, 2)
        )

        val invalidQuestion = SentenceBuilderQuestion(
            id = QuestionId("q_sb_invalid"),
            topicId = TopicId("t1"),
            difficulty = DifficultyLevel.NORMAL,
            prompt = "Build sentence",
            explanation = null,
            hint = null,
            tags = emptySet(),
            segments = listOf("She", "has", "arrived"),
            correctOrder = listOf(0, 5, 2) // out of bounds index 5
        )

        val course = Course(CourseId("c1"), "C", CourseLevel.BEGINNER, sectionIds = listOf(SectionId("s1")))
        val section = GrammarSection(SectionId("s1"), CourseId("c1"), "S", order = 1, topicIds = listOf(TopicId("t1")))
        val topic = GrammarTopic(TopicId("t1"), SectionId("s1"), "T", order = 1, lessonIds = listOf(LessonId("l1")))
        val lesson = Lesson(LessonId("l1"), TopicId("t1"), "L", order = 1, activityIds = emptyList())

        val report = CurriculumValidator.validate(
            courses = listOf(course),
            sections = listOf(section),
            topics = listOf(topic),
            lessons = listOf(lesson),
            activities = emptyList(),
            questions = listOf(validQuestion, invalidQuestion)
        )

        assertTrue(report.errors.any { it.entityId == "q_sb_invalid" && it.message.contains("valid permutation") })
    }
}
