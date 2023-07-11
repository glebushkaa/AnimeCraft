@file:Suppress("MagicNumber")

package ua.animecraft.core.theme.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// TODO Change way to define color scheme and colors

private val Purple100 = Color(0xFFB98BF7)
private val Purple7 = Color(0x12B98BF7)
private val Purple50 = Color(0x80B98BF7)
private val Black1E = Color(0xFF1E1E1E)
private val Black31 = Color(0xFF312F34)
private val Black12 = Color(0xFF121212)
private val Gray50 = Color(0x80121212)
private val Gray3 = Color(0x12121212)

val DarkColorScheme = darkColorScheme(
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

val LightColorScheme = lightColorScheme(
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
