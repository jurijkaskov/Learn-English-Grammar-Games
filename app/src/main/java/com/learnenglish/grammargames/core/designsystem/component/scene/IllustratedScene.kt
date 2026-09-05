package com.learnenglish.grammargames.core.designsystem.component.scene

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.learnenglish.grammargames.core.designsystem.theme.AppDimensions
import com.learnenglish.grammargames.core.designsystem.theme.AppShapes
import com.learnenglish.grammargames.core.designsystem.theme.AppSpacing
import com.learnenglish.grammargames.core.designsystem.theme.grammarGamesColors

/**
 * IllustratedScene: Reusable layer composition framework for game-world and narrative screens
 * (Grammar Journey, Home Header, Results celebration).
 *
 * Layer Hierarchy (Z-Order):
 * 1. [background]: Sky, distant gradient, distant mountains
 * 2. [midground]: Rolling hills, castles, paths, distant trees
 * 3. [foregroundDecoration]: Grassy tufts, rocks, flowers, ambient elements
 * 4. [content]: Primary interactive UI, cards, lesson nodes, character, buttons
 */
@Composable
fun IllustratedScene(
    modifier: Modifier = Modifier,
    background: @Composable BoxScope.() -> Unit = {},
    midground: @Composable BoxScope.() -> Unit = {},
    foregroundDecoration: @Composable BoxScope.() -> Unit = {},
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        // Layer 1: Background
        Box(
            modifier = Modifier.matchParentSize(),
            content = background
        )

        // Layer 2: Midground
        Box(
            modifier = Modifier.matchParentSize(),
            content = midground
        )

        // Layer 3: Foreground Decorative Accents
        Box(
            modifier = Modifier.matchParentSize(),
            content = foregroundDecoration
        )

        // Layer 4: Interactive Content & UI (Always top-level for accessibility & touch)
        Box(
            modifier = Modifier.fillMaxSize(),
            content = content
        )
    }
}

// -----------------------------------------------------------------------------
// Vector / Compose Art Direction Placeholders for Visual QA & Prototype Canvas
// (Follows ART_DIRECTION.md: soft rounded 2D, no black outlines, gentle volume)
// -----------------------------------------------------------------------------

/**
 * Placeholder cartoon cloud with soft multiple lobes.
 */
@Composable
fun ArtPlaceholderCloud(
    modifier: Modifier = Modifier,
    scale: Dp = 80.dp
) {
    Box(
        modifier = modifier
            .width(scale * 1.5f)
            .height(scale * 0.75f)
    ) {
        // Shadow base
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(0.9f)
                .height(scale * 0.45f)
                .clip(RoundedCornerShape(scale * 0.25f))
                .background(Color(0xFFE0EBF5))
        )
        // Main white lobes
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .size(scale * 0.6f)
                .clip(CircleShape)
                .background(Color.White)
        )
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = scale * 0.05f)
                .size(scale * 0.7f)
                .clip(CircleShape)
                .background(Color.White)
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(scale * 0.55f)
                .clip(CircleShape)
                .background(Color.White)
        )
    }
}

/**
 * Placeholder cartoon tree with rounded 3-tier crown and warm wood trunk.
 */
@Composable
fun ArtPlaceholderTree(
    modifier: Modifier = Modifier,
    height: Dp = 100.dp
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Multi-tier rounded foliage
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.width(height * 0.75f)
        ) {
            // Lower foliage (darker green for depth)
            Box(
                modifier = Modifier
                    .size(height * 0.65f)
                    .clip(CircleShape)
                    .background(Color(0xFF27AE60))
            )
            // Upper foliage (bright warm green with slight highlight)
            Box(
                modifier = Modifier
                    .offset(y = -(height * 0.12f))
                    .size(height * 0.55f)
                    .clip(CircleShape)
                    .background(
                        Brush.verticalGradient(
                            listOf(Color(0xFF2ECC71), Color(0xFF27AE60))
                        )
                    )
            )
            // Top highlight lobe
            Box(
                modifier = Modifier
                    .offset(x = -(height * 0.08f), y = -(height * 0.20f))
                    .size(height * 0.25f)
                    .clip(CircleShape)
                    .background(Color(0xFF7DDF9C).copy(alpha = 0.65f))
            )
        }

        // Tree trunk: warm rounded wood, no harsh outline
        Box(
            modifier = Modifier
                .offset(y = -(height * 0.05f))
                .width(height * 0.16f)
                .height(height * 0.35f)
                .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp, bottomStart = 8.dp, bottomEnd = 8.dp))
                .background(Color(0xFF795548))
        )
    }
}

/**
 * Placeholder fantasy chest (Closed, Ready, Opened states).
 */
enum class ChestState { CLOSED, READY, OPENED }

@Composable
fun ArtPlaceholderChest(
    state: ChestState = ChestState.READY,
    modifier: Modifier = Modifier,
    size: Dp = 56.dp
) {
    val goldColor = MaterialTheme.grammarGamesColors.xp
    val woodColor = Color(0xFF8D6E63)
    val woodDark = Color(0xFF6D4C41)

    Box(
        modifier = modifier
            .size(size)
            .clip(AppShapes.cardBase)
            .background(
                Brush.verticalGradient(
                    if (state == ChestState.OPENED) listOf(Color(0xFFFFF9C4), Color(0xFFFFE082))
                    else listOf(woodColor, woodDark)
                )
            )
            .border(
                width = 2.dp,
                color = if (state == ChestState.READY) goldColor else Color(0xFFBCAAA4),
                shape = AppShapes.cardBase
            ),
        contentAlignment = Alignment.Center
    ) {
        if (state == ChestState.OPENED) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = goldColor,
                    modifier = Modifier.size(size * 0.5f)
                )
                Text(
                    text = "CLAIMED",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF5D4037)
                )
            }
        } else {
            // Chest lock & strap
            Box(
                modifier = Modifier
                    .size(size * 0.35f)
                    .clip(CircleShape)
                    .background(goldColor),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(size * 0.14f)
                        .clip(CircleShape)
                        .background(Color(0xFF3E2723))
                )
            }
        }
    }
}

/**
 * Placeholder Lesson Node adhering to world-integrated 2D geometry.
 */
enum class NodeVisualState { LOCKED, AVAILABLE, CURRENT, COMPLETED }

@Composable
fun ArtPlaceholderLessonNode(
    number: Int,
    state: NodeVisualState,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val colors = MaterialTheme.grammarGamesColors

    val (bgColor, borderColor, iconColor) = when (state) {
        NodeVisualState.LOCKED -> Triple(colors.lockedContainer, colors.locked, colors.locked)
        NodeVisualState.AVAILABLE -> Triple(colors.secondaryActionContainer, colors.secondaryAction, colors.secondaryAction)
        NodeVisualState.CURRENT -> Triple(colors.primaryAction, Color.White, Color.White)
        NodeVisualState.COMPLETED -> Triple(colors.success, Color.White, Color.White)
    }

    Box(
        modifier = modifier
            .size(if (state == NodeVisualState.CURRENT) 64.dp else 56.dp)
            .clip(CircleShape)
            .background(bgColor)
            .border(
                width = if (state == NodeVisualState.CURRENT) 3.5.dp else 2.5.dp,
                color = if (state == NodeVisualState.CURRENT) colors.warning else borderColor,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        when (state) {
            NodeVisualState.LOCKED -> {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Locked",
                    tint = iconColor,
                    modifier = Modifier.size(24.dp)
                )
            }
            NodeVisualState.COMPLETED -> {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Completed",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }
            NodeVisualState.CURRENT,
            NodeVisualState.AVAILABLE -> {
                Text(
                    text = "$number",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = iconColor
                )
            }
        }
    }
}

/**
 * Strict silhouette placeholder for the companion character.
 * Per ART_DIRECTION.md Rule 92, NO final mascot anatomy is invented prior to Character Bible.
 */
@Composable
fun ArtPlaceholderCharacterSilhouette(
    modifier: Modifier = Modifier,
    height: Dp = 110.dp
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Friendly rounded head silhouette with small ear/horn nubs
        Box(
            modifier = Modifier
                .size(height * 0.45f)
                .clip(CircleShape)
                .background(Color(0xFF27AE60).copy(alpha = 0.5f))
                .border(2.dp, Color(0xFF27AE60), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "HERO",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.height(2.dp))
        // Rounded chunky torso silhouette
        Box(
            modifier = Modifier
                .width(height * 0.5f)
                .height(height * 0.5f)
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 20.dp, bottomEnd = 20.dp))
                .background(Color(0xFF27AE60).copy(alpha = 0.4f))
                .border(
                    2.dp,
                    Color(0xFF27AE60),
                    RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 20.dp, bottomEnd = 20.dp)
                )
        )
    }
}
