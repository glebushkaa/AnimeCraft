@file:Suppress("MagicNumber")

package com.anime.animecraft.core.theme.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.anime.animecraft.core.theme.R

val openSansFontFamily = FontFamily(
    Font(R.font.open_sans_regular, weight = FontWeight.Normal),
    Font(R.font.open_sans_medium, weight = FontWeight.Medium),
    Font(R.font.open_sans_semi_bold, weight = FontWeight.SemiBold),
    Font(R.font.open_sans_bold, weight = FontWeight.Bold)
)

data class AppTypography(

    val displayLarge: TextStyle = TextStyle(
        fontFamily = openSansFontFamily,
        fontWeight = FontWeight.Black,
        fontSize = 64.sp,
        letterSpacing = (-0.25).sp
    ),

    val displayMedium: TextStyle = TextStyle(
        fontFamily = openSansFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 44.sp
    ),

    val displaySmall: TextStyle = TextStyle(
        fontFamily = openSansFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp,
        letterSpacing = 0.15.sp
    ),

    val headlineLarge: TextStyle = TextStyle(
        fontFamily = openSansFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 40.sp,
        letterSpacing = (-0.15).sp
    ),

    val headlineMedium: TextStyle = TextStyle(
        fontFamily = openSansFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 32.sp,
        letterSpacing = (-0.15).sp
    ),

    val headlineSmall: TextStyle = TextStyle(
        fontFamily = openSansFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp
    ),

    val titleLarge: TextStyle = TextStyle(
        fontFamily = openSansFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),

    val titleMedium: TextStyle = TextStyle(
        fontFamily = openSansFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        letterSpacing = 0.15.sp
    ),

    val titleMediumExtra: TextStyle = TextStyle(
        fontFamily = openSansFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        letterSpacing = 0.15.sp
    ),

    val titleSmall: TextStyle = TextStyle(
        fontFamily = openSansFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        letterSpacing = 0.1.sp
    ),

    val labelSelected: TextStyle = TextStyle(
        fontFamily = openSansFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        letterSpacing = 0.1.sp
    ),

    val labelNormal: TextStyle = TextStyle(
        fontFamily = openSansFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        letterSpacing = 0.5.sp
    ),

    val labelMedium: TextStyle = TextStyle(
        fontFamily = openSansFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        letterSpacing = 0.5.sp
    ),

    val labelSmall: TextStyle = TextStyle(
        fontFamily = openSansFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        letterSpacing = 0.5.sp
    ),

    val bodyLarge: TextStyle = TextStyle(
        fontFamily = openSansFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp
    ),

    val bodyLargeBold: TextStyle = TextStyle(
        fontFamily = openSansFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),

    val bodyMedium: TextStyle = TextStyle(
        fontFamily = openSansFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        letterSpacing = 0.25.sp
    ),

    val bodyMediumLink: TextStyle = TextStyle(
        fontFamily = openSansFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        letterSpacing = 0.25.sp,
        textDecoration = TextDecoration.Underline
    ),

    val bodySmall: TextStyle = TextStyle(
        fontFamily = openSansFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        letterSpacing = 0.4.sp
    ),

    val buttonCta: TextStyle = TextStyle(
        fontFamily = openSansFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        letterSpacing = 0.5.sp
    ),

    val buttonMenu: TextStyle = TextStyle(
        fontFamily = openSansFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        letterSpacing = 0.5.sp
    ),

    val downloadTextBold: TextStyle = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.W600,
        fontFamily = openSansFontFamily
    ),

    val downloadText: TextStyle = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.W300,
        fontFamily = openSansFontFamily
    ),

    val swipeUpText: TextStyle = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.W600,
        fontFamily = openSansFontFamily
    )
)
