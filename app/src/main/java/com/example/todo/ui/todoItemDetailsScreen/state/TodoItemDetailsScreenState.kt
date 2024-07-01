package com.example.todo.ui.todoItemDetailsScreen.state

import com.example.todo.ui.todoItemsScreen.state.TodoItemUiModel

sealed interface TodoItemDetailsScreenState {
        data object Loading : TodoItemDetailsScreenState
        data class Success(
            val todoItemDetailsUiModel: TodoItemDetailsUiModel,
        ) : TodoItemDetailsScreenState
        data class Error(
            val message: String,
        ) : TodoItemDetailsScreenState

}