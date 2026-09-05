package com.learnenglish.grammargames.core.designsystem.state

/**
 * Universal learning item visual states across lessons, exercises, topics, and games.
 */
enum class LearningItemState {
    DEFAULT,
    SELECTED,
    CORRECT,
    WRONG,
    LOCKED,
    COMPLETED
}

/**
 * Granular state for drill options and interactive quiz choices.
 */
enum class ExerciseAnswerState {
    DEFAULT,
    SELECTED,
    CORRECT,
    WRONG,
    DISABLED
}

/**
 * Progression state for units, topics, and chapters along the learning syllabus.
 */
enum class ContentProgressState {
    LOCKED,
    AVAILABLE,
    IN_PROGRESS,
    COMPLETED
}

/**
 * Pedagogical feedback category for bottom action panels and explanations.
 */
enum class FeedbackType {
    CORRECT,
    INCORRECT,
    HINT,
    INFO,
    WARNING
}
