package com.learnenglish.grammargames.feature.settings.navigation

import androidx.navigation3.runtime.NavEntry
import com.learnenglish.grammargames.core.navigation.AppNavKey
import com.learnenglish.grammargames.core.navigation.AppNavigator
import com.learnenglish.grammargames.feature.settings.SettingsRoute

fun settingsNavEntry(
    key: AppNavKey.Settings,
    navigator: AppNavigator
): NavEntry<AppNavKey> = NavEntry(key) {
    SettingsRoute(
        onBackClick = { navigator.navigateBack() },
        onOpenShowcase = { navigator.navigateTo(AppNavKey.DesignSystemShowcase) },
        onOpenCurriculumInspector = { navigator.navigateToCurriculumInspector() },
        onOpenBookCompanion = { navigator.navigateToBookCompanion() }
    )
}
