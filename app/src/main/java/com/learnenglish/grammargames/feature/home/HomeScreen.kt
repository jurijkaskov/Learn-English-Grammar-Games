package com.learnenglish.grammargames.feature.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.learnenglish.grammargames.core.designsystem.theme.Dimens
import com.learnenglish.grammargames.core.designsystem.theme.GrammarGamesTheme
import com.learnenglish.grammargames.core.navigation.DemoNavigationFixtures
import com.learnenglish.grammargames.domain.model.Course
import com.learnenglish.grammargames.domain.model.CourseLevel
import com.learnenglish.grammargames.domain.model.UserPreferences
import com.learnenglish.grammargames.domain.model.UserProgress

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onAction: (HomeUiAction) -> Unit,
    onContinueLearning: (String) -> Unit = {},
    onDailyChallenge: () -> Unit = {},
    onGamesClick: () -> Unit = {},
    onMistakesClick: () -> Unit = {},
    onAchievementsClick: () -> Unit = {},
    onCharacterClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    if (uiState.isLoading) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .testTag("home_loading"),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
        return
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = Dimens.spacing16)
            .testTag("home_screen"),
        verticalArrangement = Arrangement.spacedBy(Dimens.spacing16)
    ) {
        item {
            Spacer(modifier = Modifier.height(Dimens.spacing8))
            Text(
                text = "Welcome Back!",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "Master English grammar with interactive games & lessons",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Stats card
        item {
            UserStatsCard(
                progress = uiState.progress,
                dailyGoalMinutes = uiState.preferences.dailyGoalMinutes,
                onAddXp = { onAction(HomeUiAction.AddXp(25L)) },
                modifier = Modifier.testTag("home_stats_card")
            )
        }

        // Continue Learning Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("home_continue_learning_card"),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                ),
                shape = MaterialTheme.shapes.medium
            ) {
                Column(
                    modifier = Modifier.padding(Dimens.spacing16),
                    verticalArrangement = Arrangement.spacedBy(Dimens.spacing8)
                ) {
                    Text(
                        text = "Continue Learning",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Present Simple vs Present Continuous",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Text(
                        text = "Unit 1: Habitual actions vs temporary events in progress.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Button(
                        onClick = { onContinueLearning(DemoNavigationFixtures.DEMO_TOPIC_ID) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("continue_learning_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = null,
                            modifier = Modifier.size(Dimens.iconSizeSmall)
                        )
                        Spacer(modifier = Modifier.size(Dimens.spacing8))
                        Text("Resume Topic")
                    }
                }
            }
        }

        // Daily Challenge & Games Row
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Dimens.spacing12)
            ) {
                OutlinedCard(
                    modifier = Modifier
                        .weight(1f)
                        .clickable(onClick = onDailyChallenge)
                        .testTag("home_daily_challenge_card"),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Column(
                        modifier = Modifier.padding(Dimens.spacing12),
                        verticalArrangement = Arrangement.spacedBy(Dimens.spacing4)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Bolt,
                            contentDescription = "Challenge",
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "Daily Challenge",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Speed Tenses Arena",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                OutlinedCard(
                    modifier = Modifier
                        .weight(1f)
                        .clickable(onClick = onMistakesClick)
                        .testTag("home_mistakes_card"),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Column(
                        modifier = Modifier.padding(Dimens.spacing12),
                        verticalArrangement = Arrangement.spacedBy(Dimens.spacing4)
                    ) {
                        Icon(
                            imageVector = Icons.Default.WarningAmber,
                            contentDescription = "Mistakes",
                            tint = MaterialTheme.colorScheme.error
                        )
                        Text(
                            text = "Weak Topics",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "2 items due review",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        // Achievements & Companion Shortcuts
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Dimens.spacing12)
            ) {
                FilledTonalButton(
                    onClick = onAchievementsClick,
                    modifier = Modifier
                        .weight(1f)
                        .testTag("home_achievements_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.EmojiEvents,
                        contentDescription = null,
                        modifier = Modifier.size(Dimens.iconSizeSmall)
                    )
                    Spacer(modifier = Modifier.size(Dimens.spacing4))
                    Text("Badges")
                }

                FilledTonalButton(
                    onClick = onCharacterClick,
                    modifier = Modifier
                        .weight(1f)
                        .testTag("home_character_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        modifier = Modifier.size(Dimens.iconSizeSmall)
                    )
                    Spacer(modifier = Modifier.size(Dimens.spacing4))
                    Text("My Dragon")
                }
            }
        }

        // Selected Course Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Grammar Tracks",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        // Courses List
        items(uiState.courses, key = { it.id }) { course ->
            val isSelected = course.id == uiState.preferences.selectedCourseId
            CourseCard(
                course = course,
                isSelected = isSelected,
                onClick = { onAction(HomeUiAction.SelectCourse(course.id)) },
                modifier = Modifier.testTag("course_item_${course.id}")
            )
        }

        item {
            Spacer(modifier = Modifier.height(Dimens.spacing24))
        }
    }
}

@Composable
fun UserStatsCard(
    progress: UserProgress,
    dailyGoalMinutes: Int,
    onAddXp: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier.padding(Dimens.spacing16),
            verticalArrangement = Arrangement.spacedBy(Dimens.spacing12)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Current Level ${progress.level}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = "${progress.totalXp} XP earned",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }

                Button(
                    onClick = onAddXp,
                    modifier = Modifier.testTag("add_xp_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Bolt,
                        contentDescription = "Add XP",
                        modifier = Modifier.size(Dimens.iconSizeSmall)
                    )
                    Spacer(modifier = Modifier.size(Dimens.spacing4))
                    Text("+25 XP")
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Dimens.spacing16)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Timer,
                        contentDescription = "Daily goal",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(Dimens.iconSizeSmall)
                    )
                    Spacer(modifier = Modifier.size(Dimens.spacing4))
                    Text(
                        text = "$dailyGoalMinutes min/day",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.School,
                        contentDescription = "Streak",
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.size(Dimens.iconSizeSmall)
                    )
                    Spacer(modifier = Modifier.size(Dimens.spacing4))
                    Text(
                        text = "${progress.streakDays} day streak",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }
    }
}

@Composable
fun CourseCard(
    course: Course,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.outlinedCardColors(
            containerColor = if (isSelected) {
                MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.4f)
            } else {
                MaterialTheme.colorScheme.surface
            }
        )
    ) {
        Column(
            modifier = Modifier.padding(Dimens.spacing16),
            verticalArrangement = Arrangement.spacedBy(Dimens.spacing8)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = course.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                if (isSelected) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Selected",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(Dimens.iconSizeMedium)
                    )
                }
            }

            Text(
                text = course.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                FilterChip(
                    selected = isSelected,
                    onClick = onClick,
                    label = { Text(course.level.name) }
                )
                Text(
                    text = course.level.name.lowercase().replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    GrammarGamesTheme {
        HomeScreen(
            uiState = HomeUiState(
                isLoading = false,
                courses = listOf(
                    Course(
                        id = "essential_grammar",
                        title = "Essential Grammar",
                        description = "Elementary grammar foundations",
                        level = CourseLevel.BEGINNER
                    )
                ),
                progress = UserProgress(totalXp = 150, level = 2, streakDays = 3),
                preferences = UserPreferences(selectedCourseId = "essential_grammar", dailyGoalMinutes = 15)
            ),
            onAction = {}
        )
    }
}
