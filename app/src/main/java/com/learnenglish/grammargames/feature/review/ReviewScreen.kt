package com.learnenglish.grammargames.feature.review

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
import androidx.compose.material.icons.filled.Autorenew
import androidx.compose.material.icons.filled.FactCheck
import androidx.compose.material.icons.filled.WarningAmber
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

data class ReviewItem(
    val id: String,
    val ruleTitle: String,
    val incorrectExample: String,
    val correctExample: String,
    val repetitionStage: String
)

@Composable
fun ReviewScreen(
    items: List<ReviewItem> = defaultReviewItems,
    onStartReview: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = Dimens.spacing16)
            .testTag("review_screen"),
        verticalArrangement = Arrangement.spacedBy(Dimens.spacing16)
    ) {
        item {
            Spacer(modifier = Modifier.height(Dimens.spacing8))
            Text(
                text = "Spaced Repetition Review",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "Review your past mistakes and cement long-term memory",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("review_action_card"),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                ),
                shape = MaterialTheme.shapes.medium
            ) {
                Row(
                    modifier = Modifier.padding(Dimens.spacing16),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "${items.size} Items Due Today",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                        Text(
                            text = "Estimated time: 5 minutes",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }

                    Button(
                        onClick = onStartReview,
                        modifier = Modifier.testTag("start_review_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Autorenew,
                            contentDescription = null,
                            modifier = Modifier.size(Dimens.iconSizeSmall)
                        )
                        Spacer(modifier = Modifier.size(Dimens.spacing4))
                        Text("Review")
                    }
                }
            }
        }

        items(items, key = { it.id }) { item ->
            OutlinedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("review_item_${item.id}"),
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
                            text = item.ruleTitle,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = item.repetitionStage,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Row(verticalAlignment = Alignment.Top) {
                        Icon(
                            imageVector = Icons.Default.WarningAmber,
                            contentDescription = "Wrong",
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(Dimens.iconSizeSmall)
                        )
                        Spacer(modifier = Modifier.size(Dimens.spacing8))
                        Text(
                            text = item.incorrectExample,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.error
                        )
                    }

                    Row(verticalAlignment = Alignment.Top) {
                        Icon(
                            imageVector = Icons.Default.FactCheck,
                            contentDescription = "Right",
                            tint = MaterialTheme.colorScheme.tertiary,
                            modifier = Modifier.size(Dimens.iconSizeSmall)
                        )
                        Spacer(modifier = Modifier.size(Dimens.spacing8))
                        Text(
                            text = item.correctExample,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(Dimens.spacing24))
        }
    }
}

val defaultReviewItems = listOf(
    ReviewItem(
        id = "rev_1",
        ruleTitle = "Third person -s suffix",
        incorrectExample = "She work at the laboratory every Saturday.",
        correctExample = "She works at the laboratory every Saturday.",
        repetitionStage = "Box 2 • Next: Tomorrow"
    ),
    ReviewItem(
        id = "rev_2",
        ruleTitle = "Present Perfect with 'for' vs 'since'",
        incorrectExample = "I have lived here since five years.",
        correctExample = "I have lived here for five years.",
        repetitionStage = "Box 1 • Next: Today"
    )
)

@Preview(showBackground = true)
@Composable
fun ReviewScreenPreview() {
    GrammarGamesTheme {
        ReviewScreen()
    }
}
