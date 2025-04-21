package com.phalu.mytaskplanner.ui.components

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

@Composable
fun LoadingIndicator() {
    CircularProgressIndicator(
        modifier = Modifier.testTag(LOADING_INDICATOR_TEST_TAG)
    )
}

const val LOADING_INDICATOR_TEST_TAG = "LOADING_INDICATOR_TEST_TAG"