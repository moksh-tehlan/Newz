package com.moksh.newz.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val lightTheme = lightColors(
    primary = deepPurple,
    onPrimary = Color.White,
    primaryVariant = deepPurple,
    surface = listBackgroundColor,
    onSurface = titleColor,
    secondary = sourceTextColor,
    onSecondary = Color.White,
//    background = listBackgroundColor,
    background = Color.White,
    onBackground = titleColor,
    secondaryVariant = deepPurple,
    error = Color.Black,
    onError = Color.White
)

private val darkTheme = darkColors(
    primary = darkColor,
    onPrimary = Color.White,
    primaryVariant = darkColor,
    surface = listBackgroundColorDark,
    onSurface = titleColorDark,
    secondary = sourceTextColorDark,
    onSecondary = Color.White,
    background = listBackgroundColorDark,
    onBackground = titleColorDark,
    secondaryVariant = darkColor,
    error = Color.White,
    onError = Color.Black
)

@Composable
fun NewzTheme(isDarkTheme: Boolean, content: @Composable () -> Unit) {
    val colors = if (isDarkTheme) {
        darkTheme
    } else {
        lightTheme
    }

    MaterialTheme(
        colors = colors,
        content = content
    )
}