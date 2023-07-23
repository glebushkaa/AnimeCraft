@file:Suppress("FunctionName")

package com.anime.animecraft.core.theme.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.window.DialogProperties
import androidx.core.view.WindowCompat

val LocalColorPalette = staticCompositionLocalOf { LightColorScheme }
val LocalTypography = staticCompositionLocalOf { AppTypography() }
val LocalShapes = staticCompositionLocalOf { Shape() }
val LocalOffsets = staticCompositionLocalOf { Offset() }
val LocalStrokes = staticCompositionLocalOf { Stroke() }
val LocalElevation = staticCompositionLocalOf { Elevation() }

@Composable
fun AnimeCraftTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            window.navigationBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    CompositionLocalProvider(
        LocalTypography provides AppTypography(),
        LocalColorPalette provides colorScheme,
        content = content
    )
}

object AppTheme {
    val typography: AppTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current

    val colors: ColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalColorPalette.current

    val shapes: Shape
        @Composable
        @ReadOnlyComposable
        get() = LocalShapes.current

    val offsets: Offset
        @Composable
        @ReadOnlyComposable
        get() = LocalOffsets.current

    val strokes: Stroke
        @Composable
        @ReadOnlyComposable
        get() = LocalStrokes.current

    val elevations: Elevation
        @Composable
        @ReadOnlyComposable
        get() = LocalElevation.current

    val dialogProperties = DialogProperties(
        dismissOnBackPress = true,
        dismissOnClickOutside = true,
        usePlatformDefaultWidth = false
    )

    val grayscaleFilter = ColorFilter.colorMatrix(
        colorMatrix = ColorMatrix().apply { setToSaturation(0f) }
    )
}
