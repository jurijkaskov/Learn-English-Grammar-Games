package com.learnenglish.grammargames.core.designsystem.component.chip

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.learnenglish.grammargames.core.designsystem.theme.AppDimensions
import com.learnenglish.grammargames.core.designsystem.theme.AppShapes
import com.learnenglish.grammargames.core.designsystem.theme.AppSpacing
import com.learnenglish.grammargames.core.designsystem.theme.grammarGamesColors

/**
 * Reusable pill-shaped semantic chip for CEFR levels, categories, and difficulty.
 */
@Composable
fun GrammarChip(
    text: String,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    leadingIcon: ImageVector? = null,
    onClick: (() -> Unit)? = null,
    testTag: String? = null
) {
    val colors = MaterialTheme.grammarGamesColors
    val bg = if (isSelected) colors.primaryAction else colors.primaryActionContainer
    val contentColor = if (isSelected) colors.onPrimaryAction else colors.onPrimaryActionContainer

    Box(
        modifier = modifier
            .height(AppDimensions.chipHeight)
            .clip(AppShapes.chip)
            .background(bg)
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier)
            .padding(horizontal = AppSpacing.sm, vertical = AppSpacing.xxs)
            .then(if (testTag != null) Modifier.testTag(testTag) else Modifier),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (leadingIcon != null) {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    tint = contentColor,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(AppSpacing.xxs))
            }
            Text(
                text = text,
                style = MaterialTheme.typography.labelSmall,
                color = contentColor
            )
        }
    }
}

/**
 * Compact visual badge (e.g. +20 XP, NEW, MASTERED).
 */
@Composable
fun GrammarBadge(
    text: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.grammarGamesColors.xpContainer,
    textColor: Color = MaterialTheme.grammarGamesColors.onXpContainer,
    leadingIcon: ImageVector? = null,
    testTag: String? = null
) {
    Box(
        modifier = modifier
            .height(AppDimensions.badgeHeight)
            .clip(AppShapes.badge)
            .background(backgroundColor)
            .padding(horizontal = AppSpacing.xs, vertical = 2.dp)
            .then(if (testTag != null) Modifier.testTag(testTag) else Modifier),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (leadingIcon != null) {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    tint = textColor,
                    modifier = Modifier.size(12.dp)
                )
                Spacer(modifier = Modifier.width(AppSpacing.xxxs))
            }
            Text(
                text = text,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = textColor
            )
        }
    }
}

/**
 * Clean vector star rating (0, 1, 2, or 3 stars) for activity scores and unit mastery.
 */
@Composable
fun GrammarStarRating(
    stars: Int,
    modifier: Modifier = Modifier,
    maxStars: Int = 3,
    starSize: Dp = 24.dp,
    testTag: String? = null
) {
    val colors = MaterialTheme.grammarGamesColors
    val filledStars = stars.coerceIn(0, maxStars)

    Row(
        modifier = modifier.then(if (testTag != null) Modifier.testTag(testTag) else Modifier),
        horizontalArrangement = Arrangement.spacedBy(AppSpacing.xxs),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 1..maxStars) {
            val isFilled = i <= filledStars
            Icon(
                imageVector = if (isFilled) Icons.Filled.Star else Icons.Outlined.Star,
                contentDescription = if (isFilled) "Star $i of $maxStars" else "Empty star",
                tint = if (isFilled) colors.xp else colors.locked,
                modifier = Modifier.size(starSize)
            )
        }
    }
}
