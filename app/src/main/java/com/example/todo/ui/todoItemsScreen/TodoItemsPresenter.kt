package com.example.todo.ui.todoItemsScreen

import javax.inject.Inject
import javax.inject.Singleton


@Singleton
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

    fun deleteItem(id: String){
        todoItemsViewModel.deleteTodoItem(id)
    }

    fun navigateToItemDetails(id: String?){
        todoItemsViewModel.navigateToTodoItemDetails(id)
    }
}