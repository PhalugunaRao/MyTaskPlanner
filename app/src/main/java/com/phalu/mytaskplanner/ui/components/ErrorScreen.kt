package com.phalu.mytaskplanner.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.phalu.mytaskplanner.R

@Composable
fun ErrorScreen() {
    // Implement your error UI here
    Label(
        text = stringResource(id = R.string.generic_error),
        modifier = Modifier.testTag(ERROR_TEST_TAG)
    )
}

const val ERROR_TEST_TAG = "ERROR_TEST_TAG"