package com.learnenglish.grammargames.core.designsystem.theme

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

/**
 * Soft, cartoon-inspired rounded shapes for Learn English: Grammar Games.
 * Emphasizes organic, friendly curves with zero harsh sharp corners.
 */
val Shapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(20.dp),
    extraLarge = RoundedCornerShape(24.dp)
)

object AppShapes {
    val extraSmall = RoundedCornerShape(8.dp)
    val small = RoundedCornerShape(12.dp)
    val medium = RoundedCornerShape(16.dp)
    val large = RoundedCornerShape(20.dp)
    val extraLarge = RoundedCornerShape(24.dp)
    val jumbo = RoundedCornerShape(28.dp)
    val pill = RoundedCornerShape(999.dp)
    val circle = CircleShape

    // Semantic component mappings
    val buttonPrimary = RoundedCornerShape(18.dp)
    val buttonSecondary = RoundedCornerShape(16.dp)
    val buttonAnswer = RoundedCornerShape(16.dp)
    val cardBase = RoundedCornerShape(20.dp)
    val cardLearning = RoundedCornerShape(22.dp)
    val cardGame = RoundedCornerShape(24.dp)
    val cardEducational = RoundedCornerShape(20.dp)
    val panelGame = RoundedCornerShape(24.dp)
    val chip = RoundedCornerShape(999.dp)
    val badge = RoundedCornerShape(8.dp)
    val progressBar = RoundedCornerShape(999.dp)
    val bottomSheet = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
}
