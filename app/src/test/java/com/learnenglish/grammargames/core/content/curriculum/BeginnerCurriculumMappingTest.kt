package com.learnenglish.grammargames.core.content.curriculum

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.learnenglish.grammargames.core.content.curriculum.index.CurriculumIndex
import com.learnenglish.grammargames.core.content.curriculum.loader.CurriculumLoader
import com.learnenglish.grammargames.core.content.curriculum.validator.CurriculumValidator
import com.learnenglish.grammargames.domain.model.curriculum.CefrLevel
import com.learnenglish.grammargames.domain.model.curriculum.CourseId
import com.learnenglish.grammargames.domain.model.curriculum.TopicId
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class BeginnerCurriculumMappingTest {

    @Test
    fun testBeginnerCurriculumValidationAndStructure() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val loader = CurriculumLoader(context)
        val bundle = loader.loadCurriculum(forceReload = true)

        // 1. Zero validation errors and warnings
        if (!bundle.report.isValid) {
            val errorSummary = bundle.report.errors.joinToString("\n") { "[${it.entityType}] ${it.entityId}: ${it.message}" }
            throw AssertionError("Curriculum validation failed:\n$errorSummary")
        }
        assertTrue("Bundle report must be valid", bundle.report.isValid)
        assertEquals("Must have 0 validation errors", 0, bundle.report.errors.size)
        assertEquals("Must have 0 validation warnings", 0, bundle.report.warnings.size)

        // 2. Beginner Course Structure
        val beginnerCourse = bundle.courses.find { it.id == CourseId("course_beginner") }
        assertNotNull("Beginner course must exist", beginnerCourse)
        assertEquals(22, beginnerCourse!!.sectionIds.size)

        val beginnerTopics = bundle.topics.filter { it.id.value.startsWith("beginner_") }
        assertEquals(56, beginnerTopics.size)

        val beginnerLessons = bundle.lessons.filter { it.id.value.startsWith("beginner_") }
        assertEquals(56, beginnerLessons.size)

        // Verify CEFR distribution (A1 and A2 only)
        for (topic in beginnerTopics) {
            assertTrue(
                "Topic ${topic.id.value} CEFR must be A1 or A2 (was ${topic.cefrLevel})",
                topic.cefrLevel == CefrLevel.A1 || topic.cefrLevel == CefrLevel.A2
            )
            assertNotNull("Topic conceptId must not be null", topic.conceptId)
        }
    }

    @Test
    fun testEssentialGrammarInUse115UnitsFullCoverage() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val loader = CurriculumLoader(context)
        val bundle = loader.loadCurriculum(forceReload = true)

        val beginnerTopics = bundle.topics.filter { it.id.value.startsWith("beginner_") }

        val coverage = CurriculumValidator.calculateBookCoverage(
            bookId = "essential_grammar_in_use",
            editionId = "essential_grammar_in_use_4",
            topics = beginnerTopics,
            totalUnits = 115
        )

        // 1. Complete coverage: 115/115 units
        assertTrue("Coverage must be complete", coverage.isComplete)
        assertEquals("Total units must be 115", 115, coverage.totalUnits)
        assertEquals("Mapped units count must be 115", 115, coverage.mappedUnits.size)
        assertTrue("Unmapped units must be empty", coverage.unmappedUnits.isEmpty())
        assertTrue("Multi-mapped units must be empty", coverage.multiMappedUnits.isEmpty())
        assertTrue("Invalid units must be empty", coverage.invalidUnits.isEmpty())
        assertEquals(100.0f, coverage.coveragePercentage, 0.001f)

        // 2. All 1..115 units are accounted for
        val expectedUnits = (1..115).toSet()
        assertEquals("Mapped units set must match 1..115 exactly", expectedUnits, coverage.mappedUnits)
    }

    @Test
    fun testCurriculumIndexReverseLookups() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val loader = CurriculumLoader(context)
        val bundle = loader.loadCurriculum(forceReload = true)
        val index = CurriculumIndex(bundle)

        // Verify reverse lookup for every unit 1..115
        for (unitNum in 1..115) {
            val matchedTopics = index.getTopicsForBookUnit("essential_grammar_in_use", unitNum)
            assertEquals("Unit $unitNum must map to exactly one topic", 1, matchedTopics.size)

            val topic = matchedTopics.first()
            assertTrue("Topic must belong to beginner course", topic.id.value.startsWith("beginner_"))

            val lessons = index.getLessonsForBookUnit("essential_grammar_in_use", unitNum)
            assertFalse("Unit $unitNum must have at least one lesson", lessons.isEmpty())

            val bookRefs = index.getBookUnitsForTopic(topic.id)
            assertTrue("Topic ${topic.id.value} must reference unit $unitNum", bookRefs.any { it.units.contains(unitNum) })
        }

        // Verify Unit 1 (am/is/are)
        val unit1Topics = index.getTopicsForBookUnit("essential_grammar_in_use", 1)
        assertEquals(1, unit1Topics.size)
        assertEquals(TopicId("beginner_topic_am_is_are"), unit1Topics.first().id)

        // Verify Unit 115 (phrasal verbs 2)
        val unit115Topics = index.getTopicsForBookUnit("essential_grammar_in_use", 115)
        assertEquals(1, unit115Topics.size)
        assertEquals(TopicId("beginner_topic_phrasal_verbs_basics"), unit115Topics.first().id)
    }

    @Test
    fun testBeginnerMappingJsonAssetIntegrity() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val mappingContent = context.assets.open("curriculum/mappings/essential_grammar_in_use_4_beginner.json")
            .bufferedReader().use { it.readText() }

        val json = JSONObject(mappingContent)
        val book = json.getJSONObject("book")
        assertEquals("essential_grammar_in_use", book.getString("id"))
        assertEquals("Fourth Edition", book.getString("edition"))
        assertEquals(115, book.getInt("totalUnits"))

        val coverage = json.getJSONObject("coverage")
        assertEquals(115, coverage.getInt("totalUnits"))
        assertEquals(115, coverage.getInt("mappedUnits"))
        assertEquals(0, coverage.getInt("unmappedUnits"))
        assertEquals(100.0, coverage.getDouble("coveragePercentage"), 0.001)

        val unitsArray = json.getJSONArray("units")
        assertEquals(115, unitsArray.length())

        for (i in 0 until unitsArray.length()) {
            val uObj = unitsArray.getJSONObject(i)
            assertEquals(i + 1, uObj.getInt("unit"))
            assertEquals("MAPPED", uObj.getString("status"))
            assertFalse(uObj.getString("mappedTopicId").isBlank())
            assertFalse(uObj.getString("mappedSectionId").isBlank())
            assertFalse(uObj.getString("unitTitle").isBlank())
        }
    }
}
