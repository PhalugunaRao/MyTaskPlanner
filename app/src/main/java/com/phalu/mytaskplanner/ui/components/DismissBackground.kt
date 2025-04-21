package com.phalu.mytaskplanner.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp

@Composable
fun DismissBackground(dismissState: SwipeToDismissBoxState) {
    val color by animateColorAsState(
        targetValue = when (dismissState.dismissDirection) {
            SwipeToDismissBoxValue.EndToStart -> Color.Red
            else -> Color.Transparent
        },
        label = ""
    )

    Box(
        modifier = Modifier
            .testTag(BOX_DISMISS_TEST_TAG)
            .fillMaxSize()
            .background(color)
            .padding(16.dp)
    ) {
        Icon(
            Icons.Default.Delete,
            contentDescription = "delete",
            modifier = Modifier
                .testTag(ICON_DISMISS_TEST_TAG)
                .align(Alignment.CenterEnd)
        )
    }
}

const val BOX_DISMISS_TEST_TAG = "BOX_DISMISS_TEST_TAG"
const val ICON_DISMISS_TEST_TAG = "ICON_DISMISS_TEST_TAG"