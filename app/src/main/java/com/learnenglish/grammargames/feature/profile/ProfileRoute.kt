package com.learnenglish.grammargames.feature.profile

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ProfileRoute(
    userName: String = "Grammar Explorer",
    dailyGoalMinutes: Int = 15,
    onDailyGoalChange: (Int) -> Unit = {},
    onAchievementsClick: () -> Unit = {},
    onCharacterClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    ProfileScreen(
        userName = userName,
        dailyGoalMinutes = dailyGoalMinutes,
        onDailyGoalChange = onDailyGoalChange,
        onAchievementsClick = onAchievementsClick,
        onCharacterClick = onCharacterClick,
        onSettingsClick = onSettingsClick,
        modifier = modifier
    )
}
