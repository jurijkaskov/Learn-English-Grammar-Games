package com.learnenglish.grammargames.core.designsystem.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = PurplePrimary,
    onPrimary = White,
    primaryContainer = PurpleLight,
    onPrimaryContainer = PurpleDark,
    secondary = BlueSecondary,
    onSecondary = White,
    secondaryContainer = BlueLight,
    onSecondaryContainer = BlueDark,
    tertiary = GreenSuccess,
    onTertiary = White,
    tertiaryContainer = GreenLight,
    onTertiaryContainer = GreenDark,
    background = BackgroundLight,
    onBackground = TextPrimaryLight,
    surface = SurfaceLight,
    onSurface = TextPrimaryLight,
    surfaceVariant = SurfaceVariantLight,
    onSurfaceVariant = TextSecondaryLight,
    outline = OutlineLight,
    outlineVariant = OutlineVariantLight,
    error = RedError,
    onError = White,
    errorContainer = RedLight,
    onErrorContainer = RedDark
)

private val DarkColorScheme = darkColorScheme(
    primary = PurpleDarkSurfaceAccent,
    onPrimary = White,
    primaryContainer = PurpleDarkContainer,
    onPrimaryContainer = PurpleLight,
    secondary = BlueSecondary,
    onSecondary = White,
    secondaryContainer = BlueDarkContainer,
    onSecondaryContainer = BlueLight,
    tertiary = GreenSuccess,
    onTertiary = White,
    tertiaryContainer = GreenDarkContainer,
    onTertiaryContainer = GreenLight,
    background = BackgroundDark,
    onBackground = TextPrimaryDark,
    surface = SurfaceDark,
    onSurface = TextPrimaryDark,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = TextSecondaryDark,
    outline = OutlineDark,
    outlineVariant = OutlineVariantDark,
    error = RedError,
    onError = White,
    errorContainer = RedDarkContainer,
    onErrorContainer = RedLight
)

val MaterialTheme.grammarGamesColors: GrammarGamesColors
    @Composable
    @ReadOnlyComposable
    get() = LocalGrammarGamesColors.current

@Composable
fun GrammarGamesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Disabled by default to preserve distinct educational purple brand identity
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val customColors = if (darkTheme) DarkGrammarGamesColors else LightGrammarGamesColors

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                window.statusBarColor = android.graphics.Color.TRANSPARENT
                window.navigationBarColor = android.graphics.Color.TRANSPARENT
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
                WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = !darkTheme
            }
        }
    }

    CompositionLocalProvider(
        LocalGrammarGamesColors provides customColors
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}
