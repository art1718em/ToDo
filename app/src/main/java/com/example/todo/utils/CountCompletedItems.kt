package com.example.todo.utils

import com.example.todo.ui.todoItemsScreen.state.TodoItemUiModel

fun List<TodoItemUiModel>.countCompletedItems(): Int {
    return this.count { it.isCompleted }
}