package com.phalu.mytaskplanner.ui.screens.addtask.category

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.phalu.mytaskplanner.ui.components.CategoryColorSelector
import com.phalu.mytaskplanner.ui.components.PrimaryButton
import com.phalu.mytaskplanner.ui.components.TextFieldTransparent
import com.phalu.mytaskplanner.R
import com.phalu.mytaskplanner.data.local.entities.Category
import com.phalu.mytaskplanner.ui.ColorSaver
import com.phalu.mytaskplanner.ui.colorToHex

@Composable
fun CreateCategoryBottomSheet(
    currentColor: Color = MaterialTheme.colorScheme.background,
    onSaveCategory: (Category) -> Unit,
    onColorChange: (Color) -> Unit
) {
    var categoryName by rememberSaveable { mutableStateOf("") }
    var backgroundColor by rememberSaveable(stateSaver = ColorSaver) { mutableStateOf(currentColor) }

    val containerColor by animateColorAsState(
        targetValue = if (backgroundColor == Color.Transparent) {
            MaterialTheme.colorScheme.background
        } else {
            backgroundColor
        },
        label = "containerColor"
    )

    val isValidCategory = categoryName.isNotEmpty()

    Box(
        modifier = Modifier
            .background(containerColor)
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TextFieldTransparent(
                textValue = categoryName,
                onTextChanged = {
                    categoryName = it
                },
                label = stringResource(id = R.string.category_name)
            )
            CategoryColorSelector(
                onColorSelected = { selectedColor ->
                    backgroundColor = selectedColor
                    onColorChange(selectedColor)
                }
            )
            PrimaryButton(
                label = stringResource(id = R.string.save),
                enabled = isValidCategory,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            ) {
                onSaveCategory(
                    Category(
                        name = categoryName,
                        color = colorToHex(color = backgroundColor)
                    )
                )
            }
        }

    }
}