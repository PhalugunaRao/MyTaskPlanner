package com.phalu.mytaskplanner.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phalu.mytaskplanner.data.repository.TaskRepository
import com.phalu.mytaskplanner.data.local.entities.Task
import com.phalu.mytaskplanner.data.local.entities.TaskWithCategory
import com.phalu.mytaskplanner.domain.usecase.SortOption
import com.phalu.mytaskplanner.domain.usecase.TaskStatusFilter
import com.phalu.mytaskplanner.viewmodel.generics.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class TaskListViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _sortOption = MutableStateFlow(SortOption.DUE_DATE)
    val sortOption = _sortOption.asStateFlow()

    private val _statusFilter = MutableStateFlow(TaskStatusFilter.ALL)
    val statusFilter = _statusFilter.asStateFlow()

    private val _tasksViewState =
        MutableStateFlow<ViewState<List<TaskWithCategory>>>(ViewState.Loading)
    open val tasksViewState: StateFlow<ViewState<List<TaskWithCategory>>> get() = _tasksViewState

    init {
        observeTasks()
    }

    private fun observeTasks() {
        viewModelScope.launch(ioDispatcher) {
            combine(
                taskRepository.allTasks,
                _sortOption,
                _statusFilter
            ) { tasks, sortOption, filter ->
                val filtered = tasks.filter {
                    when (filter) {
                        TaskStatusFilter.ALL -> true
                        TaskStatusFilter.COMPLETED -> it.task.isCompleted
                        TaskStatusFilter.PENDING -> !it.task.isCompleted
                    }
                }.sortedWith(
                    when (sortOption) {
                        SortOption.PRIORITY -> compareByDescending { it.task.priority }
                        SortOption.DUE_DATE -> compareBy { it.task.dueDate }
                        SortOption.TITLE -> compareBy { it.task.title.lowercase() }
                    }
                )
                ViewState.Data(filtered)
            }.catch {
                _tasksViewState.value = ViewState.Error
            }.collect { viewState ->
                _tasksViewState.value = viewState
            }
        }
    }

    fun setSortOption(option: SortOption) {
        _sortOption.value = option
    }

    fun setStatusFilter(filter: TaskStatusFilter) {
        _statusFilter.value = filter
    }

    open fun deleteTask(task: Task) {
        viewModelScope.launch(ioDispatcher) {
            try {
                taskRepository.delete(task)
            } catch (e: Exception) {
                _tasksViewState.value = ViewState.Error
            }
        }
    }
}
