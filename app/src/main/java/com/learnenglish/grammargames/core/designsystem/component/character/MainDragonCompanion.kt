package com.learnenglish.grammargames.core.designsystem.component.character

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
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
import com.learnenglish.grammargames.core.designsystem.theme.AppSpacing

/**
 * MainDragonCompanion: Visual model and composable component for the main dragon mascot.
 *
 * Implements the locked anatomy, proportions, and palette defined in CHARACTER_BIBLE.md:
 * - 42% Head, 28% Torso, 18% Legs, 12% Feet (8-unit scale)
 * - Large expressive warm amber-brown eyes
 * - Two small cream horns curved backward
 * - Compact pear-shaped body with pale mint belly
 * - Small rounded green wings & smooth tapered tail
 * - Signature purple backpack and golden-yellow scarf
 * - Zero black outlines, soft shape shading
 */
@Composable
fun MainDragonCompanion(
    pose: CharacterPose = CharacterPose.IDLE,
    scale: CharacterScale = CharacterScale.MEDIUM,
    modifier: Modifier = Modifier,
    customHeight: Dp? = null
) {
    val height = customHeight ?: when (scale) {
        CharacterScale.SMALL -> 72.dp
        CharacterScale.MEDIUM -> 160.dp
        CharacterScale.LARGE -> 280.dp
    }

    val width = height * 0.72f // Proportional width footprint

    Box(
        modifier = modifier
            .width(width)
            .height(height)
            .testTag("main_dragon_${pose.name.lowercase()}"),
        contentAlignment = Alignment.BottomCenter
    ) {
        // 1. Soft Oval Ground Shadow
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .width(width * 0.75f)
                .height(height * 0.08f)
                .clip(CircleShape)
                .background(CharacterPaletteTokens.GroundShadow)
        )

        // 2. Character Body Stack (Tail -> Wings & Backpack -> Torso & Legs -> Head & Horns)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            // Upper Head assembly: Horns + Head + Eyes + Scarf
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(width = height * 0.46f, height = height * 0.42f)
            ) {
                // Horns (Two small cream horns curved backward)
                Row(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .offset(y = -(height * 0.04f))
                        .width(height * 0.32f),
                    horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
                ) {
                    // Left Horn
                    Box(
                        modifier = Modifier
                            .width(height * 0.08f)
                            .height(height * 0.12f)
                            .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 4.dp, bottomStart = 2.dp, bottomEnd = 2.dp))
                            .background(CharacterPaletteTokens.Horns)
                            .border(1.dp, CharacterPaletteTokens.HornsShadow, RoundedCornerShape(topStart = 8.dp, topEnd = 4.dp))
                    )
                    // Right Horn
                    Box(
                        modifier = Modifier
                            .width(height * 0.08f)
                            .height(height * 0.12f)
                            .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 8.dp, bottomStart = 2.dp, bottomEnd = 2.dp))
                            .background(CharacterPaletteTokens.Horns)
                            .border(1.dp, CharacterPaletteTokens.HornsShadow, RoundedCornerShape(topStart = 4.dp, topEnd = 8.dp))
                    )
                }

                // Main Head: Large rounded pear shape with soft green gradient
                Box(
                    modifier = Modifier
                        .size(height * 0.42f)
                        .clip(CircleShape)
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    CharacterPaletteTokens.DragonHighlightGreen,
                                    CharacterPaletteTokens.DragonBaseGreen,
                                    CharacterPaletteTokens.DragonShadowGreen
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    // Facial Elements (Eyes, Snout, Expression)
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.offset(y = height * 0.01f)
                    ) {
                        // Expressive Eyes (warm white sclera + warm amber iris + pupil + highlight)
                        Row(
                            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(height * 0.05f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            DragonEye(size = height * 0.10f, pose = pose)
                            DragonEye(size = height * 0.10f, pose = pose)
                        }

                        Spacer(modifier = Modifier.height(height * 0.015f))

                        // Snout & Mouth Area
                        Box(
                            modifier = Modifier
                                .width(height * 0.16f)
                                .height(height * 0.07f)
                                .clip(RoundedCornerShape(height * 0.035f))
                                .background(CharacterPaletteTokens.DragonHighlightGreen.copy(alpha = 0.4f)),
                            contentAlignment = Alignment.Center
                        ) {
                            // Friendly curved mouth / expression line
                            Box(
                                modifier = Modifier
                                    .width(height * 0.09f)
                                    .height(2.dp)
                                    .clip(RoundedCornerShape(1.dp))
                                    .background(CharacterPaletteTokens.EyeIrisDark)
                            )
                        }
                    }
                }
            }

            // Signature Golden-Yellow Scarf at Neck Anchor
            Box(
                modifier = Modifier
                    .offset(y = -(height * 0.02f))
                    .width(height * 0.32f)
                    .height(height * 0.05f)
                    .clip(RoundedCornerShape(height * 0.025f))
                    .background(CharacterPaletteTokens.ScarfGold)
                    .border(1.dp, CharacterPaletteTokens.ScarfGoldShadow, RoundedCornerShape(height * 0.025f))
            )

            // Middle assembly: Torso + Belly + Backpack (Back) + Arms
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .offset(y = -(height * 0.02f))
                    .width(height * 0.48f)
                    .height(height * 0.30f)
            ) {
                // Wings & Purple Backpack peeking from behind torso
                Row(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .width(height * 0.48f),
                    horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
                ) {
                    // Left Wing
                    Box(
                        modifier = Modifier
                            .size(height * 0.11f)
                            .clip(RoundedCornerShape(topStart = 16.dp, bottomStart = 8.dp))
                            .background(CharacterPaletteTokens.WingMembrane)
                    )
                    // Backpack Straps/Top
                    Box(
                        modifier = Modifier
                            .width(height * 0.16f)
                            .height(height * 0.12f)
                            .clip(RoundedCornerShape(4.dp))
                            .background(CharacterPaletteTokens.BackpackPurple)
                            .border(1.dp, CharacterPaletteTokens.BackpackPurpleDark, RoundedCornerShape(4.dp))
                    )
                    // Right Wing
                    Box(
                        modifier = Modifier
                            .size(height * 0.11f)
                            .clip(RoundedCornerShape(topEnd = 16.dp, bottomEnd = 8.dp))
                            .background(CharacterPaletteTokens.WingMembrane)
                    )
                }

                // Compact Pear Torso with Light Mint Belly
                Box(
                    modifier = Modifier
                        .width(height * 0.34f)
                        .height(height * 0.28f)
                        .clip(RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp, bottomStart = 14.dp, bottomEnd = 14.dp))
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    CharacterPaletteTokens.DragonBaseGreen,
                                    CharacterPaletteTokens.DragonShadowGreen
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    // Light Mint Belly Oval
                    Box(
                        modifier = Modifier
                            .width(height * 0.20f)
                            .height(height * 0.22f)
                            .clip(RoundedCornerShape(50))
                            .background(CharacterPaletteTokens.DragonBelly)
                    )

                    // Arms according to pose
                    DragonArms(height = height, pose = pose)
                }
            }

            // Lower Assembly: Short strong legs and oversized feet with 3 toes
            Row(
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(height * 0.04f),
                modifier = Modifier.offset(y = -(height * 0.02f))
            ) {
                // Left Foot (3 simplified toes)
                Box(
                    modifier = Modifier
                        .width(height * 0.14f)
                        .height(height * 0.10f)
                        .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp, bottomStart = 6.dp, bottomEnd = 6.dp))
                        .background(CharacterPaletteTokens.DragonShadowGreen)
                )
                // Right Foot (3 simplified toes)
                Box(
                    modifier = Modifier
                        .width(height * 0.14f)
                        .height(height * 0.10f)
                        .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp, bottomStart = 6.dp, bottomEnd = 6.dp))
                        .background(CharacterPaletteTokens.DragonShadowGreen)
                )
            }
        }

        // 3. Smooth Curved Tapered Tail (extending from behind)
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = -(width * 0.05f), y = -(height * 0.08f))
                .width(height * 0.20f)
                .height(height * 0.10f)
                .clip(RoundedCornerShape(topEnd = 24.dp, bottomEnd = 12.dp))
                .background(CharacterPaletteTokens.DragonShadowGreen)
        )
    }
}

/**
 * DragonEye: Renders warm amber eye with highlight and expression shifts.
 */
@Composable
private fun DragonEye(size: Dp, pose: CharacterPose) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(CharacterPaletteTokens.EyeWhite),
        contentAlignment = Alignment.Center
    ) {
        // Iris: Deep warm brown outer with warm amber center
        Box(
            modifier = Modifier
                .size(size * 0.72f)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        listOf(
                            CharacterPaletteTokens.EyeIrisAmber,
                            CharacterPaletteTokens.EyeIrisDark
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            // Pupil (soft circular, not snake slit)
            Box(
                modifier = Modifier
                    .size(size * 0.38f)
                    .clip(CircleShape)
                    .background(CharacterPaletteTokens.EyePupil)
            )

            // Upper-left Specular Highlight
            Box(
                modifier = Modifier
                    .offset(x = -(size * 0.12f), y = -(size * 0.12f))
                    .size(size * 0.20f)
                    .clip(CircleShape)
                    .background(CharacterPaletteTokens.EyeHighlight)
            )
        }
    }
}

/**
 * DragonArms: Positional gestures (Idle, Thinking, Pointing, Celebrating, Reading).
 */
@Composable
private fun DragonArms(height: Dp, pose: CharacterPose) {
    when (pose) {
        CharacterPose.CELEBRATING, CharacterPose.VERY_HAPPY -> {
            // Arms raised high
            Row(
                modifier = Modifier
                    .width(height * 0.38f)
                    .offset(y = -(height * 0.06f)),
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .width(height * 0.07f)
                        .height(height * 0.12f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(CharacterPaletteTokens.DragonBaseGreen)
                )
                Box(
                    modifier = Modifier
                        .width(height * 0.07f)
                        .height(height * 0.12f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(CharacterPaletteTokens.DragonBaseGreen)
                )
            }
        }
        CharacterPose.THINKING -> {
            // One arm touching chin
            Box(
                modifier = Modifier
                    .offset(x = height * 0.06f, y = -(height * 0.04f))
                    .width(height * 0.07f)
                    .height(height * 0.12f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(CharacterPaletteTokens.DragonBaseGreen)
            )
        }
        CharacterPose.READING -> {
            // Holding purple grammar book
            Box(
                modifier = Modifier
                    .offset(y = height * 0.02f)
                    .width(height * 0.18f)
                    .height(height * 0.12f)
                    .clip(RoundedCornerShape(4.dp))
                    .background(CharacterPaletteTokens.BackpackPurple)
                    .border(1.dp, Color.White.copy(alpha = 0.4f), RoundedCornerShape(4.dp)),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .width(height * 0.14f)
                        .height(height * 0.09f)
                        .clip(RoundedCornerShape(2.dp))
                        .background(CharacterPaletteTokens.Horns)
                )
            }
        }
        else -> {
            // Default Idle resting arms
            Row(
                modifier = Modifier.width(height * 0.36f),
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .width(height * 0.06f)
                        .height(height * 0.12f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(CharacterPaletteTokens.DragonBaseGreen)
                )
                Box(
                    modifier = Modifier
                        .width(height * 0.06f)
                        .height(height * 0.12f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(CharacterPaletteTokens.DragonBaseGreen)
                )
            }
        }
    }
}
