package com.phalu.mytaskplanner.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.phalu.mytaskplanner.R

@Composable
fun CategoryColorSelector(
    colors: List<Color> = listOf(
        Color(0xFFD4E157), // Lime Green
        Color(0xFF7CB342), // Green
        Color(0xFF26A69A), // Teal
        Color(0xFF42A5F5), // Blue
        Color(0xFF29B6F6), // Light Blue
        Color(0xFFFFA726), // Orange
        Color(0xFFAB47BC), // Purple
        Color(0xFFE57373),
        Color(0xFFD4E157), // Lime Green
        Color(0xFF7CB342), // Green
        Color(0xFF26A69A), // Teal
        Color(0xFF42A5F5), // Blue
        Color(0xFF29B6F6), // Light Blue
        Color(0xFFFFA726), // Orange
        Color(0xFFAB47BC), // Purple
        Color(0xFFE57373) // Red
    ),
    onColorSelected: (Color) -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Top, // Center TextFields in the middle of the screen
        horizontalAlignment = Alignment.Start
    ) {
        Label(
            text = stringResource(id = R.string.category_color),
            modifier = Modifier.padding(bottom = 16.dp, start = 16.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(scrollState)
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            colors.forEach { color ->
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.onPrimary,
                            RoundedCornerShape(40.dp)
                        )
                        .background(color = color, shape = CircleShape)
                        .clip(CircleShape)
                        .clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = null,  // This disables the default ripple effect
                            onClick = { onColorSelected(color) }
                        )
                )
            }
        }
    }
}
