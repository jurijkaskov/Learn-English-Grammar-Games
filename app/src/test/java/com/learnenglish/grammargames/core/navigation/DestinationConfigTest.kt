package com.learnenglish.grammargames.core.navigation

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class DestinationConfigTest {

    @Test
    fun rootTabs_shouldShowBottomBar_andBeRootTab() {
        val rootKeys = listOf(
            AppNavKey.Home,
            AppNavKey.Learn,
            AppNavKey.Games,
            AppNavKey.Review,
            AppNavKey.Profile
        )

        rootKeys.forEach { key ->
            assertTrue("Key $key must show bottom bar", key.shouldShowBottomBar())
            assertTrue("Key $key must be a root tab", key.isRootTab())
            assertFalse("Key $key must not be full screen", key.isFullScreen())
        }
    }

    @Test
    fun onboardingKeys_shouldHideBottomBar_andBeFullScreen() {
        val onboardingKeys = listOf(
            AppNavKey.Bootstrap,
            AppNavKey.Welcome,
            AppNavKey.GoalSelection,
            AppNavKey.LevelSelection,
            AppNavKey.PlacementTest,
            AppNavKey.BookSelection,
            AppNavKey.DailyGoal,
            AppNavKey.OnboardingComplete
        )

        onboardingKeys.forEach { key ->
            assertFalse("Key $key must hide bottom bar", key.shouldShowBottomBar())
            assertFalse("Key $key must not be a root tab", key.isRootTab())
            assertTrue("Key $key must be full screen", key.isFullScreen())
        }
    }

    @Test
    fun detailDestinations_shouldHideBottomBar() {
        val detailKeys = listOf(
            AppNavKey.Topic("topic_1"),
            AppNavKey.Lesson("topic_1", "lesson_1"),
            AppNavKey.Test("topic_1"),
            AppNavKey.GameSession("speed_challenge"),
            AppNavKey.Results("session_1"),
            AppNavKey.Mistakes,
            AppNavKey.Achievements,
            AppNavKey.Character,
            AppNavKey.Settings
        )

        detailKeys.forEach { key ->
            assertFalse("Key $key must hide bottom bar", key.shouldShowBottomBar())
            assertTrue("Key $key must be full screen", key.isFullScreen())
        }
    }
}
