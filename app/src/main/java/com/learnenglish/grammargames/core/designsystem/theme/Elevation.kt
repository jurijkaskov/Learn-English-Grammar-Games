package com.learnenglish.grammargames.core.designsystem.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Elevation and subtle depth tokens for soft, clean gaming UI.
 * Avoids heavy, harsh Material 2/3 gray shadows in favor of tinted, light shadows and crisp borders.
 */
@Immutable
object AppElevation {
    val flat: Dp = 0.dp
    val level1: Dp = 2.dp
    val level2: Dp = 4.dp
    val level3: Dp = 8.dp
    val level4: Dp = 12.dp

    // Border strokes
    val borderHairline: Dp = 1.dp
    val borderStandard: Dp = 1.5.dp
    val borderThick: Dp = 2.dp
    val borderSelected: Dp = 2.5.dp
}

/**
 * Subtle border helper for cards and buttons.
 */
@Composable
fun subtleBorder(
    color: Color = OutlineLight,
    width: Dp = AppElevation.borderHairline
): BorderStroke = BorderStroke(width = width, color = color)
