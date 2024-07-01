package com.example.todo.domain.model

data class TodoItem(
    val id: String = "",
    val text: String = "",
    val deadline: Long? = null,
    val importance: Importance = Importance.Usual,
    val isCompleted: Boolean = false,
    val dateOfCreation: Long = 0L,
    val dateOfChange: Long? = null,
)