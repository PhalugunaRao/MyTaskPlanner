package com.phalu.mytaskplanner.data.repository

import com.phalu.mytaskplanner.data.local.dao.TaskDao
import com.phalu.mytaskplanner.data.local.entities.Task
import com.phalu.mytaskplanner.data.local.entities.TaskWithCategory
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


interface TaskRepository {
    val allTasks: Flow<List<TaskWithCategory>>
    suspend fun queryTask(taskId: Int): TaskWithCategory
    suspend fun insert(task: Task)
    suspend fun update(task: Task)
    suspend fun delete(task: Task)
}

open class TaskRepositoryImpl @Inject constructor(private val taskDao: TaskDao) : TaskRepository {
    override val allTasks: Flow<List<TaskWithCategory>> = taskDao.getAllTasks()

    override suspend fun queryTask(taskId: Int) = taskDao.getTaskWithCategory(taskId)
    override suspend fun insert(task: Task) = taskDao.insert(task)
    override suspend fun update(task: Task) = taskDao.update(task)
    override suspend fun delete(task: Task) = taskDao.delete(task)
}