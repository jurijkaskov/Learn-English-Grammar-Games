package com.learnenglish.grammargames.core.content.curriculum

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.learnenglish.grammargames.core.content.curriculum.index.CurriculumIndex
import com.learnenglish.grammargames.core.content.curriculum.loader.CurriculumLoader
import com.learnenglish.grammargames.domain.model.CourseLevel
import com.learnenglish.grammargames.domain.model.curriculum.CefrLevel
import com.learnenglish.grammargames.domain.model.curriculum.CourseId
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ThreeCoursesCurriculumTest {

    @Test
    fun `test loading and validating three official courses with shared catalogs`() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val loader = CurriculumLoader(context)
        val bundle = loader.loadCurriculum(forceReload = true)

        // 1. Validation Report must be completely valid
        if (!bundle.report.isValid) {
            val errorSummary = bundle.report.errors.joinToString("\n") { "[${it.entityType}] ${it.entityId}: ${it.message}" }
            throw AssertionError("Curriculum validation failed:\n$errorSummary")
        }
        assertTrue("Bundle report must be valid", bundle.report.isValid)
        assertEquals(0, bundle.report.errors.size)

        // 2. Exactly three courses: Beginner, Intermediate, Advanced
        assertEquals(3, bundle.courses.size)
        val beginner = bundle.courses.firstOrNull { it.id == CourseId("course_beginner") }
        val intermediate = bundle.courses.firstOrNull { it.id == CourseId("course_intermediate") }
        val advanced = bundle.courses.firstOrNull { it.id == CourseId("course_advanced") }

        assertNotNull("Beginner course must exist", beginner)
        assertNotNull("Intermediate course must exist", intermediate)
        assertNotNull("Advanced course must exist", advanced)

        // 3. Levels and CEFR
        assertEquals(CourseLevel.BEGINNER, beginner!!.level)
        assertEquals(CefrLevel.A1, beginner.cefrMin)
        assertEquals(CefrLevel.A2, beginner.cefrMax)

        assertEquals(CourseLevel.INTERMEDIATE, intermediate!!.level)
        assertEquals(CefrLevel.B1, intermediate.cefrMin)
        assertEquals(CefrLevel.B2, intermediate.cefrMax)

        assertEquals(CourseLevel.ADVANCED, advanced!!.level)
        assertEquals(CefrLevel.C1, advanced.cefrMin)
        assertEquals(CefrLevel.C1, advanced.cefrMax)

        // 4. Section counts: 22 for Beginner, 16 for Intermediate, 14 for Advanced
        assertEquals(22, beginner.sectionIds.size)
        assertEquals(16, intermediate.sectionIds.size)
        assertEquals(14, advanced.sectionIds.size)
        assertEquals(52, bundle.sections.size)

        // 5. Shared Catalogs: Concepts and Books loaded
        assertTrue("Grammar concepts catalog must not be empty", bundle.concepts.isNotEmpty())
        assertTrue("Grammar books catalog must not be empty", bundle.books.isNotEmpty())

        val murphyRed = bundle.books.find { it.id.value == "essential_grammar_in_use" }
        assertNotNull("Essential Grammar in Use book must exist in catalog", murphyRed)
        val murphyBlue = bundle.books.find { it.id.value == "english_grammar_in_use" }
        assertNotNull("English Grammar in Use book must exist in catalog", murphyBlue)
        val hewingsGreen = bundle.books.find { it.id.value == "advanced_grammar_in_use" }
        assertNotNull("Advanced Grammar in Use book must exist in catalog", hewingsGreen)

        // 6. CurriculumIndex verification
        val index = CurriculumIndex(bundle)
        assertEquals(3, index.courses.size)
        assertEquals(52, index.sections.size)
        assertNotNull(index.getCourse(CourseId("course_beginner")))
        assertNotNull(index.getCourse(CourseId("course_intermediate")))
        assertNotNull(index.getCourse(CourseId("course_advanced")))
    }
}
