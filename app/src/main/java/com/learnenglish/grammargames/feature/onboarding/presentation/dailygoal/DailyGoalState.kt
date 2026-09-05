package com.learnenglish.grammargames.feature.onboarding.presentation.dailygoal

data class DailyGoalOption(
    val minutes: Int,
    val label: String,
    val pace: String
)

data class DailyGoalUiState(
    val selectedMinutes: Int = 15,
    val options: List<DailyGoalOption> = listOf(
        DailyGoalOption(5, "Casual", "5 minutes / day"),
        DailyGoalOption(10, "Regular", "10 minutes / day"),
        DailyGoalOption(15, "Committed", "15 minutes / day"),
        DailyGoalOption(20, "Intensive", "20 minutes / day")
    )
)

sealed interface DailyGoalUiAction {
    data class SelectGoal(val minutes: Int) : DailyGoalUiAction
}
