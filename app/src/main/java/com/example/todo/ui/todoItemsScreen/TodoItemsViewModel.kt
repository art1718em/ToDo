package com.example.todo.ui.todoItemsScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.todo.data.repository.TodoItemsRepository
import com.example.todo.domain.model.TodoItem
import com.example.todo.navigation.Screen
import com.example.todo.ui.todoItemsScreen.state.TodoItemUiModel
import com.example.todo.ui.todoItemsScreen.state.TodoItemsScreenState
import com.example.todo.ui.todoItemsScreen.state.TodoItemsScreenUiEffects
import com.example.todo.utils.DateFormatting
import com.example.todo.utils.UNKNOWN_MESSAGE
import com.example.todo.utils.collectIn
import com.example.todo.utils.countCompletedItems
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TodoItemsViewModel @Inject constructor(
    private val todoItemsRepository: TodoItemsRepository,
    private val navController: NavController,
) : ViewModel() {

    private var todoItems = listOf<TodoItemUiModel>()

    private var loadingTodoItemsJob: Job? = null
    private var updatingIsCompletedTodoItemJob: Job? = null

    private val _todoItemsScreenUiState = MutableStateFlow<TodoItemsScreenState>(TodoItemsScreenState.Loading)
    val todoItemsScreenUiState = _todoItemsScreenUiState.asStateFlow()

    private val _effectFlow = MutableSharedFlow<TodoItemsScreenUiEffects>()
    val effectFlow = _effectFlow.asSharedFlow()

    init{
        loadTodoItems()
        collectTodoItems()
    }

    private fun collectTodoItems(){
        loadingTodoItemsJob?.cancel()
        loadingTodoItemsJob = todoItemsRepository.resultTodoItems.collectIn(viewModelScope){ resultOfTodoItems ->
            val currentTodoItemsScreenUiState = todoItemsScreenUiState.value
            val isHidden = when(currentTodoItemsScreenUiState){
                is TodoItemsScreenState.Success -> currentTodoItemsScreenUiState.isHiddenCompletedItems
                else -> false
            }
            if (resultOfTodoItems == null) {
                _todoItemsScreenUiState.value = TodoItemsScreenState.Loading
            } else if (resultOfTodoItems.isSuccess){
                todoItems = resultOfTodoItems.getOrThrow().map { it.toTodoItemsUiModel() }
                _todoItemsScreenUiState.value = TodoItemsScreenState.Success(
                    todoItems = if (isHidden) {
                        todoItems.filter { !it.isCompleted }
                    } else{
                        todoItems
                    },
                    countOfCompletedItems = todoItems.countCompletedItems(),
                    isHiddenCompletedItems = isHidden,
                )
            } else {
                _todoItemsScreenUiState.value = TodoItemsScreenState.Error(
                    message = resultOfTodoItems.exceptionOrNull()?.message ?: UNKNOWN_MESSAGE
                )
            }
        }
    }

    fun loadTodoItems(){
        Log.d("mytag", "загрузка данных")
        viewModelScope.launch {
            todoItemsRepository.getTodoItems()
        }
    }

    fun updateIsCompeted(id: String, isChecked: Boolean){
        updatingIsCompletedTodoItemJob?.cancel()
        updatingIsCompletedTodoItemJob = viewModelScope.launch(Dispatchers.IO) {
            val result = todoItemsRepository.updateChecked(
                id =  id,
                isCompleted = isChecked,
            )
            if (result.isFailure){
                _effectFlow.emit(
                    TodoItemsScreenUiEffects.ShowErrorMessage(
                        result.exceptionOrNull()?.message ?: UNKNOWN_MESSAGE
                    )
                )
            }
        }
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

    fun deleteTodoItem(id: String){
        viewModelScope.launch(Dispatchers.IO) {
            val result = todoItemsRepository.deleteItem(id)
            if (result.isFailure){
                _todoItemsScreenUiState.value = TodoItemsScreenState.Error(result.exceptionOrNull()?.message ?: UNKNOWN_MESSAGE)
            }
        }
    }

    fun navigateToTodoItemDetails(idOfTodoItem: String?){
        navController.navigate("${Screen.TodoItemDetailsScreen.route}/${idOfTodoItem}")
    }

}

fun TodoItem.toTodoItemsUiModel(): TodoItemUiModel{
    return TodoItemUiModel(
            id = id,
            text = text,
            isCompleted = isCompleted,
            importance = importance,
            deadline = DateFormatting.toFormattedDate(deadline),
        )
}