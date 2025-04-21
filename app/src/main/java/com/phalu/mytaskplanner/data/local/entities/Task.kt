package com.phalu.mytaskplanner.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.phalu.mytaskplanner.data.local.converter.LocalDateConverter
import com.phalu.mytaskplanner.data.local.converter.PriorityConverter
import com.phalu.mytaskplanner.domain.usecase.Priority
import java.time.LocalDate

@Entity(
    tableName = "tasks",
    foreignKeys = [ForeignKey(
        entity = Category::class,
        parentColumns = arrayOf("id"), // This is the column in the Category entity
        childColumns = arrayOf("categoryId"), // This is the column in the Task entity
        onDelete = ForeignKey.SET_NULL // Optional: delete tasks when the category is deleted
    )],
    indices = [Index(value = ["categoryId"])]
)
@TypeConverters(PriorityConverter::class, LocalDateConverter::class)
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var title: String,
    var description: String,
    var categoryId: Int? = null,
    val isCompleted: Boolean = false,
    var priority: Priority = Priority.MEDIUM,
    var dueDate: LocalDate? = null
)