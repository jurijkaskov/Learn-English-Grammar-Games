package com.learnenglish.grammargames.feature.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learnenglish.grammargames.domain.model.curriculum.DifficultyLevel
import com.learnenglish.grammargames.domain.model.mastery.QuestionAttempt
import com.learnenglish.grammargames.domain.usecase.mastery.RecordQuestionAttemptUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class TestViewModel @Inject constructor(
    private val recordQuestionAttemptUseCase: RecordQuestionAttemptUseCase
) : ViewModel() {
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
                val q = current.questions.getOrNull(current.currentQuestionIndex)
                if (q != null && current.selectedOptionIndex != null) {
                    val isCorrect = current.selectedOptionIndex == q.correctIndex
                    viewModelScope.launch {
                        recordQuestionAttemptUseCase(
                            QuestionAttempt(
                                id = UUID.randomUUID().toString(),
                                questionId = q.id,
                                topicId = current.topicId,
                                isCorrect = isCorrect,
                                difficulty = DifficultyLevel.NORMAL
                            )
                        )
                    }
                }
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
