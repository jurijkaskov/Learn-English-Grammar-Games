package com.learnenglish.grammargames.core.designsystem.component.feedback

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.learnenglish.grammargames.core.designsystem.component.button.GrammarPrimaryButton
import com.learnenglish.grammargames.core.designsystem.state.FeedbackType
import com.learnenglish.grammargames.core.designsystem.theme.AppDimensions
import com.learnenglish.grammargames.core.designsystem.theme.AppElevation
import com.learnenglish.grammargames.core.designsystem.theme.AppShapes
import com.learnenglish.grammargames.core.designsystem.theme.AppSpacing
import com.learnenglish.grammargames.core.designsystem.theme.grammarGamesColors

/**
 * Universal feedback panel rendered at the bottom of exercise scenes after an answer is submitted.
 * Designed with positive, supportive reinforcement tone.
 */
@Composable
fun GrammarFeedbackPanel(
    type: FeedbackType,
    title: String,
    modifier: Modifier = Modifier,
    description: String? = null,
    actionText: String? = null,
    onActionClick: (() -> Unit)? = null,
    testTag: String? = null
) {
    val colors = MaterialTheme.grammarGamesColors

    val (bgColor, iconBg, iconColor, iconVector) = when (type) {
        FeedbackType.CORRECT -> Tuple4(colors.successContainer, colors.success, Color.White, Icons.Default.Check)
        FeedbackType.INCORRECT -> Tuple4(colors.errorContainer, colors.error, Color.White, Icons.Default.Close)
        FeedbackType.HINT -> Tuple4(colors.warningContainer, colors.warning, Color.White, Icons.Default.Lightbulb)
        FeedbackType.INFO -> Tuple4(colors.secondaryActionContainer, colors.secondaryAction, Color.White, Icons.Default.Info)
        FeedbackType.WARNING -> Tuple4(colors.warningContainer, colors.warning, Color.White, Icons.Default.Warning)
    }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .then(if (testTag != null) Modifier.testTag(testTag) else Modifier),
        shape = AppShapes.bottomSheet,
        color = bgColor,
        border = BorderStroke(AppElevation.borderHairline, iconBg.copy(alpha = 0.3f)),
        shadowElevation = AppElevation.level3
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppSpacing.cardPaddingLarge)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(AppShapes.circle)
                        .background(iconBg),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = iconVector,
                        contentDescription = null,
                        tint = iconColor,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Spacer(modifier = Modifier.width(AppSpacing.md))

                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            if (!description.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(AppSpacing.xs))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = colors.textSecondary,
                    modifier = Modifier.padding(start = 36.dp + AppSpacing.md)
                )
            }

            if (actionText != null && onActionClick != null) {
                Spacer(modifier = Modifier.height(AppSpacing.lg))
                GrammarPrimaryButton(
                    text = actionText,
                    onClick = onActionClick,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

/**
 * Convenience wrapper for positive answer validation.
 */
@Composable
fun GrammarCorrectFeedbackPanel(
    onContinueClick: () -> Unit,
    modifier: Modifier = Modifier,
    title: String = "Nice! That's correct",
    description: String? = null,
    buttonText: String = "Continue"
) {
    GrammarFeedbackPanel(
        type = FeedbackType.CORRECT,
        title = title,
        description = description,
        actionText = buttonText,
        onActionClick = onContinueClick,
        modifier = modifier,
        testTag = "feedback_panel_correct"
    )
}

/**
 * Convenience wrapper for incorrect answer feedback with educational guidance.
 */
@Composable
fun GrammarWrongFeedbackPanel(
    explanation: String,
    onContinueClick: () -> Unit,
    modifier: Modifier = Modifier,
    title: String = "Not quite",
    buttonText: String = "Got it"
) {
    GrammarFeedbackPanel(
        type = FeedbackType.INCORRECT,
        title = title,
        description = explanation,
        actionText = buttonText,
        onActionClick = onContinueClick,
        modifier = modifier,
        testTag = "feedback_panel_wrong"
    )
}

private data class Tuple4<A, B, C, D>(val a: A, val b: B, val c: C, val d: D)
