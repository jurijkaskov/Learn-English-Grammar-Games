package com.learnenglish.grammargames.feature.topic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learnenglish.grammargames.domain.model.curriculum.TopicId
import com.learnenglish.grammargames.domain.usecase.book.GetTopicBookCompanionUseCase
import com.learnenglish.grammargames.domain.usecase.curriculum.GetTopicLearningPathUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class TopicViewModel @Inject constructor(
    private val getTopicLearningPathUseCase: GetTopicLearningPathUseCase,
    private val getTopicBookCompanionUseCase: GetTopicBookCompanionUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(TopicUiState())
    val uiState: StateFlow<TopicUiState> = _uiState.asStateFlow()

    fun setTopicId(topicId: String) {
        viewModelScope.launch {
            val path = getTopicLearningPathUseCase(TopicId(topicId))
            val companionInfo = getTopicBookCompanionUseCase(TopicId(topicId))

            if (path != null) {
                val bookRefText = if (companionInfo != null) {
                    val unitListStr = companionInfo.units.map { "Unit ${it.unitNumber}" }.joinToString(", ")
                    "${companionInfo.bookTitle} ($unitListStr)"
                } else if (path.topic.bookReferences.isNotEmpty()) {
                    val ref = path.topic.bookReferences.first()
                    "${ref.bookTitle} (Units ${ref.units.joinToString(", ")})"
                } else {
                    "Curriculum Study Unit"
                }

                val lessons = path.lessons.map { l ->
                    TopicLessonItem(
                        id = l.id.value,
                        title = l.title,
                        description = "${l.estimatedMinutes} min • ${l.difficulty.name.lowercase().replaceFirstChar { it.uppercase() }}",
                        isCompleted = false
                    )
                }

                _uiState.update {
                    it.copy(
                        topicId = topicId,
                        title = path.topic.title,
                        description = path.topic.shortDescription ?: "Master core grammar rules and practice with interactive activities.",
                        referenceBook = bookRefText,
                        lessons = lessons,
                        bookCompanionInfo = companionInfo
                    )
                }
            } else {
                _uiState.update { it.copy(topicId = topicId, bookCompanionInfo = companionInfo) }
            }
        }
    }
}
