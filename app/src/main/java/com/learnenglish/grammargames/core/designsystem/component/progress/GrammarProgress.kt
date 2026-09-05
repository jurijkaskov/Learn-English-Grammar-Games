package com.learnenglish.grammargames.core.designsystem.component.progress

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.learnenglish.grammargames.core.designsystem.theme.AppDimensions
import com.learnenglish.grammargames.core.designsystem.theme.AppMotion
import com.learnenglish.grammargames.core.designsystem.theme.AppShapes
import com.learnenglish.grammargames.core.designsystem.theme.AppSpacing
import com.learnenglish.grammargames.core.designsystem.theme.grammarGamesColors

/**
 * Clamps raw progress safely between 0f and 1f.
 */
fun safeClampProgress(progress: Float): Float = progress.coerceIn(0f, 1f)

/**
 * Prominent, rounded animated linear progress bar.
 */
@Composable
fun GrammarLinearProgress(
    progress: Float,
    modifier: Modifier = Modifier,
    height: Dp = AppDimensions.progressLinearHeight,
    color: Color = MaterialTheme.grammarGamesColors.primaryAction,
    trackColor: Color = MaterialTheme.grammarGamesColors.lockedContainer,
    testTag: String? = null
) {
    val clamped = safeClampProgress(progress)
    val animatedProgress by animateFloatAsState(
        targetValue = clamped,
        animationSpec = tween(durationMillis = AppMotion.durationNormal, easing = FastOutSlowInEasing),
        label = "linearProgress"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .clip(AppShapes.progressBar)
            .background(trackColor)
            .then(if (testTag != null) Modifier.testTag(testTag) else Modifier)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(animatedProgress)
                .fillMaxHeight()
                .clip(AppShapes.progressBar)
                .background(color)
        )
    }
}

/**
 * Animated circular progress indicator with rounded stroke caps.
 */
@Composable
fun GrammarCircularProgress(
    progress: Float,
    modifier: Modifier = Modifier,
    size: Dp = AppDimensions.progressCircularSize,
    strokeWidth: Dp = 4.dp,
    color: Color = MaterialTheme.grammarGamesColors.primaryAction,
    trackColor: Color = MaterialTheme.grammarGamesColors.lockedContainer,
    testTag: String? = null
) {
    val clamped = safeClampProgress(progress)
    val animatedProgress by animateFloatAsState(
        targetValue = clamped,
        animationSpec = tween(durationMillis = AppMotion.durationNormal, easing = FastOutSlowInEasing),
        label = "circularProgress"
    )

    CircularProgressIndicator(
        progress = { animatedProgress },
        modifier = modifier
            .size(size)
            .then(if (testTag != null) Modifier.testTag(testTag) else Modifier),
        color = color,
        trackColor = trackColor,
        strokeWidth = strokeWidth,
        strokeCap = StrokeCap.Round
    )
}

/**
 * Mastery progress for grammar units and topics (e.g. Present Perfect — 72% Mastery).
 */
@Composable
fun GrammarMasteryProgress(
    title: String,
    masteryPercentage: Int,
    modifier: Modifier = Modifier,
    testTag: String? = null
) {
    val clampedPercentage = masteryPercentage.coerceIn(0, 100)
    val progressFloat = clampedPercentage / 100f
    val colors = MaterialTheme.grammarGamesColors

    Column(
        modifier = modifier
            .fillMaxWidth()
            .then(if (testTag != null) Modifier.testTag(testTag) else Modifier)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "$clampedPercentage% Mastery",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = colors.primaryAction
            )
        }
        Spacer(modifier = Modifier.height(AppSpacing.xs))
        GrammarLinearProgress(
            progress = progressFloat,
            color = colors.primaryAction
        )
    }
}

/**
 * XP progress bar with level label and experience count (e.g. Level 3 — 450 / 1000 XP).
 */
@Composable
fun GrammarXpProgress(
    currentXp: Int,
    targetXp: Int,
    level: Int,
    modifier: Modifier = Modifier,
    testTag: String? = null
) {
    val safeTarget = if (targetXp <= 0) 1 else targetXp
    val safeCurrent = currentXp.coerceAtLeast(0)
    val progress = (safeCurrent.toFloat() / safeTarget.toFloat()).coerceIn(0f, 1f)
    val colors = MaterialTheme.grammarGamesColors

    Column(
        modifier = modifier
            .fillMaxWidth()
            .then(if (testTag != null) Modifier.testTag(testTag) else Modifier)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Level $level",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = colors.onXpContainer
            )
            Text(
                text = "$safeCurrent / $safeTarget XP",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.SemiBold,
                color = colors.textSecondary
            )
        }
        Spacer(modifier = Modifier.height(AppSpacing.xxs))
        GrammarLinearProgress(
            progress = progress,
            color = colors.xp,
            height = 8.dp
        )
    }
}

/**
 * Daily goal progress showing completed activities against daily goal target.
 */
@Composable
fun GrammarDailyGoalProgress(
    completed: Int,
    total: Int,
    modifier: Modifier = Modifier,
    testTag: String? = null
) {
    val safeTotal = if (total <= 0) 1 else total
    val safeCompleted = completed.coerceAtLeast(0)
    val progress = (safeCompleted.toFloat() / safeTotal.toFloat()).coerceIn(0f, 1f)

    Row(
        modifier = modifier.then(if (testTag != null) Modifier.testTag(testTag) else Modifier),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GrammarCircularProgress(
            progress = progress,
            size = 36.dp,
            strokeWidth = 3.5.dp
        )
        Spacer(modifier = Modifier.width(AppSpacing.xs))
        Column {
            Text(
                text = "Daily Goal",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.grammarGamesColors.textSecondary
            )
            Text(
                text = "$safeCompleted of $safeTotal completed",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
