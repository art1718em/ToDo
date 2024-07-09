package com.example.todo.ui.todoItemsScreen

import androidx.compose.runtime.Stable
import javax.inject.Inject
import javax.inject.Singleton

@Stable
@Singleton
class TodoItemsPresenter @Inject constructor(
    private val todoItemsViewModel: TodoItemsViewModel,
){
    val todoItemsScreenUiState = todoItemsViewModel.todoItemsScreenUiState

    val todoItemsScreenUiEffects = todoItemsViewModel.effectFlow

    fun loadTodoItems(){
        todoItemsViewModel.loadTodoItems()
    }

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