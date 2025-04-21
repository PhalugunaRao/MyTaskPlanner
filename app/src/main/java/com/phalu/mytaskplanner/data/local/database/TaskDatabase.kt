package com.phalu.mytaskplanner.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.phalu.mytaskplanner.data.local.dao.CategoryDao
import com.phalu.mytaskplanner.data.local.dao.TaskDao
import com.phalu.mytaskplanner.data.local.entities.Category
import com.phalu.mytaskplanner.data.local.entities.Task
import dagger.hilt.android.qualifiers.ApplicationContext

@Database(entities = [Task::class, Category::class], version = 1, exportSchema = false)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun categoryDao(): CategoryDao

    companion object {

        fun createDataBase(
            @ApplicationContext appContext: Context
        ) : TaskDatabase {
            return Room.databaseBuilder(
                appContext,
                TaskDatabase::class.java,
                "task_database"
            )
                .build()
        }
    }
}