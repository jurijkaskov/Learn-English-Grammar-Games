package com.learnenglish.grammargames.feature.bootstrap.navigation

import androidx.navigation3.runtime.NavEntry
import com.learnenglish.grammargames.core.navigation.AppNavKey
import com.learnenglish.grammargames.core.navigation.AppNavigator
import com.learnenglish.grammargames.feature.bootstrap.BootstrapRoute

fun bootstrapNavEntry(
    key: AppNavKey.Bootstrap,
    navigator: AppNavigator
): NavEntry<AppNavKey> = NavEntry(key) {
    BootstrapRoute(
        onDestinationResolved = { destination ->
            navigator.navigateToRoot(destination)
        }
    )
}
