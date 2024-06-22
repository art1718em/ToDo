package com.example.todo.ui.todoItemDetailsScreen.state

import com.example.todo.domain.model.Importance

data class TodoItemDetailsUiModel(
    val id: String = "",
    val text: String = "",
    val importance: Importance = Importance.Usual,
    val deadline: String? = null,
)
