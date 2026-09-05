package com.learnenglish.grammargames.feature.settings

import com.learnenglish.grammargames.domain.model.book.GrammarBookCompanionItem

data class SettingsUiState(
    val darkThemeEnabled: Boolean = false,
    val soundEffectsEnabled: Boolean = true,
    val hapticFeedbackEnabled: Boolean = true,
    val dailyReminderEnabled: Boolean = true,
    val reminderHour: Int = 19,
    val reminderMinute: Int = 0,
    val selectedSyllabus: String = "English Grammar in Use (B1-B2)",
    val selectedBookId: String? = "english_grammar_in_use",
    val selectedEditionId: String? = "english_grammar_in_use_5",
    val availableBooks: List<GrammarBookCompanionItem> = emptyList()
)

sealed interface SettingsUiAction {
    data class ToggleDarkTheme(val enabled: Boolean) : SettingsUiAction
    data class ToggleSound(val enabled: Boolean) : SettingsUiAction
    data class ToggleHaptic(val enabled: Boolean) : SettingsUiAction
    data class ToggleReminder(val enabled: Boolean) : SettingsUiAction
    data class SelectBook(val bookId: String?, val editionId: String?) : SettingsUiAction
}

