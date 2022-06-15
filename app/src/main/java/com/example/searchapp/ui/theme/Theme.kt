package com.example.searchapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorPalette = lightColors(
    primary = Amber500,
    primaryVariant = Amber900,
    onPrimary = Color.White,
    secondary = Amber500,
    secondaryVariant = Amber900,
    onSecondary = Color.White,
    onError = Red800,
    onBackground = Color.Black
)

private val DarkColorPalette = darkColors(
    primary = Amber200,
    primaryVariant = Amber700,
    onPrimary = Color.Black,
    secondary = Amber200,
    onSecondary = Color.Black,
    onError = Red300,
    onBackground = Color.White
)

@Composable
fun SearchAppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    MaterialTheme(
        colors = if (darkTheme) DarkColorPalette else LightColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}