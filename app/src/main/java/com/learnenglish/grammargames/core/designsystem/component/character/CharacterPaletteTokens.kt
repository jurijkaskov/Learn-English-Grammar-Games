package com.learnenglish.grammargames.core.designsystem.component.character

import androidx.compose.ui.graphics.Color

/**
 * CharacterPaletteTokens: Authoritative color tokens for the main dragon mascot
 * as defined in CHARACTER_BIBLE.md Section 14 (Palette Sheet).
 *
 * Distinct from UI semantic colors (such as success green #00B894), preventing
 * the mascot from blending into feedback states.
 */
object CharacterPaletteTokens {
    // Body Anatomy
    val DragonBaseGreen = Color(0xFF58C96B)        // Fresh, friendly medium green
    val DragonShadowGreen = Color(0xFF3FAE55)      // Self-shading, cool tone
    val DragonHighlightGreen = Color(0xFF77DA83)    // Soft upper highlight
    val DragonBelly = Color(0xFFCFF1C5)            // Pale mint/warm green ventral area

    // Head & Facial Elements
    val Horns = Color(0xFFF5DDA6)                  // Warm cream/pale yellow
    val HornsShadow = Color(0xFFDEC388)            // Warm cream shadow
    val EyeWhite = Color(0xFFFDFCF7)               // Warm white sclera
    val EyeIrisDark = Color(0xFF4E342E)            // Deep warm brown outer iris
    val EyeIrisAmber = Color(0xFFD35400)           // Warm amber inner iris
    val EyePupil = Color(0xFF261815)               // Soft charcoal-black pupil
    val EyeHighlight = Color(0xFFFFFFFF)           // Specular highlight
    val MouthInterior = Color(0xFFC0392B)          // Warm friendly mouth tone
    val Teeth = Color(0xFFFFFDE7)                  // Soft cream rounded tooth

    // Wings & Dorsal Accents
    val WingMembrane = Color(0xFF4AB75E)           // Slightly deeper fresh green
    val DorsalScales = Color(0xFF77DA83)           // Soft mint dorsal bumps

    // Signature Accessories
    val BackpackPurple = Color(0xFF6C5CE7)         // Brand royal purple
    val BackpackPurpleDark = Color(0xFF5042BD)     // Backpack shadow/straps
    val BackpackBuckle = Color(0xFFF1C40F)         // Warm gold buckle
    val ScarfGold = Color(0xFFF1C40F)              // Warm golden-yellow scarf
    val ScarfGoldShadow = Color(0xFFD4AC0D)        // Scarf fold shadow

    // Ground Shadow
    val GroundShadow = Color(0xFF1E293B).copy(alpha = 0.18f)
}
