package com.phalu.mytaskplanner.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.phalu.mytaskplanner.domain.usecase.SortOption
import com.phalu.mytaskplanner.domain.usecase.TaskStatusFilter

@Composable
fun FilterSortBar(
    selectedSortOption: SortOption,
    onSortOptionSelected: (SortOption) -> Unit,
    selectedStatusFilter: TaskStatusFilter,
    onStatusFilterSelected: (TaskStatusFilter) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        DropdownSelector(
            label = "Sort By",
            options = SortOption.values().toList(),
            selectedOption = selectedSortOption,
            onOptionSelected = onSortOptionSelected,
            optionLabel = { it.name.replace("_", " ").lowercase().replaceFirstChar(Char::uppercase) },
            modifier = Modifier.weight(1f)
        )
        DropdownSelector(
            label = "Status",
            options = TaskStatusFilter.values().toList(),
            selectedOption = selectedStatusFilter,
            onOptionSelected = onStatusFilterSelected,
            optionLabel = { it.name.lowercase().replaceFirstChar(Char::uppercase) },
            modifier = Modifier.weight(1f)
        )
    }
}
