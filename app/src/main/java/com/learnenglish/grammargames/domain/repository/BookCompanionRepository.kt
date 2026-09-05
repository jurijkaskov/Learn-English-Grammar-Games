package com.learnenglish.grammargames.domain.repository

import com.learnenglish.grammargames.domain.model.book.BookCompanionEditionMapping
import com.learnenglish.grammargames.domain.model.book.BookUnitItem
import com.learnenglish.grammargames.domain.model.book.GrammarBookCompanionItem
import com.learnenglish.grammargames.domain.model.book.SelectedBookCompanion
import com.learnenglish.grammargames.domain.model.book.TopicBookCompanionReference
import com.learnenglish.grammargames.domain.model.curriculum.GrammarTopic
import com.learnenglish.grammargames.domain.model.curriculum.TopicId
import kotlinx.coroutines.flow.Flow

interface BookCompanionRepository {
    suspend fun getAvailableBooks(): List<GrammarBookCompanionItem>
    suspend fun getBook(bookId: String): GrammarBookCompanionItem?
    suspend fun getEditionMapping(editionId: String): BookCompanionEditionMapping?
    suspend fun getAllEditionMappings(): List<BookCompanionEditionMapping>
    suspend fun getUnitsForBook(editionId: String): List<BookUnitItem>
    suspend fun getUnit(editionId: String, unitNumber: Int): BookUnitItem?
    suspend fun getUnitsForTopic(topicId: TopicId, editionId: String? = null): List<BookUnitItem>
    suspend fun getCompanionReferenceForTopic(topicId: TopicId): TopicBookCompanionReference?
    suspend fun getTopicsForUnit(editionId: String, unitNumber: Int): List<GrammarTopic>
    fun observeSelectedBook(): Flow<SelectedBookCompanion>
    suspend fun getSelectedBook(): SelectedBookCompanion
    suspend fun setSelectedBook(bookId: String?, editionId: String?)
}
