package com.learnenglish.grammargames.data.repository

import android.content.Context
import android.util.Log
import com.learnenglish.grammargames.core.content.curriculum.dto.GrammarBookDto
import com.learnenglish.grammargames.core.content.curriculum.loader.CurriculumLoader
import com.learnenglish.grammargames.core.datastore.UserPreferencesDataSource
import com.learnenglish.grammargames.data.model.book.BookMappingFileDto
import com.learnenglish.grammargames.domain.model.CourseLevel
import com.learnenglish.grammargames.domain.model.book.BookCompanionEditionMapping
import com.learnenglish.grammargames.domain.model.book.BookEditionInfo
import com.learnenglish.grammargames.domain.model.book.BookUnitItem
import com.learnenglish.grammargames.domain.model.book.GrammarBookCompanionItem
import com.learnenglish.grammargames.domain.model.book.SelectedBookCompanion
import com.learnenglish.grammargames.domain.model.book.TopicBookCompanionReference
import com.learnenglish.grammargames.domain.model.curriculum.GrammarTopic
import com.learnenglish.grammargames.domain.model.curriculum.TopicId
import com.learnenglish.grammargames.domain.repository.BookCompanionRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

@Singleton
class BookCompanionRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val curriculumLoader: CurriculumLoader,
    private val userPreferencesDataSource: UserPreferencesDataSource
) : BookCompanionRepository {

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    private val mutex = Mutex()
    private var booksCatalogCache: List<GrammarBookCompanionItem>? = null
    private var editionMappingsCache: Map<String, BookCompanionEditionMapping>? = null

    private suspend fun ensureLoaded() = withContext(Dispatchers.IO) {
        if (editionMappingsCache != null && booksCatalogCache != null) return@withContext

        mutex.withLock {
            if (editionMappingsCache != null && booksCatalogCache != null) return@withLock

            val assetManager = context.assets

            // 1. Load books catalog
            val loadedBooks = mutableListOf<GrammarBookCompanionItem>()
            runCatching {
                val booksJson = assetManager.open("curriculum/shared/books.json").bufferedReader().use { it.readText() }
                val bookDtos = json.decodeFromString<List<GrammarBookDto>>(booksJson)
                loadedBooks.addAll(
                    bookDtos.map { dto ->
                        GrammarBookCompanionItem(
                            id = dto.id,
                            title = dto.title,
                            author = dto.author,
                            targetLevel = runCatching { CourseLevel.valueOf(dto.targetLevel) }.getOrDefault(CourseLevel.BEGINNER),
                            editions = dto.editions.map { ed ->
                                BookEditionInfo(
                                    id = ed.id,
                                    editionName = ed.editionName,
                                    publicationYear = ed.publicationYear,
                                    totalUnits = ed.totalUnits
                                )
                            }
                        )
                    }
                )
            }.onFailure { Log.e(TAG, "Failed loading books.json", it) }
            booksCatalogCache = loadedBooks

            // 2. Load the 3 canonical mapping files
            val mappingsMap = mutableMapOf<String, BookCompanionEditionMapping>()
            val mappingFiles = listOf(
                "curriculum/mappings/essential_grammar_in_use_4_beginner.json",
                "curriculum/mappings/english_grammar_in_use_5_intermediate.json",
                "curriculum/mappings/advanced_grammar_in_use_3_advanced.json"
            )

            for (filePath in mappingFiles) {
                runCatching {
                    val mappingJson = assetManager.open(filePath).bufferedReader().use { it.readText() }
                    val mappingDto = json.decodeFromString<BookMappingFileDto>(mappingJson)
                    val domainUnits = mappingDto.units.map { u ->
                        BookUnitItem(
                            unitNumber = u.unit,
                            unitTitle = u.unitTitle,
                            bookSection = u.bookSection,
                            mappedTopicId = u.mappedTopicId,
                            mappedTopicTitle = u.mappedTopicTitle,
                            mappedSectionId = u.mappedSectionId,
                            status = u.status
                        )
                    }.sortedBy { it.unitNumber }

                    val mapping = BookCompanionEditionMapping(
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
                        units = domainUnits
                    )
                    mappingsMap[mapping.editionId] = mapping
                }.onFailure { Log.e(TAG, "Failed loading $filePath", it) }
            }

            editionMappingsCache = mappingsMap
        }
    }

    override suspend fun getAvailableBooks(): List<GrammarBookCompanionItem> {
        ensureLoaded()
        return booksCatalogCache ?: emptyList()
    }

    override suspend fun getBook(bookId: String): GrammarBookCompanionItem? {
        ensureLoaded()
        return booksCatalogCache?.find { it.id == bookId }
    }

    override suspend fun getEditionMapping(editionId: String): BookCompanionEditionMapping? {
        ensureLoaded()
        return editionMappingsCache?.get(editionId)
    }

    override suspend fun getAllEditionMappings(): List<BookCompanionEditionMapping> {
        ensureLoaded()
        return editionMappingsCache?.values?.toList() ?: emptyList()
    }

    override suspend fun getUnitsForBook(editionId: String): List<BookUnitItem> {
        ensureLoaded()
        return editionMappingsCache?.get(editionId)?.units ?: emptyList()
    }

    override suspend fun getUnit(editionId: String, unitNumber: Int): BookUnitItem? {
        ensureLoaded()
        return editionMappingsCache?.get(editionId)?.units?.find { it.unitNumber == unitNumber }
    }

    override suspend fun getUnitsForTopic(topicId: TopicId, editionId: String?): List<BookUnitItem> {
        ensureLoaded()
        val mappings = editionMappingsCache ?: return emptyList()
        val targetEditions = if (editionId != null) {
            listOfNotNull(mappings[editionId])
        } else {
            mappings.values.toList()
        }

        return targetEditions.flatMap { edition ->
            edition.units.filter { it.mappedTopicId == topicId.value }
        }
    }

    override suspend fun getCompanionReferenceForTopic(topicId: TopicId): TopicBookCompanionReference? {
        ensureLoaded()
        val mappings = editionMappingsCache ?: return null
        val prefs = userPreferencesDataSource.preferences.first()
        val selectedEditionId = prefs.selectedEditionId

        // If user selected an edition, check that edition first
        if (!selectedEditionId.isNullOrBlank()) {
            val selectedMapping = mappings[selectedEditionId]
            if (selectedMapping != null) {
                val matchingUnits = selectedMapping.units.filter { it.mappedTopicId == topicId.value }
                if (matchingUnits.isNotEmpty()) {
                    return TopicBookCompanionReference(
                        bookId = selectedMapping.bookId,
                        bookTitle = selectedMapping.bookTitle,
                        editionName = selectedMapping.edition,
                        editionId = selectedMapping.editionId,
                        units = matchingUnits
                    )
                }
            }
        }

        // Fallback: check all other available editions in catalog
        for (mapping in mappings.values) {
            val matchingUnits = mapping.units.filter { it.mappedTopicId == topicId.value }
            if (matchingUnits.isNotEmpty()) {
                return TopicBookCompanionReference(
                    bookId = mapping.bookId,
                    bookTitle = mapping.bookTitle,
                    editionName = mapping.edition,
                    editionId = mapping.editionId,
                    units = matchingUnits
                )
            }
        }

        return null
    }

    override suspend fun getTopicsForUnit(editionId: String, unitNumber: Int): List<GrammarTopic> {
        ensureLoaded()
        val unit = getUnit(editionId, unitNumber) ?: return emptyList()
        val bundle = curriculumLoader.loadCurriculum(forceReload = false)
        return bundle.topics.filter { it.id.value == unit.mappedTopicId }
    }

    override fun observeSelectedBook(): Flow<SelectedBookCompanion> {
        return userPreferencesDataSource.preferences.map { prefs ->
            val book = booksCatalogCache?.find { it.id == prefs.selectedBookId }
            val edition = book?.editions?.find { it.id == prefs.selectedEditionId }
            SelectedBookCompanion(
                bookId = prefs.selectedBookId,
                editionId = prefs.selectedEditionId,
                bookTitle = book?.title,
                editionName = edition?.editionName
            )
        }
    }

    override suspend fun getSelectedBook(): SelectedBookCompanion {
        ensureLoaded()
        val prefs = userPreferencesDataSource.preferences.first()
        val book = booksCatalogCache?.find { it.id == prefs.selectedBookId }
        val edition = book?.editions?.find { it.id == prefs.selectedEditionId }
        return SelectedBookCompanion(
            bookId = prefs.selectedBookId,
            editionId = prefs.selectedEditionId,
            bookTitle = book?.title,
            editionName = edition?.editionName
        )
    }

    override suspend fun setSelectedBook(bookId: String?, editionId: String?) {
        userPreferencesDataSource.setSelectedBook(bookId, editionId)
    }

    companion object {
        private const val TAG = "BookCompanionRepository"
    }
}
