package com.learnenglish.grammargames.feature.games

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Gamepad
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.learnenglish.grammargames.core.designsystem.component.button.GrammarPrimaryButton
import com.learnenglish.grammargames.core.designsystem.component.card.GrammarCard
import com.learnenglish.grammargames.core.designsystem.component.chip.GrammarBadge
import com.learnenglish.grammargames.core.designsystem.theme.AppDimensions
import com.learnenglish.grammargames.core.designsystem.theme.AppSpacing
import com.learnenglish.grammargames.core.designsystem.theme.GrammarGamesTheme
import com.learnenglish.grammargames.core.designsystem.theme.grammarGamesColors

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
            .padding(horizontal = AppSpacing.screenHorizontalPhone)
            .testTag("games_screen"),
        verticalArrangement = Arrangement.spacedBy(AppSpacing.md)
    ) {
        item {
            Spacer(modifier = Modifier.height(AppSpacing.xs))
            Text(
                text = "Grammar Games",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "Play dynamic mini-games to sharpen grammar intuition & recall",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.grammarGamesColors.textSecondary
            )
        }

        items(games, key = { it.id }) { game ->
            GrammarCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("game_item_${game.id}")
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = game.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    GrammarBadge(
                        text = "+${game.xpReward} XP",
                        backgroundColor = MaterialTheme.grammarGamesColors.xpContainer,
                        textColor = MaterialTheme.grammarGamesColors.onXpContainer
                    )
                }

                Spacer(modifier = Modifier.height(AppSpacing.xxs))

                Text(
                    text = game.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.grammarGamesColors.textSecondary
                )

                Spacer(modifier = Modifier.height(AppSpacing.sm))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    GrammarBadge(
                        text = game.difficulty,
                        backgroundColor = MaterialTheme.grammarGamesColors.secondaryActionContainer,
                        textColor = MaterialTheme.grammarGamesColors.onSecondaryActionContainer
                    )

                    GrammarPrimaryButton(
                        text = "Play",
                        leadingIcon = Icons.Default.PlayArrow,
                        onClick = { onPlayClick(game.id) },
                        testTag = "play_button_${game.id}"
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(AppSpacing.xl))
        }
    }
}

val defaultGameModes = listOf(
    GameMode(
        id = "speed_tenses",
        title = "Speed Tenses Arena",
        description = "Choose the correct tense before the 60-second timer runs out.",
        xpReward = 50,
        difficulty = "Fast & Fun"
    ),
    GameMode(
        id = "sentence_builder",
        title = "Sentence Architect",
        description = "Assemble scrambled words into grammatically impeccable structures.",
        xpReward = 40,
        difficulty = "Challenging"
    ),
    GameMode(
        id = "error_spotter",
        title = "Detective: Spot The Error",
        description = "Find the single grammatical slip in real-world everyday dialogues.",
        xpReward = 45,
        difficulty = "Intermediate"
    )
)

@Preview(showBackground = true)
@Composable
fun GamesScreenPreview() {
    GrammarGamesTheme {
        GamesScreen()
    }
}
