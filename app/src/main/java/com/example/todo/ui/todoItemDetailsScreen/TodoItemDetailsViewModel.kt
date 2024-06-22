package com.example.todo.ui.todoItemDetailsScreen

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.todo.data.repository.TodoItemsRepository
import com.example.todo.domain.model.Importance
import com.example.todo.domain.model.TodoItem
import com.example.todo.navigation.Screen
import com.example.todo.ui.todoItemDetailsScreen.state.TodoItemDetailsUiModel
import com.example.todo.utils.generateId
import com.example.todo.utils.toFormattedDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

class TodoItemDetailsViewModel @Inject constructor(
    private val repository: TodoItemsRepository,
    private val navController: NavController,
) : ViewModel() {

    private val _todoItemDetailsUiModel = MutableStateFlow(TodoItemDetailsUiModel())
    val todoItemDetailsUiModel = _todoItemDetailsUiModel.asStateFlow()

    private val todoItem = MutableStateFlow(TodoItem())

    private val id: String = navController
        .getBackStackEntry("${Screen.TodoItemDetailsScreen.route}/{id}")
        .arguments
        ?.getString("id") ?: ""

    init {
        if (id != "new")
            loadTodoItem(id)
    }

    private fun loadTodoItem(id: String){
        todoItem.value = repository.getItem(id)
        _todoItemDetailsUiModel.value = todoItem.value.toTodoItemDetailsUiModel()
    }

    fun updateText(text: String){
        _todoItemDetailsUiModel.value = todoItemDetailsUiModel.value.copy(
            text = text,
        )
    }

    fun updateImportance(importance: Importance){
        _todoItemDetailsUiModel.value = todoItemDetailsUiModel.value.copy(
            importance = importance,
        )
    }

    fun updateDeadline(deadline: Date?){
        _todoItemDetailsUiModel.value = todoItemDetailsUiModel.value.copy(
            deadline = deadline?.toFormattedDate(),
        )
    }

    fun saveItem(){
        val calendar = Calendar.getInstance()
        val currentDate = calendar.time
        repository.saveItem(
            TodoItem(
                id = todoItem.value.id.ifEmpty {
                    generateId()
                },
                text = todoItemDetailsUiModel.value.text,
                deadline = todoItemDetailsUiModel.value.deadline,
                importance = todoItemDetailsUiModel.value.importance,
                isCompleted = if (todoItem.value.id.isEmpty()){
                    false
                }else{
                    todoItem.value.isCompleted
                },
                dateOfCreation = todoItem.value.dateOfCreation.ifEmpty {
                    currentDate.toFormattedDate()
                },
                dateOfChange = if (todoItem.value.id.isEmpty()){
                    null
                }else{
                    currentDate.toFormattedDate()
                },
            )
        )
        navigateToItems()
    }

    fun navigateToItems(){
        navController.popBackStack()
    }

    fun deleteTodoItem(){
        repository.deleteItem(todoItem.value.id)
        navigateToItems()
    }
}

fun TodoItem.toTodoItemDetailsUiModel(): TodoItemDetailsUiModel{
    return TodoItemDetailsUiModel(
        id = id,
        text = text,
        importance = importance,
        deadline = deadline,
    )
}