package com.learnenglish.grammargames.core.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavEntryDecorator
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.learnenglish.grammargames.feature.games.GamesRoute
import com.learnenglish.grammargames.feature.home.HomeRoute
import com.learnenglish.grammargames.feature.learn.LearnRoute
import com.learnenglish.grammargames.feature.profile.ProfileRoute
import com.learnenglish.grammargames.feature.review.ReviewRoute

data class BottomNavigationDestination(
    val key: AppNavKey,
    val title: String,
    val icon: ImageVector,
    val testTag: String
)

val bottomNavigationDestinations = listOf(
    BottomNavigationDestination(
        key = AppNavKey.Home,
        title = "Home",
        icon = Icons.Default.Home,
        testTag = "tab_home"
    ),
    BottomNavigationDestination(
        key = AppNavKey.Learn,
        title = "Learn",
        icon = Icons.AutoMirrored.Filled.MenuBook,
        testTag = "tab_learn"
    ),
    BottomNavigationDestination(
        key = AppNavKey.Games,
        title = "Games",
        icon = Icons.Default.SportsEsports,
        testTag = "tab_games"
    ),
    BottomNavigationDestination(
        key = AppNavKey.Review,
        title = "Review",
        icon = Icons.Default.Refresh,
        testTag = "tab_review"
    ),
    BottomNavigationDestination(
        key = AppNavKey.Profile,
        title = "Profile",
        icon = Icons.Default.Person,
        testTag = "tab_profile"
    )
)

@Composable
fun AppNavigation(
    navigationState: NavigationState = rememberNavigationState(),
    modifier: Modifier = Modifier
) {
    val currentKey = navigationState.currentKey

    val entryDecorators: List<NavEntryDecorator<AppNavKey>> = listOf(
        rememberSaveableStateHolderNavEntryDecorator(),
        rememberViewModelStoreNavEntryDecorator()
    )

    val entryProvider: (AppNavKey) -> NavEntry<AppNavKey> = { key ->
        when (key) {
            AppNavKey.Home -> NavEntry(key) { HomeRoute() }
            AppNavKey.Learn -> NavEntry(key) { LearnRoute() }
            AppNavKey.Games -> NavEntry(key) { GamesRoute() }
            AppNavKey.Review -> NavEntry(key) { ReviewRoute() }
            AppNavKey.Profile -> NavEntry(key) { ProfileRoute() }
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(
                modifier = Modifier.testTag("bottom_navigation_bar")
            ) {
                bottomNavigationDestinations.forEach { dest ->
                    val isSelected = currentKey == dest.key
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { navigationState.navigateToRoot(dest.key) },
                        icon = {
                            Icon(
                                imageVector = dest.icon,
                                contentDescription = dest.title
                            )
                        },
                        label = { Text(dest.title) },
                        modifier = Modifier.testTag(dest.testTag)
                    )
                }
            }
        }
    ) { innerPadding ->
        NavDisplay(
            backStack = navigationState.backStack,
            entryProvider = entryProvider,
            entryDecorators = entryDecorators,
            onBack = { navigationState.popBack() },
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }
}
