package com.learnenglish.grammargames.feature.test

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.learnenglish.grammargames.core.designsystem.component.button.GrammarAnswerButton
import com.learnenglish.grammargames.core.designsystem.component.button.GrammarPrimaryButton
import com.learnenglish.grammargames.core.designsystem.component.navigation.GrammarTopAppBar
import com.learnenglish.grammargames.core.designsystem.component.panel.GrammarGamePanel
import com.learnenglish.grammargames.core.designsystem.component.progress.GrammarLinearProgress
import com.learnenglish.grammargames.core.designsystem.state.ExerciseAnswerState
import com.learnenglish.grammargames.core.designsystem.theme.AppSpacing
import com.learnenglish.grammargames.core.designsystem.theme.GrammarGamesTheme
import com.learnenglish.grammargames.core.designsystem.theme.grammarGamesColors

@Composable
fun TestScreen(
    state: TestUiState = TestUiState(),
    onSelectOption: (Int) -> Unit = {},
    onNextOrSubmit: () -> Unit = {},
    onFinishTest: (sessionId: String) -> Unit = {},
    onBackClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val isLastQuestion = state.currentQuestionIndex + 1 == state.questions.size
    val question = state.questions.getOrNull(state.currentQuestionIndex)
    val progressFloat = if (state.questions.isNotEmpty()) {
        (state.currentQuestionIndex + 1).toFloat() / state.questions.size
    } else 0f

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .testTag("test_screen"),
        topBar = {
            GrammarTopAppBar(
                title = state.title,
                onBackClick = onBackClick,
                testTag = "test_top_bar"
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppSpacing.cardPadding)
            ) {
                GrammarPrimaryButton(
                    text = if (isLastQuestion) "Submit Test" else "Next Question",
                    onClick = {
                        if (isLastQuestion) {
                            onFinishTest("test_session_${state.topicId}")
                        } else {
                            onNextOrSubmit()
                        }
                    },
                    enabled = state.selectedOptionIndex != null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("test_next_or_submit_button")
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = AppSpacing.screenHorizontalPhone)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Spacer(modifier = Modifier.height(AppSpacing.xs))
                GrammarLinearProgress(
                    progress = progressFloat,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(AppSpacing.sm))
                Text(
                    text = "Question ${state.currentQuestionIndex + 1} of ${state.questions.size}",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.grammarGamesColors.primaryAction
                )
                Spacer(modifier = Modifier.height(AppSpacing.md))

                if (question != null) {
                    GrammarGamePanel(
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("test_question_card")
                    ) {
                        Text(
                            text = question.prompt,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Spacer(modifier = Modifier.height(AppSpacing.lg))

                    Column(verticalArrangement = Arrangement.spacedBy(AppSpacing.sm)) {
                        question.options.forEachIndexed { index, option ->
                            val isSelected = state.selectedOptionIndex == index
                            val optionLabel = when (index) {
                                0 -> "A"
                                1 -> "B"
                                2 -> "C"
                                3 -> "D"
                                else -> "${index + 1}"
                            }

                            GrammarAnswerButton(
                                text = option,
                                optionLabel = optionLabel,
                                state = if (isSelected) ExerciseAnswerState.SELECTED else ExerciseAnswerState.DEFAULT,
                                onClick = { onSelectOption(index) },
                                testTag = "test_option_$index"
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(AppSpacing.xl))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TestScreenPreview() {
    GrammarGamesTheme {
        TestScreen()
    }
}
