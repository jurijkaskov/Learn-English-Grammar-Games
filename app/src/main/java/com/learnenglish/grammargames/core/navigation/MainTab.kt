package com.learnenglish.grammargames.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.ui.graphics.vector.ImageVector

enum class MainTab(
    val key: AppNavKey,
    val title: String,
    val icon: ImageVector,
    val testTag: String
) {
    HOME(
        key = AppNavKey.Home,
        title = "Home",
        icon = Icons.Default.Home,
        testTag = "tab_home"
    ),
    LEARN(
        key = AppNavKey.Learn,
        title = "Learn",
        icon = Icons.AutoMirrored.Filled.MenuBook,
        testTag = "tab_learn"
    ),
    GAMES(
        key = AppNavKey.Games,
        title = "Games",
        icon = Icons.Default.SportsEsports,
        testTag = "tab_games"
    ),
    REVIEW(
        key = AppNavKey.Review,
        title = "Review",
        icon = Icons.Default.Refresh,
        testTag = "tab_review"
    ),
    PROFILE(
        key = AppNavKey.Profile,
        title = "Profile",
        icon = Icons.Default.Person,
        testTag = "tab_profile"
    );

    companion object {
        fun fromKey(key: AppNavKey): MainTab? = entries.firstOrNull { it.key == key }
    }
}
