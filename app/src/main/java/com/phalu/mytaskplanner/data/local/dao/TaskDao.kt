package com.phalu.mytaskplanner.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.phalu.mytaskplanner.data.local.entities.Task
import com.phalu.mytaskplanner.data.local.entities.TaskWithCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Transaction
    @Query("SELECT * FROM tasks")
    fun getAllTasks(): Flow<List<TaskWithCategory>>

    @Transaction
    @Query("SELECT * FROM tasks WHERE id = :taskId")
    suspend fun getTaskWithCategory(taskId: Int): TaskWithCategory

    @Transaction
    @Query("SELECT * FROM tasks WHERE categoryId = :categoryId")
    fun getTasksByCategory(categoryId: Int): Flow<List<TaskWithCategory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Query("DELETE FROM tasks")
    suspend fun deleteAllTasks()
}