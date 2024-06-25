package com.example.todo.ui.todoItemsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.todo.data.repository.TodoItemsRepository
import com.example.todo.domain.model.TodoItem
import com.example.todo.navigation.Screen
import com.example.todo.ui.todoItemsScreen.state.TodoItemUiModel
import com.example.todo.ui.todoItemsScreen.state.TodoItemsScreenState
import com.example.todo.utils.collectIn
import com.example.todo.utils.countCompletedItems
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TodoItemsViewModel @Inject constructor(
    private val todoItemsRepository: TodoItemsRepository,
    private val navController: NavController,
) : ViewModel() {

    private var todoItems = listOf<TodoItemUiModel>()

    private val _todoItemsScreenUiState = MutableStateFlow<TodoItemsScreenState>(TodoItemsScreenState.Loading)
    val todoItemsScreenUiState = _todoItemsScreenUiState.asStateFlow()

    init{
        collectTodoItems()
    }

    private fun collectTodoItems(){
        todoItemsRepository.todoItems.collectIn(viewModelScope){ todoItemsList ->
            val currentTodoItemsScreenUiState = todoItemsScreenUiState.value
            val isHidden = when(currentTodoItemsScreenUiState){
                is TodoItemsScreenState.Success -> currentTodoItemsScreenUiState.isHiddenCompletedItems
                else -> false
            }
            todoItems = todoItemsList.map { it.toTodoItemsUiModel() }
            _todoItemsScreenUiState.value = TodoItemsScreenState.Success(
                todoItems = if (isHidden) {
                    todoItems.filter { !it.isCompleted }
                } else{
                    todoItems
                },
                countOfCompletedItems = todoItems.countCompletedItems(),
                isHiddenCompletedItems = isHidden,
            )
        }

    }

    fun updateIsCompeted(id: String, isChecked: Boolean){
        todoItemsRepository.updateChecked(
            id =  id,
            isCompleted = isChecked,
        )
    }

    fun changeHiddenCompletedItems(isHiddenCompleted: Boolean){
        _todoItemsScreenUiState.value = TodoItemsScreenState.Success(
            todoItems = if (isHiddenCompleted) {
                todoItems.filter { !it.isCompleted }
            } else{
                todoItems
            },
            countOfCompletedItems = todoItems.countCompletedItems(),
            isHiddenCompletedItems = isHiddenCompleted,
        )
    }

    fun navigateToTodoItemDetails(idOfTodoItem: String){
        navController.navigate("${Screen.TodoItemDetailsScreen.route}/${idOfTodoItem}")
    }

}

fun TodoItem.toTodoItemsUiModel(): TodoItemUiModel{
    return TodoItemUiModel(
            id = id,
            text = text,
            isCompleted = isCompleted,
            importance = importance,
            deadline = deadline,
        )
}