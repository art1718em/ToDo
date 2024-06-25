package com.example.todo.ui.todoItemDetailsScreen

import com.example.todo.domain.model.Importance
import java.util.Date
import javax.inject.Inject

class TodoItemDetailsPresenter @Inject constructor(
    private val todoItemDetailsViewModel: TodoItemDetailsViewModel,
) {

    val todoItemDetailsUiModel = todoItemDetailsViewModel.todoItemDetailsUiModel

    fun updateText(text: String){
        todoItemDetailsViewModel.updateText(text)
    }

    fun updateImportance(importance: Importance){
        todoItemDetailsViewModel.updateImportance(importance)
    }

    fun updateDeadline(deadline: Date?){
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