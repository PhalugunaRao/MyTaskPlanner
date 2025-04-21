package com.phalu.mytaskplanner.ui.components
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.phalu.mytaskplanner.data.local.entities.Category
import com.phalu.mytaskplanner.ui.hexToColor

@Composable
fun CategoryItem(
    category: Category,
    isSelected: Boolean,
    onSelectCategory: (Category) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Card(
            modifier = Modifier
                .clickable { onSelectCategory(category) }
                .align(Alignment.BottomEnd)
                .border(
                    if (isSelected) 3.dp else 1.dp,
                    MaterialTheme.colorScheme.onPrimary,
                    RoundedCornerShape(8.dp)
                ),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = hexToColor(category.color)!!
            )
        ) {
            Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Label(
                    text = category.name,
                    textStyle = if(isSelected) MaterialTheme.typography.bodyLarge else MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
