package com.learnenglish.grammargames.core.designsystem.component.asset

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learnenglish.grammargames.core.designsystem.component.character.CharacterPaletteTokens
import com.learnenglish.grammargames.core.designsystem.theme.AppDimensions
import com.learnenglish.grammargames.core.designsystem.theme.AppSpacing

/**
 * GraphicAssetResolver: Authoritative type-safe resolver and renderer for canonical
 * graphic assets defined in GRAPHIC_ASSETS.md.
 *
 * Implements clean fallback rendering complying strictly with ART_DIRECTION.md:
 * - 2D vector styling
 * - Soft upper-left lighting
 * - Zero black outlines
 * - Distinct reward gold and brand purple palettes
 */
object GraphicAssetResolver {

    /**
     * Renders a canonical treasure chest asset by [ChestState].
     */
    @Composable
    fun RenderChest(
        state: ChestState = ChestState.CLOSED,
        size: Dp = 72.dp,
        modifier: Modifier = Modifier
    ) {
        val chestWood = Color(0xFF8D6E63)
        val chestWoodDark = Color(0xFF5D4037)
        val chestGold = Color(0xFFF1C40F)
        val chestGoldDark = Color(0xFFD4AC0D)
        val chestGlow = Color(0xFFF39C12)

        Box(
            modifier = modifier
                .size(size)
                .testTag("asset_chest_${state.name.lowercase()}"),
            contentAlignment = Alignment.Center
        ) {
            // Ground shadow
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .width(size * 0.85f)
                    .height(size * 0.16f)
                    .clip(CircleShape)
                    .background(CharacterPaletteTokens.GroundShadow)
            )

            // Optional glow for READY / OPEN states
            if (state == ChestState.READY || state == ChestState.OPEN) {
                Box(
                    modifier = Modifier
                        .size(size * 0.95f)
                        .clip(CircleShape)
                        .background(chestGlow.copy(alpha = 0.25f))
                )
            }

            // Chest Base & Body
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.align(Alignment.Center)
            ) {
                // Lid Assembly
                val lidHeight = if (state == ChestState.OPEN || state == ChestState.CLAIMED) size * 0.22f else size * 0.28f
                val lidOffset = if (state == ChestState.OPEN || state == ChestState.CLAIMED) -(size * 0.08f) else 0.dp

                Box(
                    modifier = Modifier
                        .offset(y = lidOffset)
                        .width(size * 0.76f)
                        .height(lidHeight)
                        .clip(RoundedCornerShape(topStart = size * 0.2f, topEnd = size * 0.2f, bottomStart = 4.dp, bottomEnd = 4.dp))
                        .background(
                            Brush.verticalGradient(
                                listOf(Color(0xFFA1887F), chestWood, chestWoodDark)
                            )
                        )
                        .border(
                            width = 2.dp,
                            brush = Brush.horizontalGradient(listOf(chestGold, chestGoldDark, chestGold)),
                            shape = RoundedCornerShape(topStart = size * 0.2f, topEnd = size * 0.2f, bottomStart = 4.dp, bottomEnd = 4.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    // Central golden lock clasp
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .size(size * 0.14f)
                            .clip(RoundedCornerShape(3.dp))
                            .background(chestGold)
                            .border(1.dp, chestGoldDark, RoundedCornerShape(3.dp))
                    )
                }

                // Chest Body
                Box(
                    modifier = Modifier
                        .width(size * 0.72f)
                        .height(size * 0.38f)
                        .clip(RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp))
                        .background(
                            Brush.verticalGradient(
                                listOf(chestWood, chestWoodDark)
                            )
                        )
                        .border(
                            width = 2.dp,
                            color = chestGoldDark,
                            shape = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    // Vertical golden metal bands
                    Row(
                        modifier = Modifier.width(size * 0.58f),
                        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
                    ) {
                        Box(
                            modifier = Modifier
                                .width(size * 0.08f)
                                .height(size * 0.38f)
                                .background(chestGold)
                        )
                        Box(
                            modifier = Modifier
                                .width(size * 0.08f)
                                .height(size * 0.38f)
                                .background(chestGold)
                        )
                    }

                    // Open radiant light spill if open
                    if (state == ChestState.OPEN) {
                        Box(
                            modifier = Modifier
                                .size(size * 0.28f)
                                .clip(CircleShape)
                                .background(Color(0xFFFFF9C4).copy(alpha = 0.8f))
                        )
                    }
                }
            }
        }
    }

    /**
     * Renders a canonical XP Star or Golden Coin reward asset.
     */
    @Composable
    fun RenderRewardToken(
        type: RewardAssetType = RewardAssetType.XP_STAR,
        size: Dp = 48.dp,
        modifier: Modifier = Modifier
    ) {
        val goldMain = Color(0xFFF1C40F)
        val goldDark = Color(0xFFD4AC0D)
        val goldLight = Color(0xFFFFF176)

        Box(
            modifier = modifier
                .size(size)
                .testTag("asset_reward_${type.name.lowercase()}"),
            contentAlignment = Alignment.Center
        ) {
            when (type) {
                RewardAssetType.XP_STAR -> {
                    // Rounded radiant XP star
                    Box(
                        modifier = Modifier
                            .size(size * 0.88f)
                            .clip(CircleShape)
                            .background(
                                Brush.radialGradient(
                                    listOf(goldLight, goldMain, goldDark)
                                )
                            )
                            .border(2.dp, goldDark, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        // Star glint center
                        Text(
                            text = "★",
                            color = Color.White,
                            fontSize = (size.value * 0.48f).sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                }
                RewardAssetType.COIN_GOLD -> {
                    // Minted thick golden coin
                    Box(
                        modifier = Modifier
                            .size(size * 0.85f)
                            .clip(CircleShape)
                            .background(
                                Brush.verticalGradient(
                                    listOf(goldLight, goldMain, goldDark)
                                )
                            )
                            .border(2.5.dp, goldDark, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        // Inner ring
                        Box(
                            modifier = Modifier
                                .size(size * 0.62f)
                                .clip(CircleShape)
                                .border(1.dp, goldDark.copy(alpha = 0.6f), CircleShape)
                        )
                    }
                }
                else -> {
                    RenderChest(state = ChestState.CLOSED, size = size)
                }
            }
        }
    }

    /**
     * Renders a canonical Game Mode illustration tile for the Games Arena hub.
     */
    @Composable
    fun RenderGameArtTile(
        gameType: GameAssetType,
        size: Dp = 72.dp,
        modifier: Modifier = Modifier
    ) {
        val brandPurple = Color(0xFF6C5CE7)
        val brandPurpleLight = Color(0xFF8875FF)
        val goldAccent = Color(0xFFF1C40F)

        Box(
            modifier = modifier
                .size(size)
                .clip(RoundedCornerShape(16.dp))
                .background(
                    Brush.verticalGradient(
                        listOf(brandPurpleLight, brandPurple)
                    )
                )
                .border(2.dp, Color.White.copy(alpha = 0.25f), RoundedCornerShape(16.dp))
                .testTag("asset_game_${gameType.name.lowercase()}"),
            contentAlignment = Alignment.Center
        ) {
            when (gameType) {
                GameAssetType.CROSSWORD -> {
                    // 3 Interlocking clean tiles with golden pencil hint
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(size * 0.28f)
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(modifier = Modifier.size(size * 0.12f).background(brandPurple.copy(alpha = 0.3f)))
                        }
                        Box(
                            modifier = Modifier
                                .size(size * 0.28f)
                                .clip(RoundedCornerShape(4.dp))
                                .background(goldAccent),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(modifier = Modifier.size(size * 0.12f).background(Color.White))
                        }
                    }
                }
                GameAssetType.MEMORY -> {
                    // Two overlapping flipped cards
                    Box(contentAlignment = Alignment.Center) {
                        Box(
                            modifier = Modifier
                                .offset(x = -(size * 0.12f), y = (size * 0.04f))
                                .size(width = size * 0.32f, height = size * 0.44f)
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color.White.copy(alpha = 0.85f))
                        )
                        Box(
                            modifier = Modifier
                                .offset(x = (size * 0.12f), y = -(size * 0.04f))
                                .size(width = size * 0.32f, height = size * 0.44f)
                                .clip(RoundedCornerShape(6.dp))
                                .background(goldAccent)
                                .border(1.5.dp, Color.White, RoundedCornerShape(6.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("★", color = Color.White, fontSize = 12.sp)
                        }
                    }
                }
                GameAssetType.GRAMMAR_BATTLE -> {
                    // Magic book with radiant shield
                    Box(
                        modifier = Modifier
                            .size(size * 0.52f)
                            .clip(CircleShape)
                            .background(Color(0xFF00B894).copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(size * 0.40f)
                                .clip(RoundedCornerShape(4.dp))
                                .background(goldAccent),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("🛡", fontSize = 14.sp)
                        }
                    }
                }
                GameAssetType.SPEED_CHALLENGE -> {
                    // Stopwatch with motion streaks
                    Box(
                        modifier = Modifier
                            .size(size * 0.48f)
                            .clip(CircleShape)
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(size * 0.38f)
                                .clip(CircleShape)
                                .background(goldAccent)
                        )
                    }
                }
                GameAssetType.WORD_SEARCH, GameAssetType.SENTENCE_RACE -> {
                    // Circular magnifying glass / colorful blocks
                    Box(
                        modifier = Modifier
                            .size(size * 0.46f)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.9f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(size * 0.32f)
                                .clip(CircleShape)
                                .background(brandPurple)
                        )
                    }
                }
            }
        }
    }
}
