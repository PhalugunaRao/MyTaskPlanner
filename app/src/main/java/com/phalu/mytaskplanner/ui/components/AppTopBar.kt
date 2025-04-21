package com.phalu.mytaskplanner.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.TopAppBarDefaults

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    onBackClick: (() -> Unit)? = null,
    navigationIcon: ImageVector? = null,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    textAndActionColor: Color = MaterialTheme.colorScheme.onPrimary,
    actions: @Composable RowScope.() -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            if (onBackClick != null) {
                IconButton(
                    onClick = onBackClick,
                ) {
                    Icon(
                        imageVector = navigationIcon ?: Icons.Default.ArrowBack,
                        contentDescription = "TopAppBar navigation icon",
                        tint = textAndActionColor
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = backgroundColor,
            titleContentColor = textAndActionColor,
            actionIconContentColor = textAndActionColor
        ),
        actions = actions
    )
}