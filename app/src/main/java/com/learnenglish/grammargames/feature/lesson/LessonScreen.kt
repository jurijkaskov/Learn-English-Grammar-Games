package com.learnenglish.grammargames.feature.lesson

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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import com.learnenglish.grammargames.core.designsystem.component.card.GrammarExampleCard
import com.learnenglish.grammargames.core.designsystem.component.card.GrammarHintCard
import com.learnenglish.grammargames.core.designsystem.component.navigation.GrammarTopAppBar
import com.learnenglish.grammargames.core.designsystem.theme.AppSpacing
import com.learnenglish.grammargames.core.designsystem.theme.GrammarGamesTheme
import com.learnenglish.grammargames.core.designsystem.theme.grammarGamesColors

@Composable
fun LessonScreen(
    state: LessonUiState = LessonUiState(),
    onCompleteLesson: (sessionId: String) -> Unit = {},
    onBackClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .testTag("lesson_screen"),
        topBar = {
            GrammarTopAppBar(
                title = state.title,
                onBackClick = onBackClick,
                testTag = "lesson_top_bar"
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppSpacing.cardPadding)
            ) {
                GrammarPrimaryButton(
                    text = "Complete Lesson",
                    leadingIcon = Icons.Default.CheckCircle,
                    onClick = { onCompleteLesson("lesson_session_${state.lessonId}") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("lesson_complete_button")
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = AppSpacing.screenHorizontalPhone),
            verticalArrangement = Arrangement.spacedBy(AppSpacing.md)
        ) {
            item {
                Spacer(modifier = Modifier.height(AppSpacing.xxs))
                GrammarHintCard(
                    tip = state.ruleSummary,
                    testTag = "lesson_summary_card"
                )
            }

            items(state.sections) { section ->
                GrammarCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("lesson_section_${section.title.hashCode()}")
                ) {
                    Text(
                        text = section.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(AppSpacing.xxs))
                    Text(
                        text = section.explanation,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.grammarGamesColors.textSecondary
                    )

                    Spacer(modifier = Modifier.height(AppSpacing.sm))

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(AppSpacing.xs)
                    ) {
                        section.examples.forEach { (english, note) ->
                            GrammarExampleCard(
                                sentence = english,
                                translation = note
                            )
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(AppSpacing.xl))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LessonScreenPreview() {
    GrammarGamesTheme {
        LessonScreen()
    }
}
