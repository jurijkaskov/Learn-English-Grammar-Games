package com.learnenglish.grammargames.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learnenglish.grammargames.domain.usecase.book.GetAvailableGrammarBooksUseCase
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
class SettingsViewModel @Inject constructor(
    private val getAvailableGrammarBooksUseCase: GetAvailableGrammarBooksUseCase,
    private val observeSelectedGrammarBookUseCase: ObserveSelectedGrammarBookUseCase,
    private val setSelectedGrammarBookUseCase: SetSelectedGrammarBookUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val books = getAvailableGrammarBooksUseCase()
            _uiState.update { it.copy(availableBooks = books) }
        }

        viewModelScope.launch {
            observeSelectedGrammarBookUseCase().collect { selected ->
                _uiState.update { current ->
                    val syllabusName = when (selected.bookId) {
                        "essential_grammar_in_use" -> "Essential Grammar in Use (A1-A2)"
                        "english_grammar_in_use" -> "English Grammar in Use (B1-B2)"
                        "advanced_grammar_in_use" -> "Advanced Grammar in Use (C1)"
                        else -> "Standard Curriculum"
                    }
                    current.copy(
                        selectedBookId = selected.bookId,
                        selectedEditionId = selected.editionId,
                        selectedSyllabus = syllabusName
                    )
                }
            }
        }
    }

    fun onAction(action: SettingsUiAction) {
        when (action) {
            is SettingsUiAction.ToggleDarkTheme -> _uiState.update { it.copy(darkThemeEnabled = action.enabled) }
            is SettingsUiAction.ToggleSound -> _uiState.update { it.copy(soundEffectsEnabled = action.enabled) }
            is SettingsUiAction.ToggleHaptic -> _uiState.update { it.copy(hapticFeedbackEnabled = action.enabled) }
            is SettingsUiAction.ToggleReminder -> _uiState.update { it.copy(dailyReminderEnabled = action.enabled) }
            is SettingsUiAction.SelectBook -> {
                viewModelScope.launch {
                    setSelectedGrammarBookUseCase(action.bookId, action.editionId)
                }
            }
        }
    }
}
