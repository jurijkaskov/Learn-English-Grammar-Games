package com.learnenglish.grammargames.feature.games

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
import androidx.compose.material.icons.filled.Gamepad
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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

data class GameMode(
    val id: String,
    val title: String,
    val description: String,
    val xpReward: Int,
    val difficulty: String
)

@Composable
fun GamesScreen(
    games: List<GameMode> = defaultGameModes,
    onPlayClick: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = Dimens.spacing16)
            .testTag("games_screen"),
        verticalArrangement = Arrangement.spacedBy(Dimens.spacing16)
    ) {
        item {
            Spacer(modifier = Modifier.height(Dimens.spacing8))
            Text(
                text = "Grammar Games",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "Play dynamic mini-games to sharpen grammar intuition & recall",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        items(games, key = { it.id }) { game ->
            OutlinedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("game_item_${game.id}"),
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
                            text = game.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.MilitaryTech,
                                contentDescription = "Reward",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(Dimens.iconSizeSmall)
                            )
                            Spacer(modifier = Modifier.size(Dimens.spacing4))
                            Text(
                                text = "+${game.xpReward} XP",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    Text(
                        text = game.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Difficulty: ${game.difficulty}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.secondary
                        )

                        Button(
                            onClick = { onPlayClick(game.id) },
                            modifier = Modifier.testTag("play_button_${game.id}")
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = null,
                                modifier = Modifier.size(Dimens.iconSizeSmall)
                            )
                            Spacer(modifier = Modifier.size(Dimens.spacing4))
                            Text("Play")
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(Dimens.spacing24))
        }
    }
}

val defaultGameModes = listOf(
    GameMode(
        id = "speed_tenses",
        title = "Speed Tenses Arena",
        description = "Choose the correct auxiliary verb under the ticking timer.",
        xpReward = 50,
        difficulty = "Fast & Fun"
    ),
    GameMode(
        id = "sentence_builder",
        title = "Sentence Scramble",
        description = "Rearrange shuffled words into grammatically impeccable clauses.",
        xpReward = 40,
        difficulty = "Puzzle"
    ),
    GameMode(
        id = "error_hunter",
        title = "Error Detective",
        description = "Find the single sneaky grammatical mistake inside the sentence.",
        xpReward = 60,
        difficulty = "Challenging"
    )
)

@Preview(showBackground = true)
@Composable
fun GamesScreenPreview() {
    GrammarGamesTheme {
        GamesScreen()
    }
}
