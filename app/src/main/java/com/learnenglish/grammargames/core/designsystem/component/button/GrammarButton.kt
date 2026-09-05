package com.learnenglish.grammargames.core.designsystem.component.button

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.learnenglish.grammargames.core.designsystem.state.ExerciseAnswerState
import com.learnenglish.grammargames.core.designsystem.theme.AppDimensions
import com.learnenglish.grammargames.core.designsystem.theme.AppElevation
import com.learnenglish.grammargames.core.designsystem.theme.AppMotion
import com.learnenglish.grammargames.core.designsystem.theme.AppShapes
import com.learnenglish.grammargames.core.designsystem.theme.AppSpacing
import com.learnenglish.grammargames.core.designsystem.theme.grammarGamesColors

/**
 * High-emphasis Call-To-Action button (Continue, Start, Check, Play, Finish).
 */
@Composable
fun GrammarPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    testTag: String? = null
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale = if (isPressed && enabled && !isLoading) AppMotion.pressScale else 1.0f

    val colors = MaterialTheme.grammarGamesColors
    val backgroundColor = if (enabled) colors.primaryAction else colors.lockedContainer
    val contentColor = if (enabled) colors.onPrimaryAction else colors.onLocked

    Surface(
        modifier = modifier
            .defaultMinSize(minHeight = AppDimensions.buttonPrimaryHeight)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .clip(AppShapes.buttonPrimary)
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(color = Color.White.copy(alpha = 0.3f)),
                enabled = enabled && !isLoading,
                role = Role.Button,
                onClick = onClick
            )
            .then(if (testTag != null) Modifier.testTag(testTag) else Modifier),
        shape = AppShapes.buttonPrimary,
        color = backgroundColor,
        shadowElevation = if (enabled) AppElevation.level1 else AppElevation.flat
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppSpacing.xl, vertical = AppSpacing.md),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = contentColor,
                    strokeWidth = 2.5.dp
                )
            } else {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    if (leadingIcon != null) {
                        Icon(
                            imageVector = leadingIcon,
                            contentDescription = null,
                            tint = contentColor,
                            modifier = Modifier.size(AppDimensions.iconSmall)
                        )
                        Spacer(modifier = Modifier.width(AppSpacing.xs))
                    }
                    Text(
                        text = text,
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = contentColor,
                        textAlign = TextAlign.Center
                    )
                    if (trailingIcon != null) {
                        Spacer(modifier = Modifier.width(AppSpacing.xs))
                        Icon(
                            imageVector = trailingIcon,
                            contentDescription = null,
                            tint = contentColor,
                            modifier = Modifier.size(AppDimensions.iconSmall)
                        )
                    }
                }
            }
        }
    }
}

/**
 * Secondary button for alternative actions (Review, Try Again, Details).
 */
@Composable
fun GrammarSecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    testTag: String? = null
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale = if (isPressed && enabled) AppMotion.pressScale else 1.0f

    val colors = MaterialTheme.grammarGamesColors
    val backgroundColor = if (enabled) colors.primaryActionContainer else colors.lockedContainer
    val contentColor = if (enabled) colors.onPrimaryActionContainer else colors.onLocked

    Surface(
        modifier = modifier
            .defaultMinSize(minHeight = AppDimensions.buttonSecondaryHeight)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .clip(AppShapes.buttonSecondary)
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(),
                enabled = enabled,
                role = Role.Button,
                onClick = onClick
            )
            .then(if (testTag != null) Modifier.testTag(testTag) else Modifier),
        shape = AppShapes.buttonSecondary,
        color = backgroundColor,
        border = BorderStroke(AppElevation.borderHairline, colors.primaryAction.copy(alpha = 0.25f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppSpacing.lg, vertical = AppSpacing.sm),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (leadingIcon != null) {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    tint = contentColor,
                    modifier = Modifier.size(AppDimensions.iconSmall)
                )
                Spacer(modifier = Modifier.width(AppSpacing.xs))
            }
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.SemiBold,
                color = contentColor,
                textAlign = TextAlign.Center
            )
            if (trailingIcon != null) {
                Spacer(modifier = Modifier.width(AppSpacing.xs))
                Icon(
                    imageVector = trailingIcon,
                    contentDescription = null,
                    tint = contentColor,
                    modifier = Modifier.size(AppDimensions.iconSmall)
                )
            }
        }
    }
}

/**
 * Tertiary low-emphasis button (Skip, Not now, View all).
 */
@Composable
fun GrammarTertiaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: ImageVector? = null,
    testTag: String? = null
) {
    val colors = MaterialTheme.grammarGamesColors
    val contentColor = if (enabled) colors.primaryAction else colors.textDisabled

    Box(
        modifier = modifier
            .minimumInteractiveComponentSize()
            .clip(AppShapes.buttonSecondary)
            .clickable(
                enabled = enabled,
                role = Role.Button,
                onClick = onClick
            )
            .padding(horizontal = AppSpacing.md, vertical = AppSpacing.sm)
            .then(if (testTag != null) Modifier.testTag(testTag) else Modifier),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (leadingIcon != null) {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    tint = contentColor,
                    modifier = Modifier.size(AppDimensions.iconSmall)
                )
                Spacer(modifier = Modifier.width(AppSpacing.xxs))
            }
            Text(
                text = text,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold,
                color = contentColor
            )
        }
    }
}

/**
 * Accessible rounded icon button with 48dp minimum interactive touch target.
 */
@Composable
fun GrammarIconButton(
    icon: ImageVector,
    contentDescription: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    tint: Color = MaterialTheme.colorScheme.onSurface,
    backgroundColor: Color = Color.Transparent,
    testTag: String? = null
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale = if (isPressed && enabled) AppMotion.pressScale else 1.0f

    Box(
        modifier = modifier
            .minimumInteractiveComponentSize()
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .clip(AppShapes.circle)
            .background(backgroundColor)
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(bounded = true),
                enabled = enabled,
                role = Role.Button,
                onClick = onClick
            )
            .padding(AppSpacing.xs)
            .then(if (testTag != null) Modifier.testTag(testTag) else Modifier),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = if (enabled) tint else MaterialTheme.grammarGamesColors.textDisabled,
            modifier = Modifier.size(AppDimensions.iconMedium)
        )
    }
}

/**
 * Dedicated pedagogical Answer Button for exercises, multiple-choice drills, and tests.
 * Supports Default, Selected, Correct, Wrong, and Disabled states with color transitions,
 * semantic borders, and auxiliary check/cross icons for color-blind accessibility.
 */
@Composable
fun GrammarAnswerButton(
    text: String,
    state: ExerciseAnswerState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    optionLabel: String? = null, // e.g. "A", "B", "C"
    testTag: String? = null
) {
    val colors = MaterialTheme.grammarGamesColors
    val isInteractive = state != ExerciseAnswerState.DISABLED

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale = if (isPressed && isInteractive) AppMotion.pressScale else 1.0f

    // Target background color animated softly
    val targetBgColor = when (state) {
        ExerciseAnswerState.DEFAULT -> MaterialTheme.colorScheme.surface
        ExerciseAnswerState.SELECTED -> colors.selectedContainer
        ExerciseAnswerState.CORRECT -> colors.successContainer
        ExerciseAnswerState.WRONG -> colors.errorContainer
        ExerciseAnswerState.DISABLED -> colors.lockedContainer
    }
    val animatedBgColor by animateColorAsState(
        targetValue = targetBgColor,
        animationSpec = tween(durationMillis = AppMotion.durationNormal),
        label = "answerBg"
    )

    // Target border color & width
    val (borderColor, borderWidth) = when (state) {
        ExerciseAnswerState.DEFAULT -> MaterialTheme.colorScheme.outline to AppElevation.borderHairline
        ExerciseAnswerState.SELECTED -> colors.selected to AppElevation.borderSelected
        ExerciseAnswerState.CORRECT -> colors.success to AppElevation.borderThick
        ExerciseAnswerState.WRONG -> colors.error to AppElevation.borderThick
        ExerciseAnswerState.DISABLED -> Color.Transparent to AppElevation.flat
    }
    val animatedBorderColor by animateColorAsState(
        targetValue = borderColor,
        animationSpec = tween(durationMillis = AppMotion.durationNormal),
        label = "answerBorder"
    )

    // Text color
    val textColor = when (state) {
        ExerciseAnswerState.DEFAULT -> MaterialTheme.colorScheme.onSurface
        ExerciseAnswerState.SELECTED -> colors.onSelectedContainer
        ExerciseAnswerState.CORRECT -> colors.onSuccessContainer
        ExerciseAnswerState.WRONG -> colors.onErrorContainer
        ExerciseAnswerState.DISABLED -> colors.textDisabled
    }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = AppDimensions.buttonAnswerMinHeight)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .clip(AppShapes.buttonAnswer)
            .border(borderWidth, animatedBorderColor, AppShapes.buttonAnswer)
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(),
                enabled = isInteractive,
                role = Role.Button,
                onClick = onClick
            )
            .then(if (testTag != null) Modifier.testTag(testTag) else Modifier),
        shape = AppShapes.buttonAnswer,
        color = animatedBgColor,
        shadowElevation = if (state == ExerciseAnswerState.DEFAULT) AppElevation.level1 else AppElevation.flat
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppSpacing.md, vertical = AppSpacing.sm),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Optional Option Badge (A, B, C...)
            if (!optionLabel.isNullOrBlank()) {
                val labelBg = when (state) {
                    ExerciseAnswerState.SELECTED -> colors.primaryAction
                    ExerciseAnswerState.CORRECT -> colors.success
                    ExerciseAnswerState.WRONG -> colors.error
                    else -> colors.lockedContainer
                }
                val labelText = when (state) {
                    ExerciseAnswerState.SELECTED,
                    ExerciseAnswerState.CORRECT,
                    ExerciseAnswerState.WRONG -> Color.White
                    else -> MaterialTheme.colorScheme.onSurface
                }
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(AppShapes.small)
                        .background(labelBg),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = optionLabel,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = labelText
                    )
                }
                Spacer(modifier = Modifier.width(AppSpacing.md))
            }

            // Text
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = if (state == ExerciseAnswerState.SELECTED || state == ExerciseAnswerState.CORRECT) FontWeight.Bold else FontWeight.Medium,
                color = textColor,
                modifier = Modifier.weight(1f)
            )

            // State Icon Indicator for Accessibility
            when (state) {
                ExerciseAnswerState.CORRECT -> {
                    Spacer(modifier = Modifier.width(AppSpacing.xs))
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(AppShapes.circle)
                            .background(colors.success),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Correct",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
                ExerciseAnswerState.WRONG -> {
                    Spacer(modifier = Modifier.width(AppSpacing.xs))
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(AppShapes.circle)
                            .background(colors.error),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Incorrect",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
                else -> Unit
            }
        }
    }
}
