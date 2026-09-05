package com.learnenglish.grammargames.feature.learn

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
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
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

data class LearnTopicItem(
    val id: String,
    val title: String,
    val grammarRule: String,
    val murphyUnits: String,
    val isCompleted: Boolean = false
)

@Composable
fun LearnScreen(
    topics: List<LearnTopicItem> = defaultLearnTopics,
    onTopicClick: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = Dimens.spacing16)
            .testTag("learn_screen"),
        verticalArrangement = Arrangement.spacedBy(Dimens.spacing16)
    ) {
        item {
            Spacer(modifier = Modifier.height(Dimens.spacing8))
            Text(
                text = "Grammar Lessons",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "Structured topics based on classical grammar reference books",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        items(topics, key = { it.id }) { topic ->
            OutlinedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("learn_topic_${topic.id}"),
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
                            text = topic.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        if (topic.isCompleted) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Completed",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(Dimens.iconSizeMedium)
                            )
                        }
                    }

                    Text(
                        text = topic.grammarRule,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.MenuBook,
                                contentDescription = "Book Reference",
                                tint = MaterialTheme.colorScheme.secondary,
                                modifier = Modifier.size(Dimens.iconSizeSmall)
                            )
                            Spacer(modifier = Modifier.size(Dimens.spacing4))
                            Text(
                                text = topic.murphyUnits,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }

                        FilledTonalButton(
                            onClick = { onTopicClick(topic.id) },
                            modifier = Modifier.testTag("start_lesson_${topic.id}")
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = null,
                                modifier = Modifier.size(Dimens.iconSizeSmall)
                            )
                            Spacer(modifier = Modifier.size(Dimens.spacing4))
                            Text("Study")
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

val defaultLearnTopics = listOf(
    LearnTopicItem(
        id = "present_simple",
        title = "Present Simple vs Continuous",
        grammarRule = "Use Present Simple for habits and permanent states; Continuous for actions happening right now.",
        murphyUnits = "Murphy Units 1-4",
        isCompleted = true
    ),
    LearnTopicItem(
        id = "past_simple",
        title = "Past Simple & Irregular Verbs",
        grammarRule = "Use Past Simple for finished actions at a specific time in the past.",
        murphyUnits = "Murphy Units 5-6",
        isCompleted = false
    ),
    LearnTopicItem(
        id = "present_perfect",
        title = "Present Perfect Tense",
        grammarRule = "Connects the past with the present (experience, recent events, unfinished time).",
        murphyUnits = "Murphy Units 7-14",
        isCompleted = false
    ),
    LearnTopicItem(
        id = "conditionals",
        title = "Zero & First Conditionals",
        grammarRule = "Real and likely situations in present or future: 'If + present, will + infinitive'.",
        murphyUnits = "Murphy Units 38-40",
        isCompleted = false
    )
)

@Preview(showBackground = true)
@Composable
fun LearnScreenPreview() {
    GrammarGamesTheme {
        LearnScreen()
    }
}
