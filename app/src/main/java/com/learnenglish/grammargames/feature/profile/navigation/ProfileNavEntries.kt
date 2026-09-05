package com.learnenglish.grammargames.feature.profile.navigation

import androidx.navigation3.runtime.NavEntry
import com.learnenglish.grammargames.core.navigation.AppNavKey
import com.learnenglish.grammargames.core.navigation.AppNavigator
import com.learnenglish.grammargames.feature.profile.ProfileRoute
import com.learnenglish.grammargames.feature.profile.achievements.AchievementsRoute
import com.learnenglish.grammargames.feature.profile.character.CharacterRoute

fun profileNavEntry(
    key: AppNavKey.Profile,
    navigator: AppNavigator
): NavEntry<AppNavKey> = NavEntry(key) {
    ProfileRoute(
        onAchievementsClick = { navigator.navigateToAchievements() },
        onCharacterClick = { navigator.navigateToCharacter() },
        onSettingsClick = { navigator.navigateToSettings() }
    )
}

fun achievementsNavEntry(
    key: AppNavKey.Achievements,
    navigator: AppNavigator
): NavEntry<AppNavKey> = NavEntry(key) {
    AchievementsRoute(
        onBackClick = { navigator.navigateBack() }
    )
}

fun characterNavEntry(
    key: AppNavKey.Character,
    navigator: AppNavigator
): NavEntry<AppNavKey> = NavEntry(key) {
    CharacterRoute(
        onBackClick = { navigator.navigateBack() }
    )
}
