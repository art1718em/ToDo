package com.example.todo.ui.todoItemsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.data.repository.TodoItemsRepository
import com.example.todo.domain.model.TodoItem
import com.example.todo.ui.todoItemsScreen.state.TodoItemUiModel
import com.example.todo.ui.todoItemsScreen.state.TodoItemsScreenState
import com.example.todo.utils.UNKNOWN_MESSAGE
import com.example.todo.utils.countCompletedItems
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class TodoItemsViewModel @Inject constructor(
    private val todoItemsRepository: TodoItemsRepository,
) : ViewModel() {

    private var todoItems = listOf<TodoItemUiModel>()

    private val _todoItemsScreenUiState = MutableStateFlow<TodoItemsScreenState>(TodoItemsScreenState.Loading)
    val todoItemsScreenUiState = _todoItemsScreenUiState.asStateFlow()

    init{
        loadTodoItems()
    }

    private fun loadTodoItems(){
        val currentTodoItemsScreenUiState = todoItemsScreenUiState.value
        val isHidden = when(currentTodoItemsScreenUiState){
            is TodoItemsScreenState.Success -> currentTodoItemsScreenUiState.isHiddenCompletedItems
            else -> false
        }
        _todoItemsScreenUiState.value = TodoItemsScreenState.Loading
        viewModelScope.launch {
            val result = todoItemsRepository.getTodoItems()
            _todoItemsScreenUiState.value = if (result.isSuccess){
                todoItems = result.getOrThrow().map { it.toTodoItemsUiModel() }
                TodoItemsScreenState.Success(
                    todoItems = if (isHidden) {
                        todoItems.filter { !it.isCompleted }
                    } else{
                        todoItems
                    },
                    countOfCompletedItems = todoItems.countCompletedItems(),
                    isHiddenCompletedItems = isHidden,
                )
            }else{
                TodoItemsScreenState.Error(
                    result.exceptionOrNull()?.message ?: UNKNOWN_MESSAGE
                )
            }
        }
    }

    fun updateIsCompeted(id: String, isChecked: Boolean){
        todoItemsRepository.updateChecked(
            id =  id,
            isCompleted = isChecked,
        )
        loadTodoItems()
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