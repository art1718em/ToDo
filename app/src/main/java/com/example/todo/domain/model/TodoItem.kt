package com.example.todo.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class TodoItem(
    val id: String,
    val text: String,
    val deadline: Long?,
    val importance: Importance,
    val isCompleted: Boolean,
    val dateOfCreation: Long,
    val dateOfChange: Long?,
) {
    companion object {
        val defaultTodoItem = TodoItem(
            id = "",
            text = "",
            deadline = null,
            importance = Importance.Usual,
            isCompleted = false,
            dateOfCreation = 0L,
            dateOfChange = null,
        )
    }
}