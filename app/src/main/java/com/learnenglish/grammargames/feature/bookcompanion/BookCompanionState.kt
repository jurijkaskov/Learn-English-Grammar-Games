package com.learnenglish.grammargames.feature.bookcompanion

import com.learnenglish.grammargames.domain.model.book.BookCompanionEditionMapping
import com.learnenglish.grammargames.domain.model.book.BookUnitItem
import com.learnenglish.grammargames.domain.model.book.GrammarBookCompanionItem

data class BookCompanionUiState(
    val isLoading: Boolean = true,
    val availableBooks: List<GrammarBookCompanionItem> = emptyList(),
    val selectedBookId: String = "english_grammar_in_use",
    val selectedEditionId: String = "english_grammar_in_use_5",
    val currentMapping: BookCompanionEditionMapping? = null,
    val searchQuery: String = "",
    val availableSections: List<String> = emptyList(),
    val selectedSectionFilter: String? = null,
    val highlightUnitNumber: Int? = null,
    val activeUserBookId: String? = null,
    val activeUserEditionId: String? = null,
    val showCopyrightNotice: Boolean = false
) {
    val filteredUnits: List<BookUnitItem>
        get() {
            val allUnits = currentMapping?.units ?: return emptyList()
            return allUnits.filter { unit ->
                val matchesQuery = if (searchQuery.isBlank()) {
                    true
                } else {
                    val q = searchQuery.trim().lowercase()
                    unit.unitNumber.toString() == q ||
                        unit.unitTitle.lowercase().contains(q) ||
                        (unit.mappedTopicTitle?.lowercase()?.contains(q) == true) ||
                        unit.bookSection.lowercase().contains(q)
                }

                val matchesSection = if (selectedSectionFilter.isNullOrBlank()) {
                    true
                } else {
                    unit.bookSection == selectedSectionFilter
                }

                matchesQuery && matchesSection
            }
        }

    val isSelectedBookUserActive: Boolean
        get() = selectedBookId == activeUserBookId && selectedEditionId == activeUserEditionId
}

sealed interface BookCompanionUiAction {
    data class SelectBookEdition(val bookId: String, val editionId: String) : BookCompanionUiAction
    data class SetSearchQuery(val query: String) : BookCompanionUiAction
    data class SelectSectionFilter(val section: String?) : BookCompanionUiAction
    data object SetAsActiveBook : BookCompanionUiAction
    data object DismissHighlight : BookCompanionUiAction
    data class ToggleCopyrightNotice(val show: Boolean) : BookCompanionUiAction
}
