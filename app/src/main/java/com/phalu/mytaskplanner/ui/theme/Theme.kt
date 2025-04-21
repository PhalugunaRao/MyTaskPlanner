package com.phalu.mytaskplanner.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = Purple40,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    background = Purple40,
    onBackground = onBackgroundDark, // Text on dark background
    onSurface = onSurfaceDark,    // Text on dark surfaces
    onPrimary = onPrimaryDark,          // Text on primary dark background
    onSecondary = onSecondaryDark
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    background = Purple40,
    onBackground = onBackground, // Text on dark background
    onSurface = onSurface,    // Text on dark surfaces
    onPrimary = onPrimary,          // Text on primary dark background
    onSecondary = onSecondary,


    /* Other default colors to override
background = Color(0xFFFFFBFE),
surface = Color(0xFFFFFBFE),
onPrimary = Color.White,
onSecondary = Color.White,
onTertiary = Color.White,
onBackground = Color(0xFF1C1B1F),
onSurface = Color(0xFF1C1B1F),
*/
)

@Composable
fun TaskManagerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}