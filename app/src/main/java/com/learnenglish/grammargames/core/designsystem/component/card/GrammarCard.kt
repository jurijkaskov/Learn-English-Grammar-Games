package com.learnenglish.grammargames.core.designsystem.component.card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.learnenglish.grammargames.core.designsystem.component.button.GrammarIconButton
import com.learnenglish.grammargames.core.designsystem.component.text.GrammarExampleText
import com.learnenglish.grammargames.core.designsystem.component.text.GrammarFormulaText
import com.learnenglish.grammargames.core.designsystem.theme.AppDimensions
import com.learnenglish.grammargames.core.designsystem.theme.AppElevation
import com.learnenglish.grammargames.core.designsystem.theme.AppMotion
import com.learnenglish.grammargames.core.designsystem.theme.AppShapes
import com.learnenglish.grammargames.core.designsystem.theme.AppSpacing
import com.learnenglish.grammargames.core.designsystem.theme.grammarGamesColors

/**
 * Base card primitive across the whole application.
 * Provides soft rounded contours, subtle borders, optional tactile elevation, and click ripple.
 */
@Composable
fun GrammarCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    enabled: Boolean = true,
    shape: Shape = AppShapes.cardBase,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    border: BorderStroke? = BorderStroke(AppElevation.borderHairline, MaterialTheme.colorScheme.outlineVariant),
    elevation: Dp = AppElevation.level1,
    contentPadding: PaddingValues = PaddingValues(AppSpacing.cardPadding),
    testTag: String? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val isClickable = onClick != null && enabled
    val scale = if (isPressed && isClickable) AppMotion.pressScale else 1.0f

    Surface(
        modifier = modifier
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .clip(shape)
            .then(
                if (isClickable) {
                    Modifier.clickable(
                        interactionSource = interactionSource,
                        indication = ripple(),
                        role = Role.Button,
                        onClick = onClick!!
                    )
                } else Modifier
            )
            .then(if (testTag != null) Modifier.testTag(testTag) else Modifier),
        shape = shape,
        color = containerColor,
        contentColor = contentColor,
        border = border,
        shadowElevation = elevation
    ) {
        Column(
            modifier = Modifier.padding(contentPadding),
            content = content
        )
    }
}

/**
 * Pedagogical learning card for grammar topics, syllabi, and active units.
 */
@Composable
fun GrammarLearningCard(
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: @Composable (() -> Unit)? = null,
    badgeText: String? = null,
    isCompleted: Boolean = false,
    isLocked: Boolean = false,
    progress: Float? = null,
    testTag: String? = null
) {
    val colors = MaterialTheme.grammarGamesColors
    val containerColor = if (isLocked) colors.lockedContainer else MaterialTheme.colorScheme.surface

    GrammarCard(
        modifier = modifier.fillMaxWidth(),
        onClick = if (!isLocked) onClick else null,
        enabled = !isLocked,
        shape = AppShapes.cardLearning,
        containerColor = containerColor,
        testTag = testTag
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (leadingIcon != null) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(AppShapes.medium)
                        .background(if (isLocked) colors.locked else colors.primaryActionContainer),
                    contentAlignment = Alignment.Center
                ) {
                    leadingIcon()
                }
                Spacer(modifier = Modifier.width(AppSpacing.md))
            }

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        color = if (isLocked) colors.textDisabled else MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1f, fill = false)
                    )
                    if (badgeText != null) {
                        Spacer(modifier = Modifier.width(AppSpacing.xs))
                        Box(
                            modifier = Modifier
                                .clip(AppShapes.pill)
                                .background(colors.primaryActionContainer)
                                .padding(horizontal = AppSpacing.xs, vertical = 2.dp)
                        ) {
                            Text(
                                text = badgeText,
                                style = MaterialTheme.typography.labelSmall,
                                color = colors.primaryAction
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (isLocked) colors.textDisabled else colors.textSecondary
                )
            }

            Spacer(modifier = Modifier.width(AppSpacing.sm))

            if (isLocked) {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Locked",
                    tint = colors.locked,
                    modifier = Modifier.size(AppDimensions.iconSmall)
                )
            } else if (isCompleted) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Completed",
                    tint = colors.success,
                    modifier = Modifier.size(AppDimensions.iconMedium)
                )
            }
        }
    }
}

/**
 * Card for Games Hub mini-games (Speed Challenge, Sentence Builder, etc.).
 */
@Composable
fun GrammarGameCard(
    title: String,
    description: String,
    xpReward: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    difficultyLabel: String? = null,
    isLocked: Boolean = false,
    leadingIcon: ImageVector? = null,
    testTag: String? = null
) {
    val colors = MaterialTheme.grammarGamesColors

    GrammarCard(
        modifier = modifier.fillMaxWidth(),
        onClick = if (!isLocked) onClick else null,
        enabled = !isLocked,
        shape = AppShapes.cardGame,
        containerColor = if (isLocked) colors.lockedContainer else MaterialTheme.colorScheme.surface,
        testTag = testTag
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (leadingIcon != null) {
                Box(
                    modifier = Modifier
                        .size(54.dp)
                        .clip(AppShapes.large)
                        .background(if (isLocked) colors.locked.copy(alpha = 0.2f) else colors.primaryActionContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = leadingIcon,
                        contentDescription = null,
                        tint = if (isLocked) colors.locked else colors.primaryAction,
                        modifier = Modifier.size(AppDimensions.iconLarge)
                    )
                }
                Spacer(modifier = Modifier.width(AppSpacing.md))
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (isLocked) colors.textDisabled else MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (isLocked) colors.textDisabled else colors.textSecondary
                )
                Spacer(modifier = Modifier.height(AppSpacing.xs))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(AppSpacing.xs)
                ) {
                    if (difficultyLabel != null) {
                        Box(
                            modifier = Modifier
                                .clip(AppShapes.pill)
                                .background(colors.secondaryActionContainer)
                                .padding(horizontal = AppSpacing.xs, vertical = 2.dp)
                        ) {
                            Text(
                                text = difficultyLabel,
                                style = MaterialTheme.typography.labelSmall,
                                color = colors.secondaryAction
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .clip(AppShapes.pill)
                            .background(colors.xpContainer)
                            .padding(horizontal = AppSpacing.xs, vertical = 2.dp)
                    ) {
                        Text(
                            text = "+$xpReward XP",
                            style = MaterialTheme.typography.labelSmall,
                            color = colors.onXpContainer
                        )
                    }
                }
            }

            if (isLocked) {
                Spacer(modifier = Modifier.width(AppSpacing.sm))
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Locked game",
                    tint = colors.locked,
                    modifier = Modifier.size(AppDimensions.iconSmall)
                )
            }
        }
    }
}

/**
 * Educational grammar rule card highlighting core sentence structure formulas.
 */
@Composable
fun GrammarRuleCard(
    formula: String,
    explanation: String,
    modifier: Modifier = Modifier,
    testTag: String? = null
) {
    val colors = MaterialTheme.grammarGamesColors

    GrammarCard(
        modifier = modifier.fillMaxWidth(),
        shape = AppShapes.cardEducational,
        containerColor = colors.primaryActionContainer,
        border = BorderStroke(AppElevation.borderHairline, colors.primaryAction.copy(alpha = 0.2f)),
        testTag = testTag
    ) {
        GrammarFormulaText(
            formula = formula,
            color = colors.primaryAction
        )
        Spacer(modifier = Modifier.height(AppSpacing.xs))
        Text(
            text = explanation,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

/**
 * Pedagogical example card with optional audio pronunciation and translation.
 */
@Composable
fun GrammarExampleCard(
    sentence: String,
    modifier: Modifier = Modifier,
    translation: String? = null,
    highlightKeyword: String? = null,
    onAudioClick: (() -> Unit)? = null,
    testTag: String? = null
) {
    GrammarCard(
        modifier = modifier.fillMaxWidth(),
        shape = AppShapes.cardEducational,
        containerColor = MaterialTheme.colorScheme.surface,
        border = BorderStroke(AppElevation.borderHairline, MaterialTheme.colorScheme.outlineVariant),
        testTag = testTag
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                GrammarExampleText(
                    text = sentence,
                    highlightKeyword = highlightKeyword
                )
                if (!translation.isNullOrBlank()) {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = translation,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.grammarGamesColors.textSecondary
                    )
                }
            }
            if (onAudioClick != null) {
                Spacer(modifier = Modifier.width(AppSpacing.xs))
                GrammarIconButton(
                    icon = Icons.Default.VolumeUp,
                    contentDescription = "Listen to pronunciation",
                    onClick = onAudioClick,
                    tint = MaterialTheme.grammarGamesColors.primaryAction
                )
            }
        }
    }
}

/**
 * Hint card providing a gentle reminder or pedagogical tip.
 */
@Composable
fun GrammarHintCard(
    tip: String,
    modifier: Modifier = Modifier,
    testTag: String? = null
) {
    val colors = MaterialTheme.grammarGamesColors

    GrammarCard(
        modifier = modifier.fillMaxWidth(),
        shape = AppShapes.cardEducational,
        containerColor = colors.warningContainer,
        border = BorderStroke(AppElevation.borderHairline, colors.warning.copy(alpha = 0.3f)),
        testTag = testTag
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            Icon(
                imageVector = Icons.Default.Lightbulb,
                contentDescription = null,
                tint = colors.warning,
                modifier = Modifier.size(AppDimensions.iconSmall)
            )
            Spacer(modifier = Modifier.width(AppSpacing.xs))
            Text(
                text = tip,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
