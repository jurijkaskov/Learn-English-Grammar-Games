package com.learnenglish.grammargames.core.designsystem.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.learnenglish.grammargames.core.designsystem.component.button.GrammarAnswerButton
import com.learnenglish.grammargames.core.designsystem.component.button.GrammarPrimaryButton
import com.learnenglish.grammargames.core.designsystem.component.button.GrammarSecondaryButton
import com.learnenglish.grammargames.core.designsystem.component.button.GrammarTertiaryButton
import com.learnenglish.grammargames.core.designsystem.component.card.GrammarCard
import com.learnenglish.grammargames.core.designsystem.component.card.GrammarExampleCard
import com.learnenglish.grammargames.core.designsystem.component.card.GrammarGameCard
import com.learnenglish.grammargames.core.designsystem.component.card.GrammarHintCard
import com.learnenglish.grammargames.core.designsystem.component.card.GrammarLearningCard
import com.learnenglish.grammargames.core.designsystem.component.card.GrammarRuleCard
import com.learnenglish.grammargames.core.designsystem.component.chip.GrammarBadge
import com.learnenglish.grammargames.core.designsystem.component.chip.GrammarChip
import com.learnenglish.grammargames.core.designsystem.component.chip.GrammarStarRating
import com.learnenglish.grammargames.core.designsystem.component.feedback.GrammarCorrectFeedbackPanel
import com.learnenglish.grammargames.core.designsystem.component.feedback.GrammarFeedbackPanel
import com.learnenglish.grammargames.core.designsystem.component.feedback.GrammarWrongFeedbackPanel
import com.learnenglish.grammargames.core.designsystem.component.panel.GrammarHeartCounter
import com.learnenglish.grammargames.core.designsystem.component.panel.GrammarStreakBadge
import com.learnenglish.grammargames.core.designsystem.component.panel.GrammarTimerBadge
import com.learnenglish.grammargames.core.designsystem.component.panel.GrammarXpBadge
import com.learnenglish.grammargames.core.designsystem.component.progress.GrammarDailyGoalProgress
import com.learnenglish.grammargames.core.designsystem.component.progress.GrammarLinearProgress
import com.learnenglish.grammargames.core.designsystem.component.progress.GrammarMasteryProgress
import com.learnenglish.grammargames.core.designsystem.component.progress.GrammarXpProgress
import com.learnenglish.grammargames.core.designsystem.state.ExerciseAnswerState
import com.learnenglish.grammargames.core.designsystem.state.FeedbackType
import com.learnenglish.grammargames.core.designsystem.theme.AppSpacing
import com.learnenglish.grammargames.core.designsystem.theme.GrammarGamesTheme

@Preview(name = "Primary Buttons", showBackground = true)
@Composable
fun PrimaryButtonPreview() {
    GrammarGamesTheme {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            GrammarPrimaryButton(
                text = "Continue",
                onClick = {}
            )
            GrammarPrimaryButton(
                text = "Start Lesson",
                leadingIcon = Icons.Default.PlayArrow,
                onClick = {}
            )
            GrammarPrimaryButton(
                text = "Loading State",
                isLoading = true,
                onClick = {}
            )
            GrammarPrimaryButton(
                text = "Disabled State",
                enabled = false,
                onClick = {}
            )
            GrammarSecondaryButton(
                text = "Review Mistakes",
                onClick = {}
            )
            GrammarTertiaryButton(
                text = "Skip for now",
                onClick = {}
            )
        }
    }
}

@Preview(name = "Answer States", showBackground = true)
@Composable
fun AnswerStatesPreview() {
    GrammarGamesTheme {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            GrammarAnswerButton(
                text = "She has gone to Paris.",
                optionLabel = "A",
                state = ExerciseAnswerState.DEFAULT,
                onClick = {}
            )
            GrammarAnswerButton(
                text = "She is gone to Paris.",
                optionLabel = "B",
                state = ExerciseAnswerState.SELECTED,
                onClick = {}
            )
            GrammarAnswerButton(
                text = "She has been to Paris.",
                optionLabel = "C",
                state = ExerciseAnswerState.CORRECT,
                onClick = {}
            )
            GrammarAnswerButton(
                text = "She have been to Paris.",
                optionLabel = "D",
                state = ExerciseAnswerState.WRONG,
                onClick = {}
            )
            GrammarAnswerButton(
                text = "Disabled Choice",
                optionLabel = "E",
                state = ExerciseAnswerState.DISABLED,
                onClick = {}
            )
        }
    }
}

@Preview(name = "Cards Preview", showBackground = true)
@Composable
fun CardsPreview() {
    GrammarGamesTheme {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            GrammarLearningCard(
                title = "Present Continuous",
                subtitle = "I am doing — Ongoing actions",
                badgeText = "Unit 1",
                isCompleted = true,
                onClick = {}
            )
            GrammarLearningCard(
                title = "Past Simple vs Present Perfect",
                subtitle = "I did vs I have done",
                badgeText = "Unit 7",
                isLocked = true,
                onClick = {}
            )
            GrammarGameCard(
                title = "Speed Challenge",
                description = "Spot mistakes against the clock",
                xpReward = 50,
                difficultyLabel = "Fast",
                leadingIcon = Icons.Default.SportsEsports,
                onClick = {}
            )
            GrammarRuleCard(
                formula = "have / has + past participle",
                explanation = "Used when an action in the past has a result now in the present."
            )
            GrammarExampleCard(
                sentence = "I have lost my passport.",
                translation = "Я потерял свой паспорт.",
                highlightKeyword = "have lost",
                onAudioClick = {}
            )
            GrammarHintCard(
                tip = "Remember: Never use the present perfect with finished time expressions like yesterday or in 2010."
            )
        }
    }
}

@Preview(name = "Progress Indicators", showBackground = true)
@Composable
fun ProgressPreview() {
    GrammarGamesTheme {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            GrammarLinearProgress(progress = 0.65f)
            GrammarMasteryProgress(title = "Present Perfect Mastery", masteryPercentage = 78)
            GrammarXpProgress(currentXp = 1450, targetXp = 2000, level = 4)
            GrammarDailyGoalProgress(completed = 3, total = 5)
            GrammarStarRating(stars = 2)
        }
    }
}

@Preview(name = "Feedback Panels", showBackground = true)
@Composable
fun FeedbackPreview() {
    GrammarGamesTheme {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            GrammarCorrectFeedbackPanel(
                title = "Excellent! You got it right",
                description = "Present Perfect describes past events with current relevance.",
                onContinueClick = {}
            )
            GrammarWrongFeedbackPanel(
                explanation = "Use 'has' with third-person singular (he, she, it).",
                onContinueClick = {}
            )
        }
    }
}

@Preview(name = "Dark Theme Preview", showBackground = true)
@Composable
fun DarkThemePreview() {
    GrammarGamesTheme(darkTheme = true) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                GrammarPrimaryButton(text = "Start Lesson", onClick = {})
                GrammarAnswerButton(
                    text = "She has completed the quiz.",
                    optionLabel = "A",
                    state = ExerciseAnswerState.CORRECT,
                    onClick = {}
                )
                GrammarAnswerButton(
                    text = "She have completed the quiz.",
                    optionLabel = "B",
                    state = ExerciseAnswerState.WRONG,
                    onClick = {}
                )
                GrammarRuleCard(
                    formula = "subject + was/were + -ing",
                    explanation = "Actions in progress at a specific time in the past."
                )
            }
        }
    }
}
