package com.learnenglish.grammargames.core.content.curriculum

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.learnenglish.grammargames.core.content.curriculum.index.CurriculumIndex
import com.learnenglish.grammargames.core.content.curriculum.loader.CurriculumLoader
import com.learnenglish.grammargames.core.content.curriculum.validator.CurriculumValidator
import com.learnenglish.grammargames.domain.model.curriculum.CefrLevel
import com.learnenglish.grammargames.domain.model.curriculum.ConceptDepth
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
class IntermediateCurriculumMappingTest {

    @Test
    fun testIntermediateCurriculumValidationAndStructure() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val loader = CurriculumLoader(context)
        val bundle = loader.loadCurriculum(forceReload = true)

        // 1. Zero validation errors
        if (!bundle.report.isValid) {
            val errorSummary = bundle.report.errors.joinToString("\n") { "[${it.entityType}] ${it.entityId}: ${it.message}" }
            throw AssertionError("Curriculum validation failed:\n$errorSummary")
        }
        assertTrue("Bundle report must be valid", bundle.report.isValid)
        assertEquals("Must have 0 validation errors", 0, bundle.report.errors.size)
        assertEquals("Must have 0 validation warnings", 0, bundle.report.warnings.size)
        val intermediateErrors = bundle.report.errors.filter { it.entityId.contains("intermediate") }
        assertEquals("Must have 0 intermediate validation errors", 0, intermediateErrors.size)

        // 2. Intermediate Course Structure: exactly 18 sections
        val intermediateCourse = bundle.courses.find { it.id == CourseId("course_intermediate") }
        assertNotNull("Intermediate course must exist", intermediateCourse)
        assertEquals("Intermediate course must have exactly 18 sections", 18, intermediateCourse!!.sectionIds.size)

        // 3. Intermediate sections sequential order 1..18
        val intermediateSections = bundle.sections
            .filter { it.courseId == CourseId("course_intermediate") }
            .sortedBy { it.order }
        assertEquals("Must have 18 intermediate sections", 18, intermediateSections.size)
        val sectionOrders = intermediateSections.map { it.order }
        assertEquals("Intermediate sections must have sequential orders 1..18", (1..18).toList(), sectionOrders)

        // 4. Intermediate topics and lessons count
        val intermediateTopics = bundle.topics.filter { it.id.value.startsWith("intermediate_") }
        assertEquals("Intermediate course must have exactly 101 topics", 101, intermediateTopics.size)

        val intermediateLessons = bundle.lessons.filter { it.id.value.startsWith("lesson_intermediate_") }
        assertEquals("Intermediate course must have exactly 101 lessons", 101, intermediateLessons.size)

        // 5. CEFR Level must be B1 or B2 only
        for (topic in intermediateTopics) {
            assertTrue(
                "Topic ${topic.id.value} CEFR must be B1 or B2 (was ${topic.cefrLevel})",
                topic.cefrLevel == CefrLevel.B1 || topic.cefrLevel == CefrLevel.B2
            )
            assertNotNull("Topic ${topic.id.value} conceptId must not be null", topic.conceptId)
            assertNotNull("Topic ${topic.id.value} conceptDepth must not be null", topic.conceptDepth)
            assertTrue(
                "Topic ${topic.id.value} conceptDepth must be valid Intermediate depth (CONTROL, CONTRAST, NUANCE, MASTERY)",
                topic.conceptDepth in setOf(ConceptDepth.CONTROL, ConceptDepth.CONTRAST, ConceptDepth.NUANCE, ConceptDepth.MASTERY)
            )
        }

        // 6. Valid GrammarConcept references
        val conceptIds = bundle.concepts.map { it.id }.toSet()
        for (topic in intermediateTopics) {
            assertTrue(
                "GrammarConcept ${topic.conceptId!!.value} for topic ${topic.id.value} must exist in catalog",
                topic.conceptId in conceptIds
            )
        }

        // 7. Check Book catalog item exists for English Grammar in Use 5th Edition
        val intermediateBook = bundle.books.find { it.id.value == "english_grammar_in_use" }
        assertNotNull("Book catalog must contain 'english_grammar_in_use'", intermediateBook)
        assertEquals("English Grammar in Use", intermediateBook!!.title)
        assertEquals("Raymond Murphy", intermediateBook.author)
        val fifthEdition = intermediateBook.editions.find { it.id == "english_grammar_in_use_5" }
        assertNotNull("Fifth edition must exist in catalog", fifthEdition)
        assertEquals(145, fifthEdition!!.totalUnits)
    }

    @Test
    fun testEnglishGrammarInUse145UnitsFullCoverage() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val loader = CurriculumLoader(context)
        val bundle = loader.loadCurriculum(forceReload = true)

        val intermediateTopics = bundle.topics.filter { it.id.value.startsWith("intermediate_") }

        val coverage = CurriculumValidator.calculateBookCoverage(
            bookId = "english_grammar_in_use",
            editionId = "english_grammar_in_use_5",
            topics = intermediateTopics,
            totalUnits = 145
        )

        // 1. Complete coverage: 145/145 units
        assertTrue("Coverage must be complete", coverage.isComplete)
        assertEquals("Total units must be 145", 145, coverage.totalUnits)
        assertEquals("Mapped units count must be 145", 145, coverage.mappedUnits.size)
        assertTrue("Unmapped units must be empty", coverage.unmappedUnits.isEmpty())
        assertTrue("Multi-mapped units must be empty", coverage.multiMappedUnits.isEmpty())
        assertTrue("Invalid units must be empty", coverage.invalidUnits.isEmpty())
        assertEquals(100.0f, coverage.coveragePercentage, 0.001f)

        // 2. All 1..145 units are accounted for with no gaps
        val expectedUnits = (1..145).toSet()
        assertEquals("Mapped units set must match 1..145 exactly", expectedUnits, coverage.mappedUnits)
    }

    @Test
    fun testPresentPerfectAndPastUnits7to18CompleteCoverage() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val loader = CurriculumLoader(context)
        val bundle = loader.loadCurriculum(forceReload = true)
        val index = CurriculumIndex(bundle)

        // Units 7 to 18 (Present Perfect and Past)
        val targetUnits = 7..18
        for (u in targetUnits) {
            val topics = index.getTopicsForBookUnit("english_grammar_in_use", u)
            assertFalse("Unit $u in Present Perfect/Past must be mapped", topics.isEmpty())
            assertEquals("Unit $u must map to exactly one topic", 1, topics.size)

            val topic = topics.first()
            assertEquals("Topic for unit $u must belong to Section 02", "intermediate_present_perfect_past", topic.sectionId.value)
            assertTrue("Topic for unit $u must be B1 or B2", topic.cefrLevel == CefrLevel.B1 || topic.cefrLevel == CefrLevel.B2)
        }
    }

    @Test
    fun testCurriculumIndexIntermediateReverseLookups() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val loader = CurriculumLoader(context)
        val bundle = loader.loadCurriculum(forceReload = true)
        val index = CurriculumIndex(bundle)

        // Verify reverse lookup for every unit 1..145
        for (unitNum in 1..145) {
            val matchedTopics = index.getTopicsForBookUnit("english_grammar_in_use", unitNum)
            assertEquals("Unit $unitNum must map to exactly one topic, but mapped to: ${matchedTopics.map { it.id.value }}", 1, matchedTopics.size)

            val topic = matchedTopics.first()
            assertTrue("Topic ${topic.id.value} must belong to intermediate course", topic.id.value.startsWith("intermediate_"))

            val lessons = index.getLessonsForBookUnit("english_grammar_in_use", unitNum)
            assertFalse("Unit $unitNum (Topic: ${topic.id.value}) must have at least one lesson, but had 0 (topicId=${topic.id.value})", lessons.isEmpty())

            val bookRefs = index.getBookUnitsForTopic(topic.id)
            assertTrue("Topic ${topic.id.value} must reference unit $unitNum", bookRefs.any { it.units.contains(unitNum) })
        }

        // Sample checks
        // Unit 1: Present Continuous
        val unit1Topics = index.getTopicsForBookUnit("english_grammar_in_use", 1)
        assertEquals(1, unit1Topics.size)
        assertEquals(TopicId("intermediate_present_simple_continuous_contrast"), unit1Topics.first().id)

        // Unit 145: Phrasal verbs 9 (away/back)
        val unit145Topics = index.getTopicsForBookUnit("english_grammar_in_use", 145)
        assertEquals(1, unit145Topics.size)
        assertEquals(TopicId("intermediate_phrasal_verbs_away_back"), unit145Topics.first().id)
    }

    @Test
    fun testIntermediateMappingJsonAssetIntegrity() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val mappingContent = context.assets.open("curriculum/mappings/english_grammar_in_use_5_intermediate.json")
            .bufferedReader().use { it.readText() }

        val json = JSONObject(mappingContent)
        val book = json.getJSONObject("book")
        assertEquals("english_grammar_in_use", book.getString("id"))
        assertEquals("Fifth Edition", book.getString("edition"))
        assertEquals(145, book.getInt("totalUnits"))

        val coverage = json.getJSONObject("coverage")
        assertEquals(145, coverage.getInt("totalUnits"))
        assertEquals(145, coverage.getInt("mappedUnits"))
        assertEquals(0, coverage.getInt("unmappedUnits"))
        assertEquals(100.0, coverage.getDouble("coveragePercentage"), 0.001)

        val unitsArray = json.getJSONArray("units")
        assertEquals(145, unitsArray.length())

        val seenUnits = mutableSetOf<Int>()
        for (i in 0 until unitsArray.length()) {
            val uObj = unitsArray.getJSONObject(i)
            val unitNum = uObj.getInt("unit")
            assertEquals(i + 1, unitNum)
            assertTrue("Unit $unitNum must not be duplicate", seenUnits.add(unitNum))
            assertEquals("MAPPED", uObj.getString("status"))
            assertFalse(uObj.getString("mappedTopicId").isBlank())
            assertFalse(uObj.getString("mappedSectionId").isBlank())
        }
    }
}
