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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.Icon
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
import com.learnenglish.grammargames.core.designsystem.component.button.GrammarSecondaryButton
import com.learnenglish.grammargames.core.designsystem.component.card.GrammarCard
import com.learnenglish.grammargames.core.designsystem.component.chip.GrammarBadge
import com.learnenglish.grammargames.core.designsystem.theme.AppDimensions
import com.learnenglish.grammargames.core.designsystem.theme.AppSpacing
import com.learnenglish.grammargames.core.designsystem.theme.GrammarGamesTheme
import com.learnenglish.grammargames.core.designsystem.theme.grammarGamesColors

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
    onStartReviewSession: () -> Unit = onStartReview,
    onMistakesClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.grammarGamesColors

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = AppSpacing.screenHorizontalPhone)
            .testTag("review_screen"),
        verticalArrangement = Arrangement.spacedBy(AppSpacing.md)
    ) {
        item {
            Spacer(modifier = Modifier.height(AppSpacing.xs))
            Text(
                text = "Spaced Repetition Review",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "Review your past mistakes and cement long-term memory",
                style = MaterialTheme.typography.bodyMedium,
                color = colors.textSecondary
            )
        }

        item {
            GrammarCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("review_action_card"),
                containerColor = colors.primaryActionContainer
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "${items.size} Items Due Today",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Estimated time: 5 minutes",
                            style = MaterialTheme.typography.bodySmall,
                            color = colors.textSecondary
                        )
                    }

                    GrammarPrimaryButton(
                        text = "Review",
                        leadingIcon = Icons.Default.Autorenew,
                        onClick = onStartReviewSession,
                        testTag = "start_review_button"
                    )
                }
            }
        }

        item {
            GrammarCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("review_mistakes_library_card")
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(AppSpacing.sm)
                    ) {
                        Icon(
                            imageVector = Icons.Default.WarningAmber,
                            contentDescription = null,
                            tint = colors.error,
                            modifier = Modifier.size(AppDimensions.iconMedium)
                        )
                        Column {
                            Text(
                                text = "Personal Mistake Notebook",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Categorized analysis of rules needing practice",
                                style = MaterialTheme.typography.bodySmall,
                                color = colors.textSecondary
                            )
                        }
                    }

                    GrammarSecondaryButton(
                        text = "View",
                        trailingIcon = Icons.Default.ChevronRight,
                        onClick = onMistakesClick,
                        testTag = "review_mistakes_library_button"
                    )
                }
            }
        }

        items(items, key = { it.id }) { item ->
            GrammarCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("review_item_${item.id}")
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item.ruleTitle,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    GrammarBadge(
                        text = item.repetitionStage,
                        backgroundColor = colors.secondaryActionContainer,
                        textColor = colors.onSecondaryActionContainer
                    )
                }

                Spacer(modifier = Modifier.height(AppSpacing.xs))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Incorrect",
                        tint = colors.error,
                        modifier = Modifier.size(AppDimensions.iconSmall)
                    )
                    Spacer(modifier = Modifier.size(AppSpacing.xs))
                    Text(
                        text = item.incorrectExample,
                        style = MaterialTheme.typography.bodyMedium,
                        color = colors.error
                    )
                }

                Spacer(modifier = Modifier.height(AppSpacing.xxs))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Correct",
                        tint = colors.success,
                        modifier = Modifier.size(AppDimensions.iconSmall)
                    )
                    Spacer(modifier = Modifier.size(AppSpacing.xs))
                    Text(
                        text = item.correctExample,
                        style = MaterialTheme.typography.bodyMedium,
                        color = colors.success
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(AppSpacing.xl))
        }
    }
}

val defaultReviewItems = listOf(
    ReviewItem(
        id = "rev_1",
        ruleTitle = "Present Perfect with 'Already'",
        incorrectExample = "I did already see that film yesterday.",
        correctExample = "I have already seen that film.",
        repetitionStage = "Stage 2 (in 3 days)"
    ),
    ReviewItem(
        id = "rev_2",
        ruleTitle = "Third-person singular -s",
        incorrectExample = "He go to school by bus every morning.",
        correctExample = "He goes to school by bus every morning.",
        repetitionStage = "Stage 4 (in 7 days)"
    )
)

@Preview(showBackground = true)
@Composable
fun ReviewScreenPreview() {
    GrammarGamesTheme {
        ReviewScreen()
    }
}
