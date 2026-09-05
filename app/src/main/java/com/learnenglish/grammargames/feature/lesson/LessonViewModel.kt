package com.learnenglish.grammargames.feature.lesson

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learnenglish.grammargames.domain.model.curriculum.ActivityType
import com.learnenglish.grammargames.domain.model.curriculum.LessonContentBlock
import com.learnenglish.grammargames.domain.model.curriculum.LessonId
import com.learnenglish.grammargames.domain.usecase.curriculum.GetActivitiesForLessonUseCase
import com.learnenglish.grammargames.domain.usecase.curriculum.GetLessonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class LessonViewModel @Inject constructor(
    private val getLessonUseCase: GetLessonUseCase,
    private val getActivitiesForLessonUseCase: GetActivitiesForLessonUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(LessonUiState())
    val uiState: StateFlow<LessonUiState> = _uiState.asStateFlow()

    fun setLesson(topicId: String, lessonId: String) {
        viewModelScope.launch {
            val lesson = getLessonUseCase(LessonId(lessonId))
            if (lesson != null) {
                val activities = getActivitiesForLessonUseCase(lesson.id)
                val contentActivity = activities.firstOrNull { it.type == ActivityType.LESSON_CONTENT }

                val ruleSummary = if (lesson.learningObjectives.isNotEmpty()) {
                    lesson.learningObjectives.joinToString(". ") { it.description }
                } else {
                    "Study and master the core grammar pattern."
                }

                val sections = mutableListOf<LessonRuleSection>()
                if (contentActivity?.lessonContent != null) {
                    val blocks = contentActivity.lessonContent.blocks
                    for (block in blocks) {
                        when (block) {
                            is LessonContentBlock.Rule -> {
                                sections.add(
                                    LessonRuleSection(
                                        title = block.ruleTitle,
                                        explanation = block.ruleDescription,
                                        examples = emptyList()
                                    )
                                )
                            }
                            is LessonContentBlock.Formula -> {
                                sections.add(
                                    LessonRuleSection(
                                        title = "Formula Pattern",
                                        explanation = block.formulaPattern,
                                        examples = block.formulaNote?.let { listOf("Note" to it) } ?: emptyList()
                                    )
                                )
                            }
                            is LessonContentBlock.Example -> {
                                sections.add(
                                    LessonRuleSection(
                                        title = "Example Usage",
                                        explanation = block.sentence,
                                        examples = block.translation?.let { listOf("Translation" to it) } ?: emptyList()
                                    )
                                )
                            }
                            is LessonContentBlock.CommonMistake -> {
                                sections.add(
                                    LessonRuleSection(
                                        title = "Common Mistake",
                                        explanation = block.mistakeExplanation,
                                        examples = listOf(
                                            block.incorrectSentence to "INCORRECT",
                                            block.correctSentence to "CORRECT"
                                        )
                                    )
                                )
                            }
                            else -> {}
                        }
                    }
                }

                _uiState.update {
                    it.copy(
                        topicId = topicId,
                        lessonId = lessonId,
                        title = lesson.title,
                        ruleSummary = ruleSummary,
                        sections = if (sections.isNotEmpty()) sections else it.sections
                    )
                }
            } else {
                _uiState.update { it.copy(topicId = topicId, lessonId = lessonId) }
            }
        }
    }
}
