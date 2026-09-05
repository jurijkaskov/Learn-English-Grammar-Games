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
class AdvancedCurriculumMappingTest {

    @Test
    fun testAdvancedCurriculumValidationAndStructure() = runBlocking {
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
        val advancedErrors = bundle.report.errors.filter { it.entityId.contains("advanced") }
        assertEquals("Must have 0 advanced validation errors", 0, advancedErrors.size)

        // 2. Advanced Course Structure: exactly 20 sections
        val advancedCourse = bundle.courses.find { it.id == CourseId("course_advanced") }
        assertNotNull("Advanced course must exist", advancedCourse)
        assertEquals("Advanced course must have exactly 20 sections", 20, advancedCourse!!.sectionIds.size)

        // 3. Advanced sections sequential order 1..20
        val advancedSections = bundle.sections
            .filter { it.courseId == CourseId("course_advanced") }
            .sortedBy { it.order }
        assertEquals("Must have 20 advanced sections", 20, advancedSections.size)
        val sectionOrders = advancedSections.map { it.order }
        assertEquals("Advanced sections must have sequential orders 1..20", (1..20).toList(), sectionOrders)

        // 4. Advanced topics and lessons count: 100 book topics + 1 capstone = 101 topics & lessons
        val advancedTopics = bundle.topics.filter { it.id.value.startsWith("advanced_") }
        assertEquals("Advanced course must have exactly 101 topics", 101, advancedTopics.size)

        val advancedLessons = bundle.lessons.filter { it.id.value.startsWith("lesson_advanced_") }
        assertEquals("Advanced course must have exactly 101 lessons", 101, advancedLessons.size)

        val advancedActivities = bundle.activities.filter { it.id.value.startsWith("act_advanced_") }
        assertEquals("Advanced course must have exactly 101 activities", 101, advancedActivities.size)

        // 5. CEFR Level must be C1
        for (topic in advancedTopics) {
            assertEquals("Topic ${topic.id.value} CEFR must be C1", CefrLevel.C1, topic.cefrLevel)
            assertNotNull("Topic ${topic.id.value} conceptId must not be null", topic.conceptId)
            assertNotNull("Topic ${topic.id.value} conceptDepth must not be null", topic.conceptDepth)
            assertTrue(
                "Topic ${topic.id.value} conceptDepth must be valid Advanced depth (CONTROL, NUANCE, MASTERY)",
                topic.conceptDepth in setOf(ConceptDepth.CONTROL, ConceptDepth.NUANCE, ConceptDepth.MASTERY)
            )
        }

        // 6. Valid GrammarConcept references
        val conceptIds = bundle.concepts.map { it.id }.toSet()
        for (topic in advancedTopics) {
            assertTrue(
                "GrammarConcept ${topic.conceptId!!.value} for topic ${topic.id.value} must exist in catalog",
                topic.conceptId in conceptIds
            )
        }

        // 7. Check Book catalog item exists for Advanced Grammar in Use 3rd Edition
        val advancedBook = bundle.books.find { it.id.value == "advanced_grammar_in_use" }
        assertNotNull("Book catalog must contain 'advanced_grammar_in_use'", advancedBook)
        assertEquals("Advanced Grammar in Use", advancedBook!!.title)
        assertEquals("Martin Hewings", advancedBook.author)
        val thirdEdition = advancedBook.editions.find { it.id == "advanced_grammar_in_use_3" }
        assertNotNull("Third edition must exist in catalog", thirdEdition)
        assertEquals(100, thirdEdition!!.totalUnits)
    }

    @Test
    fun testAdvancedGrammarInUse100UnitsFullCoverage() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val loader = CurriculumLoader(context)
        val bundle = loader.loadCurriculum(forceReload = true)

        val advancedTopics = bundle.topics.filter { it.id.value.startsWith("advanced_") }
        val coverage = CurriculumValidator.calculateBookCoverage(
            bookId = "advanced_grammar_in_use",
            editionId = "advanced_grammar_in_use_3",
            topics = advancedTopics,
            totalUnits = 100
        )

        // Coverage assertions
        assertTrue("Book coverage must be complete", coverage.isComplete)
        assertEquals(100, coverage.totalUnits)
        assertEquals("All 100 units must be mapped", 100, coverage.mappedUnits.size)
        assertTrue("No unmapped units allowed", coverage.unmappedUnits.isEmpty())
        assertTrue("No duplicate/multi-mapped units", coverage.multiMappedUnits.isEmpty())
        assertEquals(100.0f, coverage.coveragePercentage, 0.001f)

        // Verify full unit spectrum [1..100]
        val expectedUnits = (1..100).toSet()
        assertEquals(expectedUnits, coverage.mappedUnits)
    }

    @Test
    fun testAdvancedMachineReadableMappingJsonIntegrity() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val jsonString = context.assets.open("curriculum/mappings/advanced_grammar_in_use_3_advanced.json")
            .bufferedReader()
            .use { it.readText() }

        val root = JSONObject(jsonString)
        val bookObj = root.getJSONObject("book")
        assertEquals("advanced_grammar_in_use", bookObj.getString("id"))
        assertEquals("advanced_grammar_in_use_3", bookObj.getString("editionId"))
        assertEquals(100, bookObj.getInt("totalUnits"))

        val coverageObj = root.getJSONObject("coverage")
        assertEquals(100, coverageObj.getInt("totalUnits"))
        assertEquals(100, coverageObj.getInt("mappedUnits"))
        assertEquals(0, coverageObj.getInt("unmappedUnits"))
        assertEquals(100.0, coverageObj.getDouble("coveragePercentage"), 0.001)

        val unitsArray = root.getJSONArray("units")
        assertEquals(100, unitsArray.length())

        val mappedUnits = mutableSetOf<Int>()
        for (i in 0 until unitsArray.length()) {
            val item = unitsArray.getJSONObject(i)
            val unitNum = item.getInt("unit")
            mappedUnits.add(unitNum)
            assertEquals("MAPPED", item.getString("status"))
            assertTrue(item.getString("mappedTopicId").startsWith("advanced_"))
            assertTrue(item.getString("mappedSectionId").startsWith("advanced_"))
        }

        assertEquals((1..100).toSet(), mappedUnits)
    }

    @Test
    fun testAdvancedInversionAndInformationPackagingCurriculumDepth() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val loader = CurriculumLoader(context)
        val bundle = loader.loadCurriculum(forceReload = true)
        val index = CurriculumIndex(bundle)

        // Verify Inversion Section
        val inversionSection = index.getSection(com.learnenglish.grammargames.domain.model.curriculum.SectionId("advanced_focus_inversion"))
        assertNotNull("advanced_focus_inversion section must exist", inversionSection)
        assertEquals(2, inversionSection!!.topicIds.size)

        val invTopic1 = index.getTopic(TopicId("advanced_inversion_negative_restrictive_adverbials"))
        assertNotNull(invTopic1)
        assertEquals(ConceptDepth.MASTERY, invTopic1!!.conceptDepth)
        assertEquals("concept_inversion", invTopic1.conceptId?.value)

        // Verify Information Structure Section
        val infoStructureSection = index.getSection(com.learnenglish.grammargames.domain.model.curriculum.SectionId("advanced_information_structure"))
        assertNotNull("advanced_information_structure section must exist", infoStructureSection)
        assertEquals(4, infoStructureSection!!.topicIds.size)

        val cleftTopic = index.getTopic(TopicId("advanced_cleft_sentences_it_what_focus_structures"))
        assertNotNull(cleftTopic)
        assertEquals(ConceptDepth.MASTERY, cleftTopic!!.conceptDepth)
        assertEquals("concept_cleft_sentences", cleftTopic.conceptId?.value)

        // Verify Capstone
        val capstoneTopic = index.getTopic(TopicId("advanced_final_challenge_mastery"))
        assertNotNull("Capstone topic must exist", capstoneTopic)
        assertEquals(ConceptDepth.MASTERY, capstoneTopic!!.conceptDepth)
        assertEquals(CefrLevel.C1, capstoneTopic.cefrLevel)
    }
}
