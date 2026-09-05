package com.learnenglish.grammargames.core.navigation

data class NavigationActions(
    val onNavigateToRoot: (AppNavKey) -> Unit,
    val onNavigateTo: (AppNavKey) -> Unit,
    val onBack: () -> Unit
)
