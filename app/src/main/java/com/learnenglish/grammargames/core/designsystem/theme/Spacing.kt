package com.learnenglish.grammargames.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Centralized, consistent spacing scale based on a flexible 4dp/8dp grid.
 */
@Immutable
object AppSpacing {
    val none: Dp = 0.dp
    val xxxs: Dp = 2.dp
    val xxs: Dp = 4.dp
    val xs: Dp = 8.dp
    val sm: Dp = 12.dp
    val md: Dp = 16.dp
    val lg: Dp = 20.dp
    val xl: Dp = 24.dp
    val xxl: Dp = 32.dp
    val xxxl: Dp = 40.dp
    val huge: Dp = 48.dp
    val giant: Dp = 64.dp

    // Semantic layout spacing
    val screenHorizontalPhone: Dp = 16.dp
    val screenHorizontalTablet: Dp = 24.dp
    val screenVertical: Dp = 16.dp
    val cardPadding: Dp = 16.dp
    val cardPaddingLarge: Dp = 20.dp
    val sectionSpacing: Dp = 24.dp
    val itemSpacing: Dp = 12.dp
    val inlineSpacing: Dp = 8.dp
}
