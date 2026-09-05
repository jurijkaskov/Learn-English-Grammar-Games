package com.learnenglish.grammargames.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Standard component sizing tokens.
 */
@Immutable
object AppDimensions {
    // Touch target
    val minTouchTarget: Dp = 48.dp

    // Button heights
    val buttonPrimaryHeight: Dp = 54.dp
    val buttonSecondaryHeight: Dp = 48.dp
    val buttonAnswerMinHeight: Dp = 56.dp
    val buttonSmallHeight: Dp = 36.dp

    // Progress bar heights
    val progressLinearHeight: Dp = 10.dp
    val progressLinearThickHeight: Dp = 14.dp
    val progressCircularSize: Dp = 48.dp
    val progressCircularSmallSize: Dp = 24.dp

    // Icon sizes
    val iconExtraSmall: Dp = 16.dp
    val iconSmall: Dp = 20.dp
    val iconMedium: Dp = 24.dp
    val iconLarge: Dp = 32.dp
    val iconExtraLarge: Dp = 48.dp
    val iconJumbo: Dp = 64.dp

    // Chip & Badge heights
    val chipHeight: Dp = 32.dp
    val badgeHeight: Dp = 24.dp

    // Card sizes
    val learningCardMinHeight: Dp = 80.dp
    val gameCardMinHeight: Dp = 100.dp
    val avatarSizeSmall: Dp = 40.dp
    val avatarSizeMedium: Dp = 56.dp
    val avatarSizeLarge: Dp = 80.dp
}
