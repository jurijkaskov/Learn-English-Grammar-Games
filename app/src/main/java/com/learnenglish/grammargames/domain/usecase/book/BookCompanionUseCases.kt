package com.learnenglish.grammargames.domain.usecase.book

import com.learnenglish.grammargames.domain.model.book.BookCompanionEditionMapping
import com.learnenglish.grammargames.domain.model.book.BookUnitItem
import com.learnenglish.grammargames.domain.model.book.GrammarBookCompanionItem
import com.learnenglish.grammargames.domain.model.book.SelectedBookCompanion
import com.learnenglish.grammargames.domain.model.book.TopicBookCompanionReference
import com.learnenglish.grammargames.domain.model.curriculum.GrammarTopic
import com.learnenglish.grammargames.domain.model.curriculum.TopicId
import com.learnenglish.grammargames.domain.repository.BookCompanionRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetAvailableGrammarBooksUseCase @Inject constructor(
    private val repository: BookCompanionRepository
) {
    suspend operator fun invoke(): List<GrammarBookCompanionItem> {
        return repository.getAvailableBooks()
    }
}

class GetBookEditionMappingUseCase @Inject constructor(
    private val repository: BookCompanionRepository
) {
    suspend operator fun invoke(editionId: String): BookCompanionEditionMapping? {
        return repository.getEditionMapping(editionId)
    }
}

class GetBookUnitsUseCase @Inject constructor(
    private val repository: BookCompanionRepository
) {
    suspend operator fun invoke(editionId: String): List<BookUnitItem> {
        return repository.getUnitsForBook(editionId)
    }
}

class GetTopicBookCompanionUseCase @Inject constructor(
    private val repository: BookCompanionRepository
) {
    suspend operator fun invoke(topicId: TopicId): TopicBookCompanionReference? {
        return repository.getCompanionReferenceForTopic(topicId)
    }
}

class GetTopicsForBookUnitUseCase @Inject constructor(
    private val repository: BookCompanionRepository
) {
    suspend operator fun invoke(editionId: String, unitNumber: Int): List<GrammarTopic> {
        return repository.getTopicsForUnit(editionId, unitNumber)
    }
}

class ObserveSelectedGrammarBookUseCase @Inject constructor(
    private val repository: BookCompanionRepository
) {
    operator fun invoke(): Flow<SelectedBookCompanion> {
        return repository.observeSelectedBook()
    }
}

class GetSelectedGrammarBookUseCase @Inject constructor(
    private val repository: BookCompanionRepository
) {
    suspend operator fun invoke(): SelectedBookCompanion {
        return repository.getSelectedBook()
    }
}

class SetSelectedGrammarBookUseCase @Inject constructor(
    private val repository: BookCompanionRepository
) {
    suspend operator fun invoke(bookId: String?, editionId: String?) {
        repository.setSelectedBook(bookId, editionId)
    }
}
