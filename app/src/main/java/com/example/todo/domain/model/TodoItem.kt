package com.example.todo.domain.model

data class TodoItem(
    val id: String = "",
    val text: String = "",
    val deadline: String? = null,
    val importance: Importance = Importance.Usual,
    val isCompleted: Boolean = false,
    val dateOfCreation: String = "",
    val dateOfChange: String? = null,
)