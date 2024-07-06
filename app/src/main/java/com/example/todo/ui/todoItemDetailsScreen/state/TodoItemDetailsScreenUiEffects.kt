package com.example.todo.ui.todoItemDetailsScreen.state

sealed interface TodoItemDetailsScreenUiEffects {
    data class ShowErrorMessage(val message: String) : TodoItemDetailsScreenUiEffects
}