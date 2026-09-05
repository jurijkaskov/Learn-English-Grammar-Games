package com.learnenglish.grammargames.feature.curriculum

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learnenglish.grammargames.core.content.curriculum.loader.CurriculumLoader
import com.learnenglish.grammargames.core.content.curriculum.validator.CurriculumValidationReport
import com.learnenglish.grammargames.domain.model.curriculum.Activity
import com.learnenglish.grammargames.domain.model.curriculum.Course
import com.learnenglish.grammargames.domain.model.curriculum.DifficultyLevel
import com.learnenglish.grammargames.domain.model.curriculum.GrammarSection
import com.learnenglish.grammargames.domain.model.curriculum.GrammarTopic
import com.learnenglish.grammargames.domain.model.curriculum.Lesson
import com.learnenglish.grammargames.domain.model.curriculum.Question
import com.learnenglish.grammargames.domain.model.mastery.CourseMastery
import com.learnenglish.grammargames.domain.model.mastery.MasteryExplanation
import com.learnenglish.grammargames.domain.model.mastery.QuestionAttempt
import com.learnenglish.grammargames.domain.model.mastery.SectionMastery
import com.learnenglish.grammargames.domain.model.mastery.TopicMastery
import com.learnenglish.grammargames.domain.repository.MasteryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
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
    private val loader: CurriculumLoader,
    private val masteryRepository: MasteryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<CurriculumInspectorUiState>(CurriculumInspectorUiState.Loading)
    val uiState: StateFlow<CurriculumInspectorUiState> = _uiState.asStateFlow()

    private val _selectedTopicMastery = MutableStateFlow<TopicMastery?>(null)
    val selectedTopicMastery: StateFlow<TopicMastery?> = _selectedTopicMastery.asStateFlow()

    private val _selectedSectionMastery = MutableStateFlow<SectionMastery?>(null)
    val selectedSectionMastery: StateFlow<SectionMastery?> = _selectedSectionMastery.asStateFlow()

    private val _selectedCourseMastery = MutableStateFlow<CourseMastery?>(null)
    val selectedCourseMastery: StateFlow<CourseMastery?> = _selectedCourseMastery.asStateFlow()

    private val _selectedExplanation = MutableStateFlow<MasteryExplanation?>(null)
    val selectedExplanation: StateFlow<MasteryExplanation?> = _selectedExplanation.asStateFlow()

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

                val firstTopic = bundle.topics.firstOrNull()?.id?.value
                if (firstTopic != null) {
                    selectTopicForMastery(firstTopic)
                }
            }.onFailure { error ->
                _uiState.value = CurriculumInspectorUiState.Error(error.localizedMessage ?: "Failed to load curriculum")
            }
        }
    }

    fun selectTopicForMastery(topicId: String) {
        viewModelScope.launch {
            val tm = masteryRepository.getTopicMastery(topicId)
            _selectedTopicMastery.value = tm
            _selectedExplanation.value = null

            val state = _uiState.value
            if (state is CurriculumInspectorUiState.Content) {
                val topic = state.topics.find { it.id.value == topicId }
                if (topic != null) {
                    val section = state.sections.find { it.id == topic.sectionId }
                    if (section != null) {
                        _selectedSectionMastery.value = masteryRepository.getSectionMastery(section.id.value)
                        _selectedCourseMastery.value = masteryRepository.getCourseMastery(section.courseId.value)
                    }
                }
            }
        }
    }

    fun simulateAttempt(topicId: String, isCorrect: Boolean, hintsUsed: Int = 0) {
        viewModelScope.launch {
            val state = _uiState.value as? CurriculumInspectorUiState.Content
            val question = state?.questions?.find { it.topicId.value == topicId }
            val questionId = question?.id?.value ?: "sim_q_${System.currentTimeMillis()}"

            val attempt = QuestionAttempt(
                id = UUID.randomUUID().toString(),
                questionId = questionId,
                topicId = topicId,
                isCorrect = isCorrect,
                difficulty = DifficultyLevel.NORMAL,
                hintsUsed = hintsUsed,
                timeSpentMs = 4500L,
                timestamp = System.currentTimeMillis()
            )
            masteryRepository.recordAttempt(attempt)
            selectTopicForMastery(topicId)
        }
    }

    fun simulateDecay(topicId: String, daysAgo: Int = 14) {
        viewModelScope.launch {
            val state = _uiState.value as? CurriculumInspectorUiState.Content
            val question = state?.questions?.find { it.topicId.value == topicId }
            val questionId = question?.id?.value ?: "sim_q_${System.currentTimeMillis()}"

            val simulatedPastTime = System.currentTimeMillis() - (daysAgo * 24L * 60 * 60 * 1000)
            val attempts = List(6) { idx ->
                QuestionAttempt(
                    id = UUID.randomUUID().toString(),
                    questionId = questionId,
                    topicId = topicId,
                    isCorrect = true,
                    difficulty = DifficultyLevel.NORMAL,
                    hintsUsed = 0,
                    timeSpentMs = 3000L,
                    timestamp = simulatedPastTime + (idx * 1000)
                )
            }
            masteryRepository.recordAttempts(attempts)
            selectTopicForMastery(topicId)
        }
    }

    fun resetTopicMastery(topicId: String) {
        viewModelScope.launch {
            masteryRepository.resetTopicMastery(topicId)
            selectTopicForMastery(topicId)
        }
    }

    fun resetAllMastery() {
        viewModelScope.launch {
            masteryRepository.resetAllMastery()
            val currentTopic = _selectedTopicMastery.value?.topicId
            if (currentTopic != null) {
                selectTopicForMastery(currentTopic)
            }
        }
    }

    fun requestExplanation(skillId: String, topicId: String) {
        viewModelScope.launch {
            _selectedExplanation.value = masteryRepository.explainSkill(skillId, topicId)
        }
    }
}
