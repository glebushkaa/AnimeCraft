@file:Suppress("FunctionName")

package ua.anime.animecraft.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.window.DialogProperties
import androidx.core.view.WindowCompat
import ua.anime.animecraft.ui.theme.colors.Black12
import ua.anime.animecraft.ui.theme.colors.Black1E
import ua.anime.animecraft.ui.theme.colors.Black31
import ua.anime.animecraft.ui.theme.colors.Gray3
import ua.anime.animecraft.ui.theme.colors.Gray50
import ua.anime.animecraft.ui.theme.colors.Purple100
import ua.anime.animecraft.ui.theme.colors.Purple50
import ua.anime.animecraft.ui.theme.colors.Purple7
import ua.anime.animecraft.ui.theme.elevations.Elevation
import ua.anime.animecraft.ui.theme.offsets.Offset
import ua.anime.animecraft.ui.theme.shapes.Shape
import ua.anime.animecraft.ui.theme.sizes.Sizes
import ua.anime.animecraft.ui.theme.strokes.Stroke
import ua.anime.animecraft.ui.theme.typography.AppTypography

private val DarkColorScheme = darkColorScheme(
    primary = Purple100,
    secondary = Color.White,
    surface = Black31,
    onBackground = Color.White,
    tertiary = Purple50,
    background = Black1E,
    surfaceVariant = Purple7,
    onSurface = Black1E,
    onSurfaceVariant = Purple100
)

private val LightColorScheme = lightColorScheme(
    primary = Black12,
    secondary = Color.White,
    surface = Color.White,
    onBackground = Black12,
    tertiary = Gray50,
    background = Color.White,
    surfaceVariant = Gray3,
    onSurface = Color.White,
    onSurfaceVariant = Black12
)

val LocalColorPalette = staticCompositionLocalOf { LightColorScheme }
val LocalTypography = staticCompositionLocalOf { AppTypography() }
val LocalShapes = staticCompositionLocalOf { Shape() }
val LocalOffsets = staticCompositionLocalOf { Offset() }
val LocalSizes = staticCompositionLocalOf { Sizes() }
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

    val sizes: Sizes
        @Composable
        @ReadOnlyComposable
        get() = LocalSizes.current

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
