package com.learnenglish.grammargames.core.designsystem.component.panel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.learnenglish.grammargames.core.designsystem.theme.AppDimensions
import com.learnenglish.grammargames.core.designsystem.theme.AppShapes
import com.learnenglish.grammargames.core.designsystem.theme.AppSpacing
import com.learnenglish.grammargames.core.designsystem.theme.grammarGamesColors

/**
 * XP Pill indicator for TopBar and HUD headers.
 */
@Composable
fun GrammarXpBadge(
    xp: Int,
    modifier: Modifier = Modifier,
    testTag: String? = null
) {
    val colors = MaterialTheme.grammarGamesColors
    Box(
        modifier = modifier
            .height(AppDimensions.chipHeight)
            .clip(AppShapes.pill)
            .background(colors.xpContainer)
            .padding(horizontal = AppSpacing.sm, vertical = AppSpacing.xxs)
            .then(if (testTag != null) Modifier.testTag(testTag) else Modifier),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Stars,
                contentDescription = null,
                tint = colors.onXpContainer,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(AppSpacing.xxs))
            Text(
                text = "$xp XP",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = colors.onXpContainer
            )
        }
    }
}

/**
 * Daily Streak counter badge with fire icon.
 */
@Composable
fun GrammarStreakBadge(
    streakDays: Int,
    modifier: Modifier = Modifier,
    testTag: String? = null
) {
    val colors = MaterialTheme.grammarGamesColors
    Box(
        modifier = modifier
            .height(AppDimensions.chipHeight)
            .clip(AppShapes.pill)
            .background(colors.warningContainer)
            .padding(horizontal = AppSpacing.sm, vertical = AppSpacing.xxs)
            .then(if (testTag != null) Modifier.testTag(testTag) else Modifier),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.LocalFireDepartment,
                contentDescription = null,
                tint = colors.warning,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(AppSpacing.xxs))
            Text(
                text = "$streakDays ${if (streakDays == 1) "day" else "days"}",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = colors.onWarningContainer
            )
        }
    }
}

/**
 * Hearts counter for timed quizzes and lives.
 */
@Composable
fun GrammarHeartCounter(
    hearts: Int,
    maxHearts: Int = 5,
    modifier: Modifier = Modifier,
    testTag: String? = null
) {
    val colors = MaterialTheme.grammarGamesColors
    Box(
        modifier = modifier
            .height(AppDimensions.chipHeight)
            .clip(AppShapes.pill)
            .background(colors.errorContainer)
            .padding(horizontal = AppSpacing.sm, vertical = AppSpacing.xxs)
            .then(if (testTag != null) Modifier.testTag(testTag) else Modifier),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Hearts remaining: $hearts of $maxHearts",
                tint = colors.error,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(AppSpacing.xxs))
            Text(
                text = "$hearts",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = colors.onErrorContainer
            )
        }
    }
}

/**
 * Game session timer badge.
 */
@Composable
fun GrammarTimerBadge(
    secondsRemaining: Int,
    modifier: Modifier = Modifier,
    isUrgent: Boolean = secondsRemaining <= 10,
    testTag: String? = null
) {
    val colors = MaterialTheme.grammarGamesColors
    val bg = if (isUrgent) colors.errorContainer else colors.secondaryActionContainer
    val contentColor = if (isUrgent) colors.onErrorContainer else colors.onSecondaryActionContainer
    val iconTint = if (isUrgent) colors.error else colors.secondaryAction

    Box(
        modifier = modifier
            .height(AppDimensions.chipHeight)
            .clip(AppShapes.pill)
            .background(bg)
            .padding(horizontal = AppSpacing.sm, vertical = AppSpacing.xxs)
            .then(if (testTag != null) Modifier.testTag(testTag) else Modifier),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Timer,
                contentDescription = "Time remaining: $secondsRemaining seconds",
                tint = iconTint,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(AppSpacing.xxs))
            Text(
                text = "${secondsRemaining}s",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = contentColor
            )
        }
    }
}
