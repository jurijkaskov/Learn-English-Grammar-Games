package com.learnenglish.grammargames.feature.learn

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.unit.dp
import com.learnenglish.grammargames.core.designsystem.theme.Dimens

@Composable
fun LearnScreen(
    uiState: LearnUiState,
    onTopicClick: (String) -> Unit = {},
    onRetryClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    when (uiState) {
        is LearnUiState.Loading -> {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .testTag("learn_loading"),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is LearnUiState.Error -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(Dimens.spacing24)
                    .testTag("learn_error"),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Unable to load curriculum",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.error
                )
                Spacer(modifier = Modifier.height(Dimens.spacing8))
                Text(
                    text = uiState.message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(Dimens.spacing16))
                Button(onClick = onRetryClick) {
                    Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
                    Spacer(modifier = Modifier.size(Dimens.spacing8))
                    Text("Retry")
                }
            }
        }
        is LearnUiState.Success -> {
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
                        text = uiState.courseTitle,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "Curriculum Engine • Grammar topics powered by structured assets",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                items(uiState.topics, key = { it.id }) { topic ->
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
    }
}
