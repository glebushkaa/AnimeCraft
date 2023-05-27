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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

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

@Composable
fun AnimeCraftTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
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
}
