package com.learnenglish.grammargames.core.navigation

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class NavigationStateTest {

    @Test
    fun initialState_defaultsToHome() {
        val navState = NavigationState()
        assertEquals(AppNavKey.Home, navState.currentKey)
        assertEquals(1, navState.backStack.size)
    }

    @Test
    fun navigateToRoot_switchesDestination() {
        val navState = NavigationState()
        navState.navigateToRoot(AppNavKey.Learn)
        assertEquals(AppNavKey.Learn, navState.currentKey)

        navState.navigateToRoot(AppNavKey.Games)
        assertEquals(AppNavKey.Games, navState.currentKey)
    }

    @Test
    fun navigateToRoot_doesNotDuplicateIfAlreadyCurrent() {
        val navState = NavigationState()
        navState.navigateToRoot(AppNavKey.Home)
        assertEquals(1, navState.backStack.size)
        assertEquals(AppNavKey.Home, navState.currentKey)
    }

    @Test
    fun navigateTo_and_popBack_worksCorrectly() {
        val navState = NavigationState()
        navState.navigateTo(AppNavKey.Profile)
        assertEquals(2, navState.backStack.size)
        assertEquals(AppNavKey.Profile, navState.currentKey)

        val popped = navState.popBack()
        assertTrue(popped)
        assertEquals(1, navState.backStack.size)
        assertEquals(AppNavKey.Home, navState.currentKey)

        val poppedRoot = navState.popBack()
        assertFalse(poppedRoot)
        assertEquals(1, navState.backStack.size)
    }
}
