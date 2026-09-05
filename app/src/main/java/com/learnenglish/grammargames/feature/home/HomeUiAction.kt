package com.learnenglish.grammargames.feature.home

sealed interface HomeUiAction {
    data class SelectCourse(val courseId: String) : HomeUiAction
    data class AddXp(val amount: Long) : HomeUiAction
    data class UpdateDailyGoal(val minutes: Int) : HomeUiAction
}
