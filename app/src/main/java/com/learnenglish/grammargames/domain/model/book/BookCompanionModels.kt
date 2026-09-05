package com.learnenglish.grammargames.domain.model.book

import com.learnenglish.grammargames.domain.model.CourseLevel

data class BookEditionInfo(
    val id: String,
    val editionName: String,
    val publicationYear: Int? = null,
    val totalUnits: Int
)

data class GrammarBookCompanionItem(
    val id: String,
    val title: String,
    val author: String,
    val targetLevel: CourseLevel,
    val editions: List<BookEditionInfo> = emptyList()
)

data class BookUnitItem(
    val unitNumber: Int,
    val unitTitle: String,
    val bookSection: String,
    val mappedTopicId: String,
    val mappedTopicTitle: String? = null,
    val mappedSectionId: String? = null,
    val status: String = "MAPPED"
)

data class BookCompanionEditionMapping(
    val bookId: String,
    val bookTitle: String,
    val author: String,
    val edition: String,
    val editionId: String,
    val publicationYear: Int,
    val totalUnits: Int,
    val targetCourseId: String,
    val targetCourseLevel: String,
    val cefrRange: String,
    val units: List<BookUnitItem>
) {
    val mappedUnitsCount: Int get() = units.count { it.status == "MAPPED" }
    val coveragePercentage: Float get() = if (totalUnits > 0) (mappedUnitsCount.toFloat() / totalUnits) * 100f else 0f
    val isComplete: Boolean get() = mappedUnitsCount == totalUnits && units.size == totalUnits
}

data class TopicBookCompanionReference(
    val bookId: String,
    val bookTitle: String,
    val editionName: String,
    val editionId: String,
    val units: List<BookUnitItem>
)

data class SelectedBookCompanion(
    val bookId: String?,
    val editionId: String?,
    val bookTitle: String? = null,
    val editionName: String? = null
) {
    val isConfigured: Boolean get() = !bookId.isNullOrBlank() && !editionId.isNullOrBlank()
}

data class BookMappingValidationReport(
    val bookId: String,
    val editionId: String,
    val isValid: Boolean,
    val errors: List<String> = emptyList(),
    val warnings: List<String> = emptyList(),
    val totalUnits: Int,
    val mappedUnitsCount: Int,
    val missingUnits: Set<Int> = emptySet(),
    val duplicateUnits: Set<Int> = emptySet(),
    val coveragePercentage: Float
)
