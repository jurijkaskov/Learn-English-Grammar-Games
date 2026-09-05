package com.learnenglish.grammargames.feature.topic

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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.learnenglish.grammargames.core.designsystem.theme.Dimens
import com.learnenglish.grammargames.core.designsystem.theme.GrammarGamesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopicScreen(
    state: TopicUiState = TopicUiState(),
    onStartLesson: (String) -> Unit = {},
    onStartTest: () -> Unit = {},
    onOpenGames: () -> Unit = {},
    onOpenBookCompanion: (bookId: String?, editionId: String?, unitNumber: Int?) -> Unit = { _, _, _ -> },
    onBackClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .testTag("topic_screen"),
        topBar = {
            TopAppBar(
                title = { Text("Grammar Topic") },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier.testTag("topic_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = Dimens.spacing16),
            verticalArrangement = Arrangement.spacedBy(Dimens.spacing16)
        ) {
            item {
                Spacer(modifier = Modifier.height(Dimens.spacing4))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("topic_header_card"),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    shape = MaterialTheme.shapes.medium
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
                                text = state.title,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.weight(1f)
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                repeat(state.starsEarned) {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = "Star",
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }

                        Text(
                            text = state.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.MenuBook,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(Dimens.iconSizeSmall)
                            )
                            Spacer(modifier = Modifier.size(Dimens.spacing4))
                            Text(
                                text = state.referenceBook,
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Spacer(modifier = Modifier.height(Dimens.spacing4))
                        LinearProgressIndicator(
                            progress = { state.masteryPercentage / 100f },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                        )
                        Text(
                            text = "Mastery: ${state.masteryPercentage}%",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }

            // Quick actions (Games & Test)
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(Dimens.spacing12)
                ) {
                    OutlinedButton(
                        onClick = onOpenGames,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .testTag("topic_games_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.SportsEsports,
                            contentDescription = null,
                            modifier = Modifier.size(Dimens.iconSizeSmall)
                        )
                        Spacer(modifier = Modifier.size(Dimens.spacing4))
                        Text("Practice Games")
                    }

                    Button(
                        onClick = onStartTest,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .testTag("topic_test_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Quiz,
                            contentDescription = null,
                            modifier = Modifier.size(Dimens.iconSizeSmall)
                        )
                        Spacer(modifier = Modifier.size(Dimens.spacing4))
                        Text("Take Test")
                    }
                }
            }

            // Book Companion Card
            item {
                val companion = state.bookCompanionInfo
                if (companion != null) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("topic_book_companion_card"),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        ),
                        shape = MaterialTheme.shapes.medium
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
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.MenuBook,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(Dimens.iconSizeMedium)
                                    )
                                    Spacer(modifier = Modifier.size(Dimens.spacing8))
                                    Text(
                                        text = "YOUR GRAMMAR BOOK",
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                                Text(
                                    text = companion.editionName,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            Text(
                                text = companion.bookTitle,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Text(
                                text = "Corresponding unit(s) in this textbook:",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            companion.units.forEach { unit ->
                                Surface(
                                    shape = MaterialTheme.shapes.small,
                                    color = MaterialTheme.colorScheme.surface,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(
                                        modifier = Modifier.padding(
                                            horizontal = Dimens.spacing12,
                                            vertical = Dimens.spacing8
                                        ),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Surface(
                                            shape = MaterialTheme.shapes.extraSmall,
                                            color = MaterialTheme.colorScheme.primaryContainer
                                        ) {
                                            Text(
                                                text = "Unit ${unit.unitNumber}",
                                                style = MaterialTheme.typography.labelSmall,
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                            )
                                        }
                                        Spacer(modifier = Modifier.size(Dimens.spacing8))
                                        Text(
                                            text = unit.unitTitle,
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = FontWeight.Medium,
                                            modifier = Modifier.weight(1f)
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(Dimens.spacing4))

                            FilledTonalButton(
                                onClick = {
                                    val firstUnit = companion.units.firstOrNull()?.unitNumber ?: 1
                                    onOpenBookCompanion(
                                        companion.bookId,
                                        companion.editionId,
                                        firstUnit
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("open_in_book_companion_button")
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.MenuBook,
                                    contentDescription = null,
                                    modifier = Modifier.size(Dimens.iconSizeSmall)
                                )
                                Spacer(modifier = Modifier.size(Dimens.spacing8))
                                Text("Open in Book Companion")
                            }
                        }
                    }
                } else {
                    OutlinedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("topic_choose_book_companion_card"),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(Dimens.spacing16)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Grammar Book Companion",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Studying with Murphy or Hewings? Sync your textbook units.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Spacer(modifier = Modifier.size(Dimens.spacing8))
                            FilledTonalButton(
                                onClick = { onOpenBookCompanion(null, null, null) },
                                modifier = Modifier.testTag("choose_book_companion_button")
                            ) {
                                Text("Browse Books")
                            }
                        }
                    }
                }
            }

            item {
                Text(
                    text = "Topic Lessons",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            items(state.lessons, key = { it.id }) { lesson ->
                OutlinedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("topic_lesson_card_${lesson.id}"),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(Dimens.spacing16),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = lesson.title,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                if (lesson.isCompleted) {
                                    Spacer(modifier = Modifier.size(Dimens.spacing8))
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "Completed",
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(Dimens.iconSizeSmall)
                                    )
                                }
                            }
                            Text(
                                text = lesson.description,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        FilledTonalButton(
                            onClick = { onStartLesson(lesson.id) },
                            modifier = Modifier.testTag("start_lesson_button_${lesson.id}")
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = null,
                                modifier = Modifier.size(Dimens.iconSizeSmall)
                            )
                            Spacer(modifier = Modifier.size(Dimens.spacing4))
                            Text(if (lesson.isCompleted) "Review" else "Study")
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(Dimens.spacing24))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopicScreenPreview() {
    GrammarGamesTheme {
        TopicScreen()
    }
}
