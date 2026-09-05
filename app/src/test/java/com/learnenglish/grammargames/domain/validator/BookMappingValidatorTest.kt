package com.learnenglish.grammargames.domain.validator

import com.learnenglish.grammargames.core.content.curriculum.dto.GrammarBookDto
import com.learnenglish.grammargames.core.content.curriculum.dto.TopicDto
import com.learnenglish.grammargames.data.model.book.BookMappingFileDto
import com.learnenglish.grammargames.domain.model.book.BookCompanionEditionMapping
import com.learnenglish.grammargames.domain.model.book.BookUnitItem
import java.io.File
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class BookMappingValidatorTest {

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    private lateinit var booksCatalog: List<GrammarBookDto>
    private val allTopicIds = mutableSetOf<String>()

    @Before
    fun setUp() {
        val rootDir = File(".").canonicalFile
        val assetsDir = if (File(rootDir, "src/main/assets").exists()) {
            File(rootDir, "src/main/assets")
        } else {
            File(rootDir, "app/src/main/assets")
        }

        // 1. Load book catalog
        val booksFile = File(assetsDir, "curriculum/shared/books.json")
        assertTrue("books.json must exist at ${booksFile.absolutePath}", booksFile.exists())
        booksCatalog = json.decodeFromString<List<GrammarBookDto>>(booksFile.readText())

        // 2. Load all topic IDs across beginner, intermediate, and advanced
        listOf("beginner", "intermediate", "advanced").forEach { level ->
            val topicsFile = File(assetsDir, "curriculum/$level/topics.json")
            if (topicsFile.exists()) {
                val topics = json.decodeFromString<List<TopicDto>>(topicsFile.readText())
                topics.forEach { allTopicIds.add(it.id) }
            }
        }
        assertTrue("Expected to find curriculum topics", allTopicIds.isNotEmpty())
    }

    @Test
    fun validateEssentialGrammarInUse4() {
        validateMappingFile("essential_grammar_in_use_4_beginner.json", expectedTotalUnits = 115)
    }

    @Test
    fun validateEnglishGrammarInUse5() {
        validateMappingFile("english_grammar_in_use_5_intermediate.json", expectedTotalUnits = 145)
    }

    @Test
    fun validateAdvancedGrammarInUse3() {
        validateMappingFile("advanced_grammar_in_use_3_advanced.json", expectedTotalUnits = 100)
    }

    private fun validateMappingFile(fileName: String, expectedTotalUnits: Int) {
        val rootDir = File(".").canonicalFile
        val assetsDir = if (File(rootDir, "src/main/assets").exists()) {
            File(rootDir, "src/main/assets")
        } else {
            File(rootDir, "app/src/main/assets")
        }

        val mappingFile = File(assetsDir, "curriculum/mappings/$fileName")
        assertTrue("Mapping file $fileName must exist", mappingFile.exists())

        val mappingDto = json.decodeFromString<BookMappingFileDto>(mappingFile.readText())
        assertEquals(expectedTotalUnits, mappingDto.book.totalUnits)
        assertEquals(expectedTotalUnits, mappingDto.units.size)

        val domainMapping = BookCompanionEditionMapping(
            bookId = mappingDto.book.id,
            bookTitle = mappingDto.book.title,
            author = mappingDto.book.author,
            edition = mappingDto.book.edition,
            editionId = mappingDto.book.editionId,
            publicationYear = mappingDto.book.publicationYear,
            totalUnits = mappingDto.book.totalUnits,
            targetCourseId = mappingDto.targetCourseId,
            targetCourseLevel = mappingDto.targetCourseLevel,
            cefrRange = mappingDto.cefrRange,
            units = mappingDto.units.map { u ->
                BookUnitItem(
                    unitNumber = u.unit,
                    unitTitle = u.unitTitle,
                    bookSection = u.bookSection,
                    mappedTopicId = u.mappedTopicId,
                    mappedTopicTitle = u.mappedTopicTitle,
                    mappedSectionId = u.mappedSectionId,
                    status = u.status
                )
            }
        )

        val result = BookMappingValidator.validate(
            mapping = domainMapping,
            validTopicIds = allTopicIds
        )

        val errorSummary = result.errors.joinToString("\n")
        assertTrue("Validation failed for $fileName:\n$errorSummary", result.isValid)
        assertEquals(0, result.errors.size)
        assertEquals(expectedTotalUnits, result.totalUnits)
        assertEquals(expectedTotalUnits, result.mappedUnitsCount)
        assertEquals(100.0f, result.coveragePercentage, 0.01f)
    }
}
