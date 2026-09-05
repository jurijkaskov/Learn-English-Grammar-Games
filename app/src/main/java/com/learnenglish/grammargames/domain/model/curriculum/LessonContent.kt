package com.learnenglish.grammargames.domain.model.curriculum

sealed interface LessonContentBlock {
    val id: String

    data class Text(
        override val id: String,
        val markdownText: String
    ) : LessonContentBlock

    data class Rule(
        override val id: String,
        val ruleTitle: String,
        val ruleDescription: String
    ) : LessonContentBlock

    data class Formula(
        override val id: String,
        val formulaPattern: String,
        val formulaNote: String? = null
    ) : LessonContentBlock

    data class Example(
        override val id: String,
        val sentence: String,
        val highlightedPart: String? = null,
        val translation: String? = null
    ) : LessonContentBlock

    data class CommonMistake(
        override val id: String,
        val incorrectSentence: String,
        val correctSentence: String,
        val mistakeExplanation: String
    ) : LessonContentBlock

    data class Tip(
        override val id: String,
        val tipText: String
    ) : LessonContentBlock

    data class Note(
        override val id: String,
        val noteText: String
    ) : LessonContentBlock
}

data class LessonContent(
    val blocks: List<LessonContentBlock>
)
