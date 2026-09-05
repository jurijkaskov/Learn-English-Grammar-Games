package com.learnenglish.grammargames.feature.onboarding.navigation

import androidx.navigation3.runtime.NavEntry
import com.learnenglish.grammargames.core.navigation.AppNavKey
import com.learnenglish.grammargames.core.navigation.AppNavigator
import com.learnenglish.grammargames.feature.onboarding.presentation.book.BookSelectionRoute
import com.learnenglish.grammargames.feature.onboarding.presentation.complete.OnboardingCompleteRoute
import com.learnenglish.grammargames.feature.onboarding.presentation.dailygoal.DailyGoalRoute
import com.learnenglish.grammargames.feature.onboarding.presentation.goal.GoalSelectionRoute
import com.learnenglish.grammargames.feature.onboarding.presentation.level.LevelSelectionRoute
import com.learnenglish.grammargames.feature.onboarding.presentation.placement.PlacementTestRoute
import com.learnenglish.grammargames.feature.onboarding.presentation.welcome.WelcomeRoute

fun onboardingNavEntry(
    key: AppNavKey,
    navigator: AppNavigator
): NavEntry<AppNavKey> = when (key) {
    AppNavKey.Welcome -> NavEntry(key) {
        WelcomeRoute(
            onGetStartedClick = { navigator.navigateToGoalSelection() }
        )
    }

    AppNavKey.GoalSelection -> NavEntry(key) {
        GoalSelectionRoute(
            onContinueClick = { navigator.navigateToLevelSelection() },
            onBackClick = { navigator.navigateBack() }
        )
    }

    AppNavKey.LevelSelection -> NavEntry(key) {
        LevelSelectionRoute(
            onContinueClick = { navigator.navigateToBookSelection() },
            onTakePlacementTestClick = { navigator.navigateToPlacementTest() },
            onBackClick = { navigator.navigateBack() }
        )
    }

    AppNavKey.PlacementTest -> NavEntry(key) {
        PlacementTestRoute(
            onContinueClick = { navigator.navigateToBookSelection() },
            onBackClick = { navigator.navigateBack() }
        )
    }

    AppNavKey.BookSelection -> NavEntry(key) {
        BookSelectionRoute(
            onContinueClick = { navigator.navigateToDailyGoal() },
            onBackClick = { navigator.navigateBack() }
        )
    }

    AppNavKey.DailyGoal -> NavEntry(key) {
        DailyGoalRoute(
            onContinueClick = { navigator.navigateToOnboardingComplete() },
            onBackClick = { navigator.navigateBack() }
        )
    }

    AppNavKey.OnboardingComplete -> NavEntry(key) {
        OnboardingCompleteRoute(
            onStartLearningClick = { navigator.finishOnboardingAndNavigateToHome() }
        )
    }

    else -> error("Unexpected onboarding key: $key")
}
