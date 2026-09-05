package com.learnenglish.grammargames.feature.onboarding.presentation.book

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learnenglish.grammargames.domain.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class BookSelectionViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(BookSelectionUiState())
    val uiState: StateFlow<BookSelectionUiState> = _uiState.asStateFlow()

    fun onAction(action: BookSelectionUiAction) {
        when (action) {
            is BookSelectionUiAction.SelectBook -> {
                _uiState.update { it.copy(selectedBookId = action.bookId) }
                viewModelScope.launch {
                    userPreferencesRepository.setSelectedCourseId(action.bookId)
                }
            }
        }
    }
}
