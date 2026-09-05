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
        val report: CurriculumValidationReport,
        val books: List<com.learnenglish.grammargames.domain.model.curriculum.GrammarBookCatalogItem> = emptyList(),
        val concepts: List<com.learnenglish.grammargames.domain.model.curriculum.GrammarConcept> = emptyList(),
        val beginnerCoverage: com.learnenglish.grammargames.core.content.curriculum.validator.BookMappingCoverageReport? = null,
        val intermediateCoverage: com.learnenglish.grammargames.core.content.curriculum.validator.BookMappingCoverageReport? = null,
        val advancedCoverage: com.learnenglish.grammargames.core.content.curriculum.validator.BookMappingCoverageReport? = null,
        val presentPerfectPastCoverage: Pair<Int, Int>? = null
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
                val beginnerTopics = bundle.topics.filter { it.id.value.startsWith("beginner_") }
                val beginnerCoverage = com.learnenglish.grammargames.core.content.curriculum.validator.CurriculumValidator.calculateBookCoverage(
                    bookId = "essential_grammar_in_use",
                    editionId = "essential_grammar_in_use_4",
                    topics = beginnerTopics,
                    totalUnits = 115
                )

                val intermediateTopics = bundle.topics.filter { it.id.value.startsWith("intermediate_") }
                val intermediateCoverage = com.learnenglish.grammargames.core.content.curriculum.validator.CurriculumValidator.calculateBookCoverage(
                    bookId = "english_grammar_in_use",
                    editionId = "english_grammar_in_use_5",
                    topics = intermediateTopics,
                    totalUnits = 145
                )

                val advancedTopics = bundle.topics.filter { it.id.value.startsWith("advanced_") }
                val advancedCoverage = com.learnenglish.grammargames.core.content.curriculum.validator.CurriculumValidator.calculateBookCoverage(
                    bookId = "advanced_grammar_in_use",
                    editionId = "advanced_grammar_in_use_3",
                    topics = advancedTopics,
                    totalUnits = 100
                )

                val ppUnits = (7..18).toSet()
                val ppMapped = intermediateCoverage.mappedUnits.intersect(ppUnits)
                val ppCoveragePair = Pair(ppMapped.size, ppUnits.size)

                _uiState.value = CurriculumInspectorUiState.Content(
                    manifestVersion = bundle.manifest.contentVersion,
                    courses = bundle.courses,
                    sections = bundle.sections,
                    topics = bundle.topics,
                    lessons = bundle.lessons,
                    activities = bundle.activities,
                    questions = bundle.questions,
                    report = bundle.report,
                    books = bundle.books,
                    concepts = bundle.concepts,
                    beginnerCoverage = beginnerCoverage,
                    intermediateCoverage = intermediateCoverage,
                    advancedCoverage = advancedCoverage,
                    presentPerfectPastCoverage = ppCoveragePair
                )
            }.onFailure { error ->
                _uiState.value = CurriculumInspectorUiState.Error(error.localizedMessage ?: "Failed to load curriculum")
            }
        }
    }
}
