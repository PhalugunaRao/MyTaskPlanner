package com.phalu.mytaskplanner.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


@Composable
fun PrimaryButton(
    label: String,
    modifier: Modifier = Modifier
        .fillMaxWidth(),
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            enabled = enabled,
            colors = ButtonColors(
                containerColor = MaterialTheme.colorScheme.onPrimary,
                contentColor = MaterialTheme.colorScheme.primary,
                disabledContainerColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.12f),
                disabledContentColor = MaterialTheme.colorScheme.inversePrimary
            )
        ) {
            Text(label)
        }
    }
}