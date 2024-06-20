package com.example.todo.ui.todoItemsScreen

import javax.inject.Inject

class TodoItemsPresenter @Inject constructor(
    private val todoItemsViewModel: TodoItemsViewModel,
){
    val todoItemsScreenUiState = todoItemsViewModel.todoItemsScreenUiState

    fun updateIsCompleted(id: String, isChecked: Boolean){
        todoItemsViewModel.updateIsCompeted(id, isChecked)
    }

    fun changeHiddenCompletedItems(isHiddenCompletedItems: Boolean){
        todoItemsViewModel.changeHiddenCompletedItems(isHiddenCompletedItems)
    }
}