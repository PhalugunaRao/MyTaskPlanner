package com.phalu.mytaskplanner.data.local.entities
import androidx.room.Embedded
import androidx.room.Relation

data class TaskWithCategory(
    @Embedded val task: Task,

    @Relation(
        parentColumn = "categoryId",
        entityColumn = "id"
    )
    val category: Category?
)