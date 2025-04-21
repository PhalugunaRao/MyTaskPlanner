package com.phalu.mytaskplanner.domain.usecase

import com.phalu.mytaskplanner.data.repository.TaskRepository
import com.phalu.mytaskplanner.data.local.entities.Task
import java.time.LocalDate
import javax.inject.Inject

interface SaveTaskUseCase {
    suspend fun execute(
        taskId: Int? = null,
        taskTitle: String,
        taskDescription: String,
        taskCategoryId: Int? = null,
        priority: Priority,
        dueDate: LocalDate?,
        isEdit: Boolean
    )
}

class SaveTaskUseCaseImpl @Inject constructor(
    private val taskRepository: TaskRepository
) : SaveTaskUseCase {
    override suspend fun execute(
        taskId: Int?,
        taskTitle: String,
        taskDescription: String,
        taskCategoryId: Int?,
        priority: Priority,
        dueDate: LocalDate?,
        isEdit: Boolean
    ) {
        val task = Task(
            title = taskTitle,
            description = taskDescription,
            categoryId = taskCategoryId,
            priority = priority,
            dueDate = dueDate


        )

        if (isEdit) {
            taskId?.let {
                val editTask = task.copy(id = it)
                taskRepository.update(editTask)
            }
        } else {
            taskRepository.insert(task)
        }
    }
}