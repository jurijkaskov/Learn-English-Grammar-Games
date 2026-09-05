package com.learnenglish.grammargames.feature.onboarding.presentation.book

data class GrammarBookOption(
    val id: String,
    val title: String,
    val author: String,
    val levelDescription: String,
    val unitCount: Int
)

data class BookSelectionUiState(
    val selectedBookId: String = "intermediate_murphy",
    val books: List<GrammarBookOption> = listOf(
        GrammarBookOption(
            id = "essential_murphy",
            title = "Essential Grammar in Use",
            author = "Raymond Murphy",
            levelDescription = "Elementary (A1-A2) • Clear explanations and essential patterns",
            unitCount = 115
        ),
        GrammarBookOption(
            id = "intermediate_murphy",
            title = "English Grammar in Use",
            author = "Raymond Murphy",
            levelDescription = "Intermediate (B1-B2) • The world's #1 grammar reference book",
            unitCount = 145
        ),
        GrammarBookOption(
            id = "advanced_hewings",
            title = "Advanced Grammar in Use",
            author = "Martin Hewings",
            levelDescription = "Advanced (C1-C2) • Sophisticated structures and subtle distinctions",
            unitCount = 100
        )
    )
)

sealed interface BookSelectionUiAction {
    data class SelectBook(val bookId: String) : BookSelectionUiAction
}
