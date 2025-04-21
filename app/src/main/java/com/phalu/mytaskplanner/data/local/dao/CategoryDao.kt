package com.phalu.mytaskplanner.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.phalu.mytaskplanner.data.local.entities.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories")
    fun getAllCategories(): Flow<List<Category>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Category): Long

    @Update
    suspend fun update(task: Category)

    @Delete
    suspend fun delete(task: Category)

    @Query("DELETE FROM categories")
    suspend fun deleteAllCategories()
}