package com.phalu.mytaskplanner.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.phalu.mytaskplanner.data.local.entities.Category

@Composable
fun CategorySelector(
    categories: List<Category>,
    selectedCategoryId: Int?,
    onCategorySelected: (Category) -> Unit,
    onAddCategory: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp), // Center TextFields in the middle of the screen
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(horizontal = 16.dp)
        ) {
            Label(
                text = "Category",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )
            Icon(
                imageVector = Icons.Filled.Edit, // Use the standard icon here
                contentDescription = "category",
                tint = Color.White,
                modifier = Modifier
                    .clickable {
                        onAddCategory()
                    }
                    .align(Alignment.CenterVertically)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(scrollState)
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            categories.forEach { category ->
                CategoryItem(
                    category = category,
                    isSelected = category.id == selectedCategoryId,
                    onSelectCategory = onCategorySelected
                )
            }
        }
    }
}