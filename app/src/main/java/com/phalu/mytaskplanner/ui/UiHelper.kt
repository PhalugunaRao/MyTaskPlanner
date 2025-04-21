package com.phalu.mytaskplanner.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

val ColorSaver: Saver<Color, Int> = Saver(
    save = { it.toArgb() },
    restore = { Color(it) }
)


fun convertStringToColor(color: String?): Color? {
    return Color(android.graphics.Color.parseColor("#$color"))
}

fun hexToColor(hex: String?): Color? {
    if (hex.isNullOrBlank()) {
        return null
    }

    val color = android.graphics.Color.parseColor(hex)
    return Color(
        red = (android.graphics.Color.red(color) / 255f),
        green = (android.graphics.Color.green(color) / 255f),
        blue = (android.graphics.Color.blue(color) / 255f),
        alpha = (android.graphics.Color.alpha(color) / 255f)
    )
}

fun colorToHex(color: Color): String {
    val red = (color.red * 255).toInt()
    val green = (color.green * 255).toInt()
    val blue = (color.blue * 255).toInt()
    val alpha = (color.alpha * 255).toInt()

    return String.format("#%02x%02x%02x%02x", alpha, red, green, blue)
}