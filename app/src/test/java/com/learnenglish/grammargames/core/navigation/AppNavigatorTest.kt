package com.learnenglish.grammargames.core.navigation

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class AppNavigatorTest {

    private lateinit var navigationState: NavigationState
    private lateinit var navigator: AppNavigator

    @Before
    fun setUp() {
        navigationState = NavigationState(AppNavKey.Home)
        navigator = DefaultAppNavigator(navigationState)
    }

    @Test
    fun onboardingTransitions_followSequentialFlow() {
        navigator.navigateTo(AppNavKey.Welcome)
        assertEquals(AppNavKey.Welcome, navigationState.currentKey)

        navigator.navigateToGoalSelection()
        assertEquals(AppNavKey.GoalSelection, navigationState.currentKey)

        navigator.navigateToLevelSelection()
        assertEquals(AppNavKey.LevelSelection, navigationState.currentKey)

        navigator.navigateToPlacementTest()
        assertEquals(AppNavKey.PlacementTest, navigationState.currentKey)

        navigator.navigateToBookSelection()
        assertEquals(AppNavKey.BookSelection, navigationState.currentKey)

        navigator.navigateToDailyGoal()
        assertEquals(AppNavKey.DailyGoal, navigationState.currentKey)

        navigator.navigateToOnboardingComplete()
        assertEquals(AppNavKey.OnboardingComplete, navigationState.currentKey)

        navigator.finishOnboardingAndNavigateToHome()
        assertEquals(AppNavKey.Home, navigationState.currentKey)
        assertEquals(1, navigationState.backStack.size)
    }

    @Test
    fun detailDestinations_navigationAndBack() {
        navigator.navigateToTopic("present_simple")
        assertEquals(AppNavKey.Topic("present_simple"), navigationState.currentKey)

        navigator.navigateToLesson("present_simple", "unit_1")
        assertEquals(AppNavKey.Lesson("present_simple", "unit_1"), navigationState.currentKey)

        navigator.navigateBack()
        assertEquals(AppNavKey.Topic("present_simple"), navigationState.currentKey)

        navigator.navigateToTest("present_simple")
        assertEquals(AppNavKey.Test("present_simple"), navigationState.currentKey)

        navigator.navigateBack()
        assertEquals(AppNavKey.Topic("present_simple"), navigationState.currentKey)
    }

    @Test
    fun gameSession_andResults_poppingBehavior() {
        navigator.navigateToGameSession("speed_challenge", "past_tenses")
        assertEquals(AppNavKey.GameSession("speed_challenge", "past_tenses"), navigationState.currentKey)

        navigator.navigateToResults("session_42", "GAME")
        assertEquals(AppNavKey.Results("session_42", "GAME"), navigationState.currentKey)

        // Calling navigateAfterResults should pop both Results and the underlying GameSession
        navigator.navigateAfterResults()
        assertEquals(AppNavKey.Home, navigationState.currentKey)
    }

    @Test
    fun mistakesAndReview_navigation() {
        navigator.navigateToMistakes()
        assertEquals(AppNavKey.Mistakes, navigationState.currentKey)

        navigator.navigateBack()
        assertEquals(AppNavKey.Home, navigationState.currentKey)

        navigator.navigateToReviewSession("MISTAKES")
        assertEquals(AppNavKey.ReviewSession("MISTAKES"), navigationState.currentKey)
    }

    @Test
    fun profileAndGamification_navigation() {
        navigator.navigateToAchievements()
        assertEquals(AppNavKey.Achievements, navigationState.currentKey)

        navigator.navigateBack()
        assertEquals(AppNavKey.Home, navigationState.currentKey)

        navigator.navigateToCharacter()
        assertEquals(AppNavKey.Character, navigationState.currentKey)

        navigator.navigateBack()
        assertEquals(AppNavKey.Home, navigationState.currentKey)

        navigator.navigateToSettings()
        assertEquals(AppNavKey.Settings, navigationState.currentKey)
    }
}
