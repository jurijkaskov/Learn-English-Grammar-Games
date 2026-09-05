package com.learnenglish.grammargames.core.navigation

fun AppNavKey.isRootTab(): Boolean {
    return when (this) {
        AppNavKey.Home,
        AppNavKey.Learn,
        AppNavKey.Games,
        AppNavKey.Review,
        AppNavKey.Profile -> true
        else -> false
    }
}

fun AppNavKey.shouldShowBottomBar(): Boolean {
    return when (this) {
        AppNavKey.Home,
        AppNavKey.Learn,
        AppNavKey.Games,
        AppNavKey.Review,
        AppNavKey.Profile -> true

        // Full-screen and immersive destinations explicitly hide the bottom navigation bar
        AppNavKey.Bootstrap,
        AppNavKey.Welcome,
        AppNavKey.GoalSelection,
        AppNavKey.LevelSelection,
        AppNavKey.PlacementTest,
        AppNavKey.BookSelection,
        AppNavKey.DailyGoal,
        AppNavKey.OnboardingComplete,
        is AppNavKey.Topic,
        is AppNavKey.Lesson,
        is AppNavKey.Test,
        is AppNavKey.GameSession,
        is AppNavKey.Results,
        AppNavKey.Achievements,
        is AppNavKey.AchievementDetails,
        AppNavKey.Character,
        AppNavKey.CharacterCustomization,
        AppNavKey.Settings,
        AppNavKey.Mistakes,
        is AppNavKey.ReviewSession,
        AppNavKey.DesignSystemShowcase,
        AppNavKey.CurriculumInspector,
        is AppNavKey.BookCompanion -> false
    }
}

fun AppNavKey.isFullScreen(): Boolean {
    return !shouldShowBottomBar()
}
