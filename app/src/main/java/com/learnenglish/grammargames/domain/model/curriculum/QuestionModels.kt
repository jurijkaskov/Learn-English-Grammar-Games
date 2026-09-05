package com.learnenglish.grammargames.domain.model.curriculum

data class AnswerOption(
    val id: String,
    val text: String
)

sealed interface Question {
    val id: QuestionId
    val topicId: TopicId
    val difficulty: DifficultyLevel
    val prompt: String
    val explanation: String?
    val hint: String?
    val tags: Set<QuestionTag>
    val learningObjectiveIds: List<LearningObjectiveId>
}

data class MultipleChoiceQuestion(
    override val id: QuestionId,
    override val topicId: TopicId,
    override val difficulty: DifficultyLevel,
    override val prompt: String,
    override val explanation: String?,
    override val hint: String?,
    override val tags: Set<QuestionTag>,
    override val learningObjectiveIds: List<LearningObjectiveId> = emptyList(),
    val options: List<AnswerOption>,
    val correctOptionId: String,
    val shuffleOptions: Boolean = true
) : Question

data class GapFillQuestion(
    override val id: QuestionId,
    override val topicId: TopicId,
    override val difficulty: DifficultyLevel,
    override val prompt: String,
    override val explanation: String?,
    override val hint: String?,
    override val tags: Set<QuestionTag>,
    override val learningObjectiveIds: List<LearningObjectiveId> = emptyList(),
    val sentenceWithGaps: String,
    val correctAnswers: List<String>,
    val optionsPool: List<String> = emptyList()
) : Question

data class SentenceBuilderQuestion(
    override val id: QuestionId,
    override val topicId: TopicId,
    override val difficulty: DifficultyLevel,
    override val prompt: String,
    override val explanation: String?,
    override val hint: String?,
    override val tags: Set<QuestionTag>,
    override val learningObjectiveIds: List<LearningObjectiveId> = emptyList(),
    val segments: List<String>,
    val correctOrder: List<Int>,
    val distractors: List<String> = emptyList()
) : Question

data class FindMistakeQuestion(
    override val id: QuestionId,
    override val topicId: TopicId,
    override val difficulty: DifficultyLevel,
    override val prompt: String,
    override val explanation: String?,
    override val hint: String?,
    override val tags: Set<QuestionTag>,
    override val learningObjectiveIds: List<LearningObjectiveId> = emptyList(),
    val sentenceParts: List<String>,
    val incorrectPartIndex: Int,
    val correction: String
) : Question

data class TrueFalseQuestion(
    override val id: QuestionId,
    override val topicId: TopicId,
    override val difficulty: DifficultyLevel,
    override val prompt: String,
    override val explanation: String?,
    override val hint: String?,
    override val tags: Set<QuestionTag>,
    override val learningObjectiveIds: List<LearningObjectiveId> = emptyList(),
    val statement: String,
    val isTrue: Boolean
) : Question
