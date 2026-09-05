package com.learnenglish.grammargames.core.content.curriculum.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurriculumManifestDto(
    val schemaVersion: Int = 1,
    val contentVersion: Int = 1,
    val courses: List<String> = emptyList()
)

@Serializable
data class CourseDto(
    val id: String,
    val title: String,
    val level: String, // BEGINNER, INTERMEDIATE, ADVANCED
    val description: String = "",
    val order: Int = 1,
    val sectionIds: List<String> = emptyList(),
    val isEnabled: Boolean = true,
    val cefrLevel: String = "A1"
)

@Serializable
data class SectionDto(
    val id: String,
    val courseId: String,
    val title: String,
    val description: String = "",
    val order: Int,
    val topicIds: List<String>
)

@Serializable
data class BookReferenceDto(
    val bookId: String,
    val bookTitle: String,
    val edition: String,
    val units: List<Int>
)

@Serializable
data class TopicDto(
    val id: String,
    val sectionId: String,
    val title: String,
    val shortDescription: String? = null,
    val order: Int,
    val lessonIds: List<String>,
    val prerequisites: List<String> = emptyList(),
    val difficulty: String = "NORMAL",
    val cefrLevel: String = "A1",
    val conceptId: String? = null,
    val bookReferences: List<BookReferenceDto> = emptyList(),
    val artworkId: String? = null,
    val status: String = "ACTIVE"
)

@Serializable
data class LearningObjectiveDto(
    val id: String,
    val description: String
)

@Serializable
data class LessonContentBlockDto(
    val type: String, // text, rule, formula, example, mistake, tip, note
    val id: String,
    val text: String? = null,
    val title: String? = null,
    val description: String? = null,
    val formulaPattern: String? = null,
    val formulaNote: String? = null,
    val sentence: String? = null,
    val highlightedPart: String? = null,
    val translation: String? = null,
    val incorrectSentence: String? = null,
    val correctSentence: String? = null,
    val mistakeExplanation: String? = null
)

@Serializable
data class LessonContentDto(
    val blocks: List<LessonContentBlockDto> = emptyList()
)

@Serializable
sealed interface ActivityConfigDto {
    @Serializable
    @SerialName("practice")
    data class Practice(
        val shuffleQuestions: Boolean = true,
        val allowRetry: Boolean = true,
        val showInstantExplanation: Boolean = true
    ) : ActivityConfigDto

    @Serializable
    @SerialName("test")
    data class Test(
        val questionCount: Int = 10,
        val passThreshold: Float = 0.8f,
        val timeLimitSeconds: Int? = null,
        val randomizeQuestions: Boolean = true
    ) : ActivityConfigDto

    @Serializable
    @SerialName("speed_challenge")
    data class SpeedChallenge(
        val timeLimitSeconds: Int = 60,
        val questionCount: Int = 20,
        val comboEnabled: Boolean = true,
        val bonusTimePerCorrectSec: Int = 2
    ) : ActivityConfigDto

    @Serializable
    @SerialName("crossword")
    data class Crossword(
        val gridSize: Int = 9,
        val wordCount: Int = 6,
        val allowClueReveal: Boolean = true
    ) : ActivityConfigDto

    @Serializable
    @SerialName("word_search")
    data class WordSearch(
        val gridSize: Int = 10,
        val wordCount: Int = 8,
        val allowDiagonal: Boolean = false
    ) : ActivityConfigDto
}

@Serializable
data class ActivityDto(
    val id: String,
    val lessonId: String,
    val type: String,
    val title: String,
    val order: Int,
    val questionIds: List<String> = emptyList(),
    val config: ActivityConfigDto? = null,
    val lessonContent: LessonContentDto? = null
)

@Serializable
data class LessonDto(
    val id: String,
    val topicId: String,
    val title: String,
    val order: Int,
    val activityIds: List<String>,
    val estimatedMinutes: Int = 5,
    val difficulty: String = "NORMAL",
    val learningObjectives: List<LearningObjectiveDto> = emptyList()
)

@Serializable
data class AnswerOptionDto(
    val id: String,
    val text: String
)

@Serializable
sealed interface QuestionDto {
    val id: String
    val topicId: String
    val difficulty: String
    val prompt: String
    val explanation: String?
    val hint: String?
    val tags: List<String>
    val learningObjectiveIds: List<String>

    @Serializable
    @SerialName("multiple_choice")
    data class MultipleChoice(
        override val id: String,
        override val topicId: String,
        override val difficulty: String = "NORMAL",
        override val prompt: String,
        override val explanation: String? = null,
        override val hint: String? = null,
        override val tags: List<String> = emptyList(),
        override val learningObjectiveIds: List<String> = emptyList(),
        val options: List<AnswerOptionDto>,
        val correctOptionId: String,
        val shuffleOptions: Boolean = true
    ) : QuestionDto

    @Serializable
    @SerialName("gap_fill")
    data class GapFill(
        override val id: String,
        override val topicId: String,
        override val difficulty: String = "NORMAL",
        override val prompt: String,
        override val explanation: String? = null,
        override val hint: String? = null,
        override val tags: List<String> = emptyList(),
        override val learningObjectiveIds: List<String> = emptyList(),
        val sentenceWithGaps: String,
        val correctAnswers: List<String>,
        val optionsPool: List<String> = emptyList()
    ) : QuestionDto

    @Serializable
    @SerialName("sentence_builder")
    data class SentenceBuilder(
        override val id: String,
        override val topicId: String,
        override val difficulty: String = "NORMAL",
        override val prompt: String,
        override val explanation: String? = null,
        override val hint: String? = null,
        override val tags: List<String> = emptyList(),
        override val learningObjectiveIds: List<String> = emptyList(),
        val segments: List<String>,
        val correctOrder: List<Int>,
        val distractors: List<String> = emptyList()
    ) : QuestionDto

    @Serializable
    @SerialName("find_mistake")
    data class FindMistake(
        override val id: String,
        override val topicId: String,
        override val difficulty: String = "NORMAL",
        override val prompt: String,
        override val explanation: String? = null,
        override val hint: String? = null,
        override val tags: List<String> = emptyList(),
        override val learningObjectiveIds: List<String> = emptyList(),
        val sentenceParts: List<String>,
        val incorrectPartIndex: Int,
        val correction: String
    ) : QuestionDto

    @Serializable
    @SerialName("true_false")
    data class TrueFalse(
        override val id: String,
        override val topicId: String,
        override val difficulty: String = "NORMAL",
        override val prompt: String,
        override val explanation: String? = null,
        override val hint: String? = null,
        override val tags: List<String> = emptyList(),
        override val learningObjectiveIds: List<String> = emptyList(),
        val statement: String,
        val isTrue: Boolean
    ) : QuestionDto
}
