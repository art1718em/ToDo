package com.example.todo.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todo.domain.model.Importance

@Entity(tableName = "todoItems")
data class TodoItemEntity(
    @PrimaryKey
    val id: String,
    val text: String,
    val deadline: Long?,
    val importance: String,
    val isCompleted: Boolean,
    val dateOfCreation: Long,
    val dateOfChange: Long?,
)