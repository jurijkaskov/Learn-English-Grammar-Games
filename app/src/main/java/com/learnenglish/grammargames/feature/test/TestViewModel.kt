package com.learnenglish.grammargames.feature.test

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class TestViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(TestUiState())
    val uiState: StateFlow<TestUiState> = _uiState.asStateFlow()

    fun setTopicId(topicId: String) {
        _uiState.update { it.copy(topicId = topicId) }
    }

    fun onAction(action: TestUiAction) {
        when (action) {
            is TestUiAction.SelectOption -> {
                _uiState.update { it.copy(selectedOptionIndex = action.index) }
            }
            TestUiAction.NextOrSubmit -> {
                val current = _uiState.value
                if (current.currentQuestionIndex + 1 < current.questions.size) {
                    _uiState.update {
                        it.copy(
                            currentQuestionIndex = it.currentQuestionIndex + 1,
                            selectedOptionIndex = null
                        )
                    }
                }
            }
        }
    }
}
