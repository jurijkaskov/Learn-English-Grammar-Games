package com.learnenglish.grammargames.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// ============================================================================
// Raw Brand Palette (Learn English: Grammar Games)
// ============================================================================

// Primary Purple / Violet
val PurplePrimary = Color(0xFF7357E8)
val PurpleDark = Color(0xFF5840C8)
val PurpleLight = Color(0xFFEEEAFE)
val PurpleDarkSurfaceAccent = Color(0xFF8D77F3)
val PurpleDarkContainer = Color(0xFF352A68)

// Secondary Blue
val BlueSecondary = Color(0xFF55A8F5)
val BlueDark = Color(0xFF2C86DC)
val BlueLight = Color(0xFFEAF5FF)
val BlueDarkContainer = Color(0xFF1E3F66)

// Success Green
val GreenSuccess = Color(0xFF45C67A)
val GreenDark = Color(0xFF2E9E5B)
val GreenLight = Color(0xFFE8F8EE)
val GreenDarkContainer = Color(0xFF1B4D30)

// Warning Orange
val OrangeWarning = Color(0xFFF5A742)
val OrangeDark = Color(0xFFD98622)
val OrangeLight = Color(0xFFFFF4E3)
val OrangeDarkContainer = Color(0xFF543913)

// Error Red
val RedError = Color(0xFFE85A62)
val RedDark = Color(0xFFC43840)
val RedLight = Color(0xFFFDEBED)
val RedDarkContainer = Color(0xFF562125)

// XP / Reward Gold
val GoldXp = Color(0xFFF6C453)
val GoldDark = Color(0xFFDCA42C)
val GoldLight = Color(0xFFFFF7DB)
val GoldDarkContainer = Color(0xFF544111)

// Neutral Light Surfaces (Soft Lavender-Gray Tint)
val BackgroundLight = Color(0xFFF7F8FC)
val SurfaceLight = Color(0xFFFFFFFF)
val SurfaceVariantLight = Color(0xFFF1F2F7)
val OutlineLight = Color(0xFFE2E3EA)
val OutlineVariantLight = Color(0xFFECEEF4)

// Neutral Text Light
val TextPrimaryLight = Color(0xFF25243A)
val TextSecondaryLight = Color(0xFF6F7185)
val TextDisabledLight = Color(0xFFA9ABBA)

// Neutral Dark Surfaces (Deep Navy / Purple Tints)
val BackgroundDark = Color(0xFF131226)
val SurfaceDark = Color(0xFF1C1A36)
val SurfaceVariantDark = Color(0xFF262447)
val OutlineDark = Color(0xFF363456)
val OutlineVariantDark = Color(0xFF2A2845)

// Neutral Text Dark
val TextPrimaryDark = Color(0xFFF4F4F8)
val TextSecondaryDark = Color(0xFFA5A7BC)
val TextDisabledDark = Color(0xFF64667A)

// Inactive / Locked
val LockedLight = Color(0xFF9E9EB2)
val LockedContainerLight = Color(0xFFEAEAF0)
val LockedDark = Color(0xFF707085)
val LockedContainerDark = Color(0xFF2B2A3D)

// Pure Base
val White = Color(0xFFFFFFFF)
val Black = Color(0xFF000000)

// ============================================================================
// Extended Semantic Color Scheme for Gamified Learning
// ============================================================================

@Immutable
data class GrammarGamesColors(
    val primaryAction: Color,
    val onPrimaryAction: Color,
    val primaryActionContainer: Color,
    val onPrimaryActionContainer: Color,

    val secondaryAction: Color,
    val onSecondaryAction: Color,
    val secondaryActionContainer: Color,
    val onSecondaryActionContainer: Color,

    val success: Color,
    val onSuccess: Color,
    val successContainer: Color,
    val onSuccessContainer: Color,

    val warning: Color,
    val onWarning: Color,
    val warningContainer: Color,
    val onWarningContainer: Color,

    val error: Color,
    val onError: Color,
    val errorContainer: Color,
    val onErrorContainer: Color,

    val xp: Color,
    val onXp: Color,
    val xpContainer: Color,
    val onXpContainer: Color,

    val locked: Color,
    val onLocked: Color,
    val lockedContainer: Color,
    val onLockedContainer: Color,

    val selected: Color,
    val onSelected: Color,
    val selectedContainer: Color,
    val onSelectedContainer: Color,

    val completed: Color,
    val onCompleted: Color,
    val completedContainer: Color,
    val onCompletedContainer: Color,

    val surfaceGame: Color,
    val onSurfaceGame: Color,
    val surfaceLesson: Color,
    val onSurfaceLesson: Color,

    val shadowColor: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val textDisabled: Color
)

val LightGrammarGamesColors = GrammarGamesColors(
    primaryAction = PurplePrimary,
    onPrimaryAction = White,
    primaryActionContainer = PurpleLight,
    onPrimaryActionContainer = PurpleDark,

    secondaryAction = BlueSecondary,
    onSecondaryAction = White,
    secondaryActionContainer = BlueLight,
    onSecondaryActionContainer = BlueDark,

    success = GreenSuccess,
    onSuccess = White,
    successContainer = GreenLight,
    onSuccessContainer = GreenDark,

    warning = OrangeWarning,
    onWarning = White,
    warningContainer = OrangeLight,
    onWarningContainer = OrangeDark,

    error = RedError,
    onError = White,
    errorContainer = RedLight,
    onErrorContainer = RedDark,

    xp = GoldXp,
    onXp = TextPrimaryLight,
    xpContainer = GoldLight,
    onXpContainer = GoldDark,

    locked = LockedLight,
    onLocked = TextSecondaryLight,
    lockedContainer = LockedContainerLight,
    onLockedContainer = LockedLight,

    selected = PurplePrimary,
    onSelected = White,
    selectedContainer = PurpleLight,
    onSelectedContainer = PurpleDark,

    completed = GreenSuccess,
    onCompleted = White,
    completedContainer = GreenLight,
    onCompletedContainer = GreenDark,

    surfaceGame = SurfaceLight,
    onSurfaceGame = TextPrimaryLight,
    surfaceLesson = SurfaceLight,
    onSurfaceLesson = TextPrimaryLight,

    shadowColor = Color(0x1A25243A),
    textPrimary = TextPrimaryLight,
    textSecondary = TextSecondaryLight,
    textDisabled = TextDisabledLight
)

val DarkGrammarGamesColors = GrammarGamesColors(
    primaryAction = PurpleDarkSurfaceAccent,
    onPrimaryAction = White,
    primaryActionContainer = PurpleDarkContainer,
    onPrimaryActionContainer = PurpleLight,

    secondaryAction = BlueSecondary,
    onSecondaryAction = White,
    secondaryActionContainer = BlueDarkContainer,
    onSecondaryActionContainer = BlueLight,

    success = GreenSuccess,
    onSuccess = White,
    successContainer = GreenDarkContainer,
    onSuccessContainer = GreenLight,

    warning = OrangeWarning,
    onWarning = White,
    warningContainer = OrangeDarkContainer,
    onWarningContainer = OrangeLight,

    error = RedError,
    onError = White,
    errorContainer = RedDarkContainer,
    onErrorContainer = RedLight,

    xp = GoldXp,
    onXp = Black,
    xpContainer = GoldDarkContainer,
    onXpContainer = GoldLight,

    locked = LockedDark,
    onLocked = TextSecondaryDark,
    lockedContainer = LockedContainerDark,
    onLockedContainer = LockedDark,

    selected = PurpleDarkSurfaceAccent,
    onSelected = White,
    selectedContainer = PurpleDarkContainer,
    onSelectedContainer = PurpleLight,

    completed = GreenSuccess,
    onCompleted = White,
    completedContainer = GreenDarkContainer,
    onCompletedContainer = GreenLight,

    surfaceGame = SurfaceDark,
    onSurfaceGame = TextPrimaryDark,
    surfaceLesson = SurfaceDark,
    onSurfaceLesson = TextPrimaryDark,

    shadowColor = Color(0x33000000),
    textPrimary = TextPrimaryDark,
    textSecondary = TextSecondaryDark,
    textDisabled = TextDisabledDark
)

val LocalGrammarGamesColors = staticCompositionLocalOf { LightGrammarGamesColors }
