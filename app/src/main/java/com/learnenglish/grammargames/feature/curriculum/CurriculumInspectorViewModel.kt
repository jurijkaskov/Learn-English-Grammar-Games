package com.learnenglish.grammargames.feature.curriculum

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learnenglish.grammargames.core.content.curriculum.loader.CurriculumLoader
import com.learnenglish.grammargames.core.content.curriculum.validator.CurriculumValidationReport
import com.learnenglish.grammargames.domain.model.curriculum.Activity
import com.learnenglish.grammargames.domain.model.curriculum.Course
import com.learnenglish.grammargames.domain.model.curriculum.GrammarSection
import com.learnenglish.grammargames.domain.model.curriculum.GrammarTopic
import com.learnenglish.grammargames.domain.model.curriculum.Lesson
import com.learnenglish.grammargames.domain.model.curriculum.Question
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface CurriculumInspectorUiState {
    data object Loading : CurriculumInspectorUiState
    data class Content(
        val manifestVersion: Int,
        val courses: List<Course>,
        val sections: List<GrammarSection>,
        val topics: List<GrammarTopic>,
        val lessons: List<Lesson>,
        val activities: List<Activity>,
        val questions: List<Question>,
        val report: CurriculumValidationReport
    ) : CurriculumInspectorUiState
    data class Error(val message: String) : CurriculumInspectorUiState
}

@HiltViewModel
class CurriculumInspectorViewModel @Inject constructor(
    private val loader: CurriculumLoader
) : ViewModel() {

    private val _uiState = MutableStateFlow<CurriculumInspectorUiState>(CurriculumInspectorUiState.Loading)
    val uiState: StateFlow<CurriculumInspectorUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun loadData(forceReload: Boolean = false) {
        viewModelScope.launch {
            _uiState.value = CurriculumInspectorUiState.Loading
            runCatching {
                val bundle = loader.loadCurriculum(forceReload = forceReload)
                _uiState.value = CurriculumInspectorUiState.Content(
                    manifestVersion = bundle.manifest.contentVersion,
                    courses = bundle.courses,
                    sections = bundle.sections,
                    topics = bundle.topics,
                    lessons = bundle.lessons,
                    activities = bundle.activities,
                    questions = bundle.questions,
                    report = bundle.report
                )
            }.onFailure { error ->
                _uiState.value = CurriculumInspectorUiState.Error(error.localizedMessage ?: "Failed to load curriculum")
            }
        }
    }
}
