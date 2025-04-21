package com.phalu.mytaskplanner.ui.screens.addtask

import android.app.DatePickerDialog
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.phalu.mytaskplanner.ui.components.AppTopBar
import com.phalu.mytaskplanner.ui.components.CategorySelector
import com.phalu.mytaskplanner.ui.components.PrimaryButton
import com.phalu.mytaskplanner.ui.components.TextFieldTransparent
import com.phalu.mytaskplanner.R
import com.phalu.mytaskplanner.domain.usecase.Priority
import com.phalu.mytaskplanner.ui.ColorSaver
import com.phalu.mytaskplanner.ui.hexToColor
import com.phalu.mytaskplanner.ui.screens.addtask.category.CreateCategoryBottomSheet
import com.phalu.mytaskplanner.viewmodel.ManageTaskViewModel
import java.time.LocalDate
import java.util.Calendar
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(
    viewModel: ManageTaskViewModel,
    navController: NavController,
    taskId: Int? = null
) {
    val categories by viewModel.categories.collectAsState()

    val taskTitle by viewModel.taskTitle.collectAsState()
    val taskDescription by viewModel.taskDescription.collectAsState()
    val taskCategoryId by viewModel.taskCategoryId.collectAsState()
    val isValidTask = taskTitle.isNotEmpty()
    var expanded by remember { mutableStateOf(false) }

    var backgroundColor by rememberSaveable(stateSaver = ColorSaver) { mutableStateOf(Color.Transparent) }
    val defaultColor = MaterialTheme.colorScheme.background
    val containerColor by animateColorAsState(
        targetValue = if (backgroundColor == Color.Transparent) {
            defaultColor
        } else {
            backgroundColor
        },
        label = "containerColor"
    )

    val bottomSheetState = rememberModalBottomSheetState()
    val openBottomSheet = rememberSaveable { mutableStateOf(false) }

    val isEdit: Boolean = taskId !== null

    val taskPriority by viewModel.taskPriority.collectAsState()
    val taskDueDate by viewModel.taskDueDate.collectAsState()

// DatePicker
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, year, month, day ->
                val selectedDate = LocalDate.of(year, month + 1, day)
                viewModel.setTaskDueDate(selectedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    LaunchedEffect(taskId) {
        taskId?.let { id ->
            viewModel.findTaskById(id)
        }
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = stringResource(id = R.string.add_task),
                onBackClick = { navController.navigateUp() },
                backgroundColor = containerColor
            )
        },
        containerColor = containerColor,
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 16.dp)
    ) { innerPadding ->

        if (openBottomSheet.value) {
            ModalBottomSheet(
                onDismissRequest = {
                    if (taskCategoryId == null) {
                        backgroundColor = defaultColor
                    }
                    openBottomSheet.value = false
                },
                sheetState = bottomSheetState,
                containerColor = containerColor
            ) {
                CreateCategoryBottomSheet(
                    currentColor = backgroundColor,
                    onColorChange = {
                        backgroundColor = it
                    },
                    onSaveCategory = { category ->
                        hexToColor(
                            hex = category.color
                        )?.let { updatedColor ->
                            backgroundColor = updatedColor
                        }

                        openBottomSheet.value = false
                        viewModel.addCategory(
                            category = category,
                            onAdded = {
                                viewModel.setTaskCategoryId(it)
                            }
                        )
                    }
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween, // This ensures the last part sticks to the bottom
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextFieldTransparent(
                    textValue = taskTitle,
                    onTextChanged = viewModel::setTaskTitle,
                    imeAction = ImeAction.Next,
                    label = stringResource(id = R.string.title)
                )
                TextFieldTransparent(
                    textValue = taskDescription,
                    onTextChanged = viewModel::setTaskDescription,
                    label = stringResource(id = R.string.description)
                )

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primary)
                        .zIndex(2f)
                ) {
                    TextField(
                        readOnly = true,
                        value = taskPriority.name,
                        onValueChange = {},
                        label = { Text("Priority") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.primary)
                    ) {
                        Priority.values().forEach { priority ->
                            DropdownMenuItem(
                                text = { Text(priority.name.capitalize(Locale.ROOT),color = MaterialTheme.colorScheme.onSurface) },
                                onClick = {
                                    viewModel.setTaskPriority(priority)
                                    expanded = false
                                }
                            )
                        }
                    }

                }

                OutlinedButton(onClick = { datePickerDialog.show() }) {
                    Text(taskDueDate?.toString() ?: "Select Due Date",
                        color = MaterialTheme.colorScheme.onError
                    )
                }

                CategorySelector(
                    categories = categories,
                    selectedCategoryId = taskCategoryId,
                    onCategorySelected = { category ->
                        viewModel.setTaskCategoryId(category.id)
                        hexToColor(hex = category.color)?.let { updatedColor ->
                            backgroundColor = updatedColor
                        }
                    },
                    onAddCategory = {
                        openBottomSheet.value = true
                    })

                if (isEdit) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        OutlinedButton(
                            onClick = {
                                viewModel.markTaskComplete(taskId!!)
                                navController.navigateUp()
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.1f), // Light red bg
                                contentColor = MaterialTheme.colorScheme.onPrimary // Red text
                            ),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.onPrimary)
                        ) {
                            Text(
                                stringResource(id = R.string.mark_complete),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }

                        OutlinedButton(
                            onClick = {
                                viewModel.deleteTask(taskId!!)
                                navController.navigateUp()
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.1f), // Light red bg
                                contentColor = MaterialTheme.colorScheme.onPrimary // Red text
                            ),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.onPrimary)
                        ) {
                            Text(
                                stringResource(id = R.string.delete_task),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                }


                PrimaryButton(
                    label = if (isEdit) stringResource(id = R.string.update_task) else stringResource(id = R.string.add_task),
                    enabled = isValidTask,
                    modifier = Modifier.fillMaxSize().padding(vertical = 16.dp),
                    onClick = {
                        viewModel.handleSaveTask(taskId, isEdit)
                        navController.navigateUp()
                    }
                )

            }
        }

    }
}