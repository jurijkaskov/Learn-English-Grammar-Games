package com.learnenglish.grammargames.feature.onboarding.presentation.goal

data class LearningGoalOption(
    val id: String,
    val title: String,
    val description: String,
    val emoji: String
)

data class GoalSelectionUiState(
    val selectedGoalId: String = "fluency",
    val options: List<LearningGoalOption> = listOf(
        LearningGoalOption("fluency", "Confident Fluency", "Speak and write naturally without second-guessing grammar rules", "🗣️"),
        LearningGoalOption("exams", "Ace Grammar Exams", "Master Cambridge, IELTS, TOEFL or School tests with high accuracy", "📝"),
        LearningGoalOption("career", "Career & Professional", "Clear and polished business correspondence and presentations", "💼"),
        LearningGoalOption("fun", "Fun & Casual", "Play games, unlock dragon evolutions, and build daily habits", "🎮")
    )
)

sealed interface GoalSelectionUiAction {
    data class SelectGoal(val goalId: String) : GoalSelectionUiAction
}
