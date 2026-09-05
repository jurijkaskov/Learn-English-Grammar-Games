package com.learnenglish.grammargames.feature.learn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learnenglish.grammargames.domain.model.curriculum.CourseId
import com.learnenglish.grammargames.domain.usecase.curriculum.GetCoursesUseCase
import com.learnenglish.grammargames.domain.usecase.curriculum.GetSectionsForCourseUseCase
import com.learnenglish.grammargames.domain.usecase.curriculum.GetTopicsForSectionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class LearnTopicUiModel(
    val id: String,
    val title: String,
    val grammarRule: String,
    val murphyUnits: String,
    val isCompleted: Boolean = false
)

sealed interface LearnUiState {
    data object Loading : LearnUiState
    data class Success(
        val courseTitle: String,
        val topics: List<LearnTopicUiModel>
    ) : LearnUiState
    data class Error(val message: String) : LearnUiState
}

@HiltViewModel
class LearnViewModel @Inject constructor(
    private val getCoursesUseCase: GetCoursesUseCase,
    private val getSectionsForCourseUseCase: GetSectionsForCourseUseCase,
    private val getTopicsForSectionUseCase: GetTopicsForSectionUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<LearnUiState>(LearnUiState.Loading)
    val uiState: StateFlow<LearnUiState> = _uiState.asStateFlow()

    init {
        loadCurriculumTopics()
    }

    fun loadCurriculumTopics() {
        viewModelScope.launch {
            _uiState.value = LearnUiState.Loading
            runCatching {
                val courses = getCoursesUseCase.getList()
                val activeCourse = courses.firstOrNull { it.isEnabled } ?: courses.firstOrNull()
                if (activeCourse == null) {
                    _uiState.value = LearnUiState.Error("No courses available in curriculum.")
                    return@launch
                }

                val sections = getSectionsForCourseUseCase(activeCourse.id)
                val allTopics = mutableListOf<LearnTopicUiModel>()

                for (section in sections) {
                    val topics = getTopicsForSectionUseCase(section.id)
                    for (t in topics) {
                        val unitsText = if (t.bookReferences.isNotEmpty()) {
                            val ref = t.bookReferences.first()
                            "${ref.bookTitle} Units ${ref.units.joinToString(", ")}"
                        } else {
                            "Curriculum Topic"
                        }
                        allTopics.add(
                            LearnTopicUiModel(
                                id = t.id.value,
                                title = t.title,
                                grammarRule = t.shortDescription ?: "Grammar practice and rule mastery",
                                murphyUnits = unitsText,
                                isCompleted = false
                            )
                        )
                    }
                }

                _uiState.value = LearnUiState.Success(
                    courseTitle = activeCourse.title,
                    topics = allTopics
                )
            }.onFailure { error ->
                _uiState.value = LearnUiState.Error(error.localizedMessage ?: "Failed to load curriculum")
            }
        }
    }
}
