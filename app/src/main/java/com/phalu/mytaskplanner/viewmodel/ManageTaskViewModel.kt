package com.phalu.mytaskplanner.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phalu.mytaskplanner.data.repository.CategoryRepository
import com.phalu.mytaskplanner.data.repository.TaskRepository
import com.phalu.mytaskplanner.data.local.entities.Category
import com.phalu.mytaskplanner.data.local.entities.TaskWithCategory
import com.phalu.mytaskplanner.domain.usecase.Priority
import com.phalu.mytaskplanner.domain.usecase.SaveTaskUseCase
import com.phalu.mytaskplanner.domain.usecase.SortOption
import com.phalu.mytaskplanner.domain.usecase.TaskStatusFilter
import com.phalu.mytaskplanner.viewmodel.generics.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ManageTaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val categoryRepository: CategoryRepository,
    private val saveTaskUseCase: SaveTaskUseCase,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _taskPriority = MutableStateFlow(Priority.MEDIUM)
    val taskPriority: StateFlow<Priority> get() = _taskPriority

    private val _taskDueDate = MutableStateFlow<LocalDate?>(null)
    val taskDueDate: StateFlow<LocalDate?> get() = _taskDueDate


    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> get() = _categories

    private val _taskTitle = MutableStateFlow("")
    val taskTitle: StateFlow<String> get() = _taskTitle

    private val _taskDescription = MutableStateFlow("")
    val taskDescription: StateFlow<String> get() = _taskDescription

    private val _taskCategoryId = MutableStateFlow<Int?>(null)
    val taskCategoryId: StateFlow<Int?> get() = _taskCategoryId

    private val _sortOption = MutableStateFlow(SortOption.DUE_DATE)
    val sortOption = _sortOption.asStateFlow()

    private val _statusFilter = MutableStateFlow(TaskStatusFilter.ALL)
    val statusFilter = _statusFilter.asStateFlow()

    init {
        fetchCategories()
    }

    private val _dueDate = MutableStateFlow<LocalDate?>(null)
    val dueDate: StateFlow<LocalDate?> get() = _dueDate


    fun setTaskPriority(priority: Priority) {
        _taskPriority.value = priority
    }

    fun setTaskDueDate(dueDate: LocalDate?) {
        _taskDueDate.value = dueDate
    }

    open fun fetchCategories() {
        viewModelScope.launch(ioDispatcher) {
            categoryRepository.allCategories.collect { allCategories ->
                _categories.value = allCategories
            }
        }
    }
    fun deleteTask(taskId: Int) {
        viewModelScope.launch {
            val taskWithCategory = taskRepository.queryTask(taskId)
            taskRepository.delete(taskWithCategory.task)
        }
    }

    fun markTaskComplete(taskId: Int) {
        viewModelScope.launch {
            val taskWithCategory = taskRepository.queryTask(taskId)
            val updatedTask = taskWithCategory.task.copy(isCompleted = true)
            taskRepository.update(updatedTask)
        }
    }



    fun setTaskTitle(title: String) {
        _taskTitle.value = title
    }

    fun setTaskDescription(description: String) {
        _taskDescription.value = description
    }

    fun setTaskCategoryId(categoryId: Int?) {
        _taskCategoryId.value = categoryId
    }



    val tasksViewState: StateFlow<ViewState<List<TaskWithCategory>>> = combine(
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
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), ViewState.Loading)

    fun setSortOption(option: SortOption) {
        _sortOption.value = option
    }

    fun setStatusFilter(filter: TaskStatusFilter) {
        _statusFilter.value = filter
    }

    fun findTaskById(taskId: Int) {
        viewModelScope.launch(ioDispatcher) {
            val task = taskRepository.queryTask(taskId)
            _taskTitle.value = task.task.title
            _taskDescription.value = task.task.description
            _taskCategoryId.value = task.task.categoryId
            _taskPriority.value = task.task.priority // Assuming your TaskEntity supports this
            _taskDueDate.value = task.task.dueDate // Assuming it's stored as LocalDate or similar
        }
    }

    fun handleSaveTask(taskId: Int?, isEdit: Boolean) {
        viewModelScope.launch(ioDispatcher) {
            saveTaskUseCase.execute(
                taskId = taskId,
                taskTitle = _taskTitle.value,
                taskDescription = _taskDescription.value,
                taskCategoryId = _taskCategoryId.value,
                priority = _taskPriority.value,
                dueDate = _taskDueDate.value,
                isEdit = isEdit
            )
        }
    }

    fun addCategory(category: Category, onAdded: (Int) -> Unit) {
        viewModelScope.launch(ioDispatcher) {
            val result = categoryRepository.insert(category)
            onAdded(result.toInt())
        }
    }
}