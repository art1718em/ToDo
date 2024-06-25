package com.example.todo.ui.todoItemsScreen.state

import com.example.todo.domain.model.Importance

data class TodoItemUiModel(
    val id: String,
    val text: String,
    val isCompleted: Boolean,
    val importance: Importance,
    val deadline: String?,
)