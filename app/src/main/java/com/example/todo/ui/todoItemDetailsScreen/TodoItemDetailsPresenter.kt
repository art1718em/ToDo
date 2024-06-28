package com.example.todo.ui.todoItemDetailsScreen

import com.example.todo.domain.model.Importance
import javax.inject.Inject

class TodoItemDetailsPresenter @Inject constructor(
    private val todoItemDetailsViewModel: TodoItemDetailsViewModel,
) {

    val todoItemDetailsScreenState = todoItemDetailsViewModel.todoItemDetailsScreenState

    fun loadTodoItem(){
        todoItemDetailsViewModel.loadTodoItem()
    }

    fun updateText(text: String){
        todoItemDetailsViewModel.updateText(text)
    }

    fun updateImportance(importance: Importance){
        todoItemDetailsViewModel.updateImportance(importance)
    }

    fun updateDeadline(deadline: Long?){
        todoItemDetailsViewModel.updateDeadline(deadline)
    }

    fun addItem(){
        todoItemDetailsViewModel.saveItem()
    }

    fun navigateToItems(){
        todoItemDetailsViewModel.navigateToItems()
    }

    fun deleteTodoItem(){
        todoItemDetailsViewModel.deleteTodoItem()
    }
}