package com.phalu.mytaskplanner.ui.screens.tasklist
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.phalu.mytaskplanner.ui.components.AppTopBar
import com.phalu.mytaskplanner.ui.components.ErrorScreen
import com.phalu.mytaskplanner.ui.components.FilterSortBar
import com.phalu.mytaskplanner.ui.components.LoadingIndicator
import com.phalu.mytaskplanner.ui.components.TaskItem
import com.phalu.mytaskplanner.R
import com.phalu.mytaskplanner.data.local.entities.Task
import com.phalu.mytaskplanner.data.local.entities.TaskWithCategory
import com.phalu.mytaskplanner.ui.components.EmptyTaskListUI
import com.phalu.mytaskplanner.ui.navigation.Screen
import com.phalu.mytaskplanner.viewmodel.TaskListViewModel
import com.phalu.mytaskplanner.viewmodel.generics.ViewState
import kotlinx.coroutines.launch

@Composable
fun TaskListScreen(
    viewModel: TaskListViewModel,
    navController: NavController
) {
    val taskViewState by viewModel.tasksViewState.collectAsState()
    val sortOption by viewModel.sortOption.collectAsState()
    val statusFilter by viewModel.statusFilter.collectAsState()

    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val snackBackBarMessage = stringResource(id = R.string.task_deleted)

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = {
            AppTopBar(
                title = stringResource(id = R.string.task_list),
                backgroundColor = MaterialTheme.colorScheme.onBackground
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.testTag(tag = ADD_TEST_TAG),
                onClick = {
                    navController.navigate(Screen.ADD_TASK.name)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.add_task)
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.onBackground
    ) { innerPadding ->

        when (taskViewState) {
            is ViewState.Data -> {
                val tasks = (taskViewState as ViewState.Data<List<TaskWithCategory>>).data

                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    if (tasks.isNotEmpty()) {
                        FilterSortBar(
                            selectedSortOption = sortOption,
                            onSortOptionSelected = { viewModel.setSortOption(it) },
                            selectedStatusFilter = statusFilter,
                            onStatusFilterSelected = { viewModel.setStatusFilter(it) }
                        )
                    }

                    TaskListContent(
                        tasks = (taskViewState as ViewState.Data<List<TaskWithCategory>>).data,
                        onTaskClick = { taskId ->
                            navController.navigate(Screen.EDIT_TASK.name + "/$taskId")
                        },
                        onDeleteTask = { task ->
                            viewModel.deleteTask(task)
                            scope.launch {
                                snackBarHostState.showSnackbar(message = snackBackBarMessage)
                            }
                        }
                    )
                }
            }

            ViewState.Error -> ErrorScreen()

            ViewState.Loading -> LoadingIndicator()
        }
    }
}

@Composable
fun TaskListContent(
    tasks: List<TaskWithCategory>,
    onTaskClick: (Int) -> Unit,
    onDeleteTask: (Task) -> Unit,
) {
    if (tasks.isEmpty()) {
        // Show Empty UI
        EmptyTaskListUI()
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(tasks) { task ->
                TaskItem(
                    task = task,
                    onTaskClick = { onTaskClick(task.task.id) },
                    onDeleteTask = { onDeleteTask(task.task) }
                )
            }
        }
    }
}

const val ADD_TEST_TAG = "ADD_TEST_TAG"
