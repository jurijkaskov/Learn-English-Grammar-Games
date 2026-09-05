package com.learnenglish.grammargames.feature.bookcompanion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learnenglish.grammargames.domain.usecase.book.GetAvailableGrammarBooksUseCase
import com.learnenglish.grammargames.domain.usecase.book.GetBookEditionMappingUseCase
import com.learnenglish.grammargames.domain.usecase.book.GetSelectedGrammarBookUseCase
import com.learnenglish.grammargames.domain.usecase.book.ObserveSelectedGrammarBookUseCase
import com.learnenglish.grammargames.domain.usecase.book.SetSelectedGrammarBookUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class BookCompanionViewModel @Inject constructor(
    private val getAvailableGrammarBooksUseCase: GetAvailableGrammarBooksUseCase,
    private val getBookEditionMappingUseCase: GetBookEditionMappingUseCase,
    private val getSelectedGrammarBookUseCase: GetSelectedGrammarBookUseCase,
    private val setSelectedGrammarBookUseCase: SetSelectedGrammarBookUseCase,
    private val observeSelectedGrammarBookUseCase: ObserveSelectedGrammarBookUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(BookCompanionUiState())
    val uiState: StateFlow<BookCompanionUiState> = _uiState.asStateFlow()

    private var isInitialized = false

    init {
        viewModelScope.launch {
            observeSelectedGrammarBookUseCase().collect { selected ->
                _uiState.update { current ->
                    current.copy(
                        activeUserBookId = selected.bookId,
                        activeUserEditionId = selected.editionId
                    )
                }
            }
        }
    }

    fun initialize(
        initialBookId: String?,
        initialEditionId: String?,
        initialUnitNumber: Int?
    ) {
        if (isInitialized) return
        isInitialized = true

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val books = getAvailableGrammarBooksUseCase()
            val userSelected = getSelectedGrammarBookUseCase()

            val targetBookId = initialBookId
                ?: userSelected.bookId
                ?: "english_grammar_in_use"

            val targetEditionId = initialEditionId
                ?: userSelected.editionId
                ?: books.find { it.id == targetBookId }?.editions?.firstOrNull()?.id
                ?: "english_grammar_in_use_5"

            val mapping = getBookEditionMappingUseCase(targetEditionId)
            val sections = mapping?.units?.map { it.bookSection }?.distinct() ?: emptyList()

            _uiState.update {
                it.copy(
                    isLoading = false,
                    availableBooks = books,
                    selectedBookId = targetBookId,
                    selectedEditionId = targetEditionId,
                    currentMapping = mapping,
                    availableSections = sections,
                    highlightUnitNumber = initialUnitNumber,
                    activeUserBookId = userSelected.bookId,
                    activeUserEditionId = userSelected.editionId
                )
            }
        }
    }

    fun onAction(action: BookCompanionUiAction) {
        when (action) {
            is BookCompanionUiAction.SelectBookEdition -> {
                viewModelScope.launch {
                    _uiState.update { it.copy(isLoading = true, selectedBookId = action.bookId, selectedEditionId = action.editionId) }
                    val mapping = getBookEditionMappingUseCase(action.editionId)
                    val sections = mapping?.units?.map { it.bookSection }?.distinct() ?: emptyList()
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            currentMapping = mapping,
                            availableSections = sections,
                            selectedSectionFilter = null,
                            searchQuery = "",
                            highlightUnitNumber = null
                        )
                    }
                }
            }

            is BookCompanionUiAction.SetSearchQuery -> {
                _uiState.update { it.copy(searchQuery = action.query) }
            }

            is BookCompanionUiAction.SelectSectionFilter -> {
                _uiState.update { it.copy(selectedSectionFilter = action.section) }
            }

            is BookCompanionUiAction.SetAsActiveBook -> {
                val state = _uiState.value
                viewModelScope.launch {
                    setSelectedGrammarBookUseCase(state.selectedBookId, state.selectedEditionId)
                }
            }

            is BookCompanionUiAction.DismissHighlight -> {
                _uiState.update { it.copy(highlightUnitNumber = null) }
            }

            is BookCompanionUiAction.ToggleCopyrightNotice -> {
                _uiState.update { it.copy(showCopyrightNotice = action.show) }
            }
        }
    }
}
