package com.example.todo.ui.todoItemsScreen.state

sealed interface TodoItemsScreenState {
    data object Loading : TodoItemsScreenState
    data class Success(
        val todoItems: List<TodoItemUiModel>,
        val countOfCompletedItems: Int,
        val isHiddenCompletedItems: Boolean,
    ) : TodoItemsScreenState
    data class Error(
        val message: String,
    ) : TodoItemsScreenState
}