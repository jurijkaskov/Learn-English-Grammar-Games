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
        backStack.clear()
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

    fun popBackTo(key: AppNavKey, inclusive: Boolean = false): Boolean {
        val index = backStack.indexOfLast { it == key }
        if (index != -1) {
            val targetSize = if (inclusive) index else index + 1
            while (backStack.size > targetSize && backStack.size > 1) {
                backStack.removeAt(backStack.size - 1)
            }
            return true
        }
        return false
    }

    fun replaceStack(keys: List<AppNavKey>) {
        require(keys.isNotEmpty()) { "Navigation backstack cannot be empty" }
        backStack.clear()
        backStack.addAll(keys)
    }

    fun finishOnboarding() {
        backStack.clear()
        backStack.add(AppNavKey.Home)
    }
}

@Composable
fun rememberNavigationState(initialKey: AppNavKey = AppNavKey.Home): NavigationState {
    return remember(initialKey) { NavigationState(initialKey) }
}
