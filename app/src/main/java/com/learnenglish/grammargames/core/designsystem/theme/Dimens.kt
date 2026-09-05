package com.learnenglish.grammargames.core.designsystem.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Legacy Dimens reference mapping to centralized AppSpacing and AppDimensions tokens.
 */
object Dimens {
    val spacing2: Dp = AppSpacing.xxxs
    val spacing4: Dp = AppSpacing.xxs
    val spacing8: Dp = AppSpacing.xs
    val spacing10: Dp = 10.dp
    val spacing12: Dp = AppSpacing.sm
    val spacing16: Dp = AppSpacing.md
    val spacing20: Dp = AppSpacing.lg
    val spacing24: Dp = AppSpacing.xl
    val spacing32: Dp = AppSpacing.xxl
    val spacing40: Dp = AppSpacing.xxxl
    val spacing48: Dp = AppSpacing.huge

    val minTouchTarget: Dp = AppDimensions.minTouchTarget
    val cardElevation: Dp = AppElevation.level1
    val iconSizeSmall: Dp = AppDimensions.iconSmall
    val iconSizeMedium: Dp = AppDimensions.iconMedium
    val iconSizeLarge: Dp = AppDimensions.iconLarge
    val iconSizeExtraLarge: Dp = AppDimensions.iconExtraLarge
}
