package com.learnenglish.grammargames.core.designsystem.theme

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import kotlinx.coroutines.launch

/**
 * Motion and animation tokens for playful, tactile responsiveness.
 */
@Immutable
object AppMotion {
    const val durationFast: Int = 120
    const val durationNormal: Int = 220
    const val durationSlow: Int = 350
    const val durationShake: Int = 300

    const val pressScale: Float = 0.98f

    val springBouncy = spring<Float>(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessLow
    )

    val springSnappy = spring<Float>(
        dampingRatio = Spring.DampingRatioNoBouncy,
        stiffness = Spring.StiffnessMedium
    )

    val tweenFast = tween<Float>(durationMillis = durationFast, easing = FastOutSlowInEasing)
    val tweenNormal = tween<Float>(durationMillis = durationNormal, easing = FastOutSlowInEasing)
}

/**
 * Custom tactile press clickable modifier.
 * Scales down subtly to 0.98f on press and triggers the callback on release.
 */
@Composable
fun Modifier.grammarPressClickable(
    enabled: Boolean = true,
    onClickLabel: String? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: () -> Unit
): Modifier {
    val isPressed by interactionSource.collectIsPressedAsState()
    val targetScale = if (isPressed && enabled) AppMotion.pressScale else 1.0f

    return this
        .graphicsLayer {
            scaleX = targetScale
            scaleY = targetScale
        }
        .clickable(
            interactionSource = interactionSource,
            indication = ripple(bounded = true),
            enabled = enabled,
            onClickLabel = onClickLabel,
            onClick = onClick
        )
}
