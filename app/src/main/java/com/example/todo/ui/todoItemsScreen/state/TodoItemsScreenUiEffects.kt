package com.example.todo.ui.todoItemsScreen.state

sealed interface TodoItemsScreenUiEffects {
    data class ShowErrorMessage(val message: String) : TodoItemsScreenUiEffects
}