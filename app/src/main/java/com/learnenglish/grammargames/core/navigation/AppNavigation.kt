package com.learnenglish.grammargames.core.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import com.learnenglish.grammargames.feature.bookcompanion.navigation.bookCompanionNavEntry
import com.learnenglish.grammargames.feature.bootstrap.navigation.bootstrapNavEntry
import com.learnenglish.grammargames.feature.games.navigation.gameSessionNavEntry
import com.learnenglish.grammargames.feature.games.navigation.gamesHubNavEntry
import com.learnenglish.grammargames.feature.home.navigation.homeNavEntry
import com.learnenglish.grammargames.feature.learn.navigation.learnNavEntry
import com.learnenglish.grammargames.feature.lesson.navigation.lessonNavEntry
import com.learnenglish.grammargames.feature.mistakes.navigation.mistakesNavEntry
import com.learnenglish.grammargames.feature.onboarding.navigation.onboardingNavEntry
import com.learnenglish.grammargames.feature.profile.navigation.achievementsNavEntry
import com.learnenglish.grammargames.feature.profile.navigation.characterNavEntry
import com.learnenglish.grammargames.feature.profile.navigation.profileNavEntry
import com.learnenglish.grammargames.feature.results.navigation.resultsNavEntry
import com.learnenglish.grammargames.feature.review.navigation.reviewNavEntry
import com.learnenglish.grammargames.feature.settings.navigation.settingsNavEntry
import com.learnenglish.grammargames.feature.test.navigation.testNavEntry
import com.learnenglish.grammargames.feature.topic.navigation.topicNavEntry

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
    navigationState: NavigationState = rememberNavigationState(initialKey = AppNavKey.Bootstrap),
    modifier: Modifier = Modifier
) {
    val navigator: AppNavigator = rememberAppNavigator(navigationState)
    val currentKey = navigationState.currentKey
    val showBottomBar = currentKey.shouldShowBottomBar()

    val entryDecorators: List<NavEntryDecorator<AppNavKey>> = listOf(
        rememberSaveableStateHolderNavEntryDecorator(),
        rememberViewModelStoreNavEntryDecorator()
    )

    val entryProvider: (AppNavKey) -> NavEntry<AppNavKey> = { key ->
        when (key) {
            is AppNavKey.Bootstrap -> bootstrapNavEntry(key, navigator)

            is AppNavKey.Welcome,
            is AppNavKey.GoalSelection,
            is AppNavKey.LevelSelection,
            is AppNavKey.PlacementTest,
            is AppNavKey.BookSelection,
            is AppNavKey.DailyGoal,
            is AppNavKey.OnboardingComplete -> onboardingNavEntry(key, navigator)

            is AppNavKey.Home -> homeNavEntry(key, navigator)
            is AppNavKey.Learn -> learnNavEntry(key, navigator)
            is AppNavKey.Games -> gamesHubNavEntry(key, navigator)
            is AppNavKey.GameSession -> gameSessionNavEntry(key, navigator)

            is AppNavKey.Topic -> topicNavEntry(key, navigator)
            is AppNavKey.Lesson -> lessonNavEntry(key, navigator)
            is AppNavKey.Test -> testNavEntry(key, navigator)
            is AppNavKey.Results -> resultsNavEntry(key, navigator)

            is AppNavKey.Review -> reviewNavEntry(key, navigator)
            is AppNavKey.ReviewSession -> gameSessionNavEntry(
                AppNavKey.GameSession("review_session", key.reviewType),
                navigator
            )
            is AppNavKey.Mistakes -> mistakesNavEntry(key, navigator)

            is AppNavKey.Profile -> profileNavEntry(key, navigator)
            is AppNavKey.Achievements -> achievementsNavEntry(key, navigator)
            is AppNavKey.AchievementDetails -> achievementsNavEntry(AppNavKey.Achievements, navigator)
            is AppNavKey.Character -> characterNavEntry(key, navigator)
            is AppNavKey.CharacterCustomization -> characterNavEntry(AppNavKey.Character, navigator)
            is AppNavKey.Settings -> settingsNavEntry(key, navigator)
            is AppNavKey.DesignSystemShowcase -> NavEntry(key) {
                com.learnenglish.grammargames.core.designsystem.showcase.DesignSystemShowcaseScreen(
                    onBackClick = { navigator.navigateBack() }
                )
            }
            is AppNavKey.CurriculumInspector -> NavEntry(key) {
                com.learnenglish.grammargames.feature.curriculum.CurriculumInspectorRoute(
                    onBackClick = { navigator.navigateBack() }
                )
            }
            is AppNavKey.BookCompanion -> bookCompanionNavEntry(key, navigator)
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            AnimatedVisibility(
                visible = showBottomBar,
                enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
            ) {
                NavigationBar(
                    modifier = Modifier.testTag("bottom_navigation_bar")
                ) {
                    bottomNavigationDestinations.forEach { dest ->
                        val isSelected = currentKey == dest.key
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = { navigator.navigateToRoot(dest.key) },
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
        }
    ) { innerPadding ->
        NavDisplay(
            backStack = navigationState.backStack,
            entryProvider = entryProvider,
            entryDecorators = entryDecorators,
            onBack = { navigator.navigateBack() },
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }
}
