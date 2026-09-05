package com.learnenglish.grammargames.feature.onboarding.presentation.level

data class LevelOption(
    val id: String,
    val title: String,
    val cefr: String,
    val description: String
)

data class LevelSelectionUiState(
    val selectedLevelId: String = "intermediate",
    val levels: List<LevelOption> = listOf(
        LevelOption("beginner", "Beginner / Elementary", "A1 - A2", "Basic sentence structure, present/past simple, pronouns, articles"),
        LevelOption("intermediate", "Intermediate", "B1 - B2", "Perfect tenses, modals, conditionals, passive voice, relative clauses"),
        LevelOption("advanced", "Advanced Grammar", "C1", "Inversion, subjunctive, complex cleft sentences, subtle nuances")
    )
)

sealed interface LevelSelectionUiAction {
    data class SelectLevel(val levelId: String) : LevelSelectionUiAction
}
