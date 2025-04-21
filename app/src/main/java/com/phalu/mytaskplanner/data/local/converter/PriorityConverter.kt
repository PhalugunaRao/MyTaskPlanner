package com.phalu.mytaskplanner.data.local.converter

import androidx.room.TypeConverter
import com.phalu.mytaskplanner.domain.usecase.Priority

class PriorityConverter {
    @TypeConverter
    fun fromPriority(priority: Priority): String = priority.name

    @TypeConverter
    fun toPriority(value: String): Priority = Priority.valueOf(value)
}