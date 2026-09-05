package com.learnenglish.grammargames.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList

class NavigationState(initialKey: AppNavKey = AppNavKey.Home) {
    val backStack: SnapshotStateList<AppNavKey> = mutableStateListOf(initialKey)

    val currentKey: AppNavKey
        get() = backStack.lastOrNull() ?: AppNavKey.Home

    fun navigateToRoot(key: AppNavKey) {
        if (currentKey == key) return
        backStack.remove(key)
        backStack.add(key)
    }

    fun navigateTo(key: AppNavKey) {
        backStack.add(key)
    }

    fun popBack(): Boolean {
        if (backStack.size > 1) {
            backStack.removeAt(backStack.size - 1)
            return true
        }
        return false
    }
}

@Composable
fun rememberNavigationState(initialKey: AppNavKey = AppNavKey.Home): NavigationState {
    return remember { NavigationState(initialKey) }
}
