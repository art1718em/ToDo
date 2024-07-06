package com.example.todo.ui.todoItemDetailsScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.todo.data.repository.TodoItemsRepository
import com.example.todo.domain.model.Importance
import com.example.todo.domain.model.TodoItem
import com.example.todo.navigation.Screen
import com.example.todo.ui.todoItemDetailsScreen.state.TodoItemDetailsScreenState
import com.example.todo.ui.todoItemDetailsScreen.state.TodoItemDetailsScreenUiEffects
import com.example.todo.ui.todoItemDetailsScreen.state.TodoItemDetailsUiModel
import com.example.todo.ui.todoItemsScreen.state.TodoItemsScreenUiEffects
import com.example.todo.utils.DateFormatting
import com.example.todo.utils.UNKNOWN_MESSAGE
import com.example.todo.utils.generateId
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

class TodoItemDetailsViewModel @Inject constructor(
    private val repository: TodoItemsRepository,
    private val navController: NavController,
) : ViewModel() {

    private val _todoItemDetailsScreenState =
        MutableStateFlow<TodoItemDetailsScreenState>(TodoItemDetailsScreenState.Loading)
    val todoItemDetailsScreenState = _todoItemDetailsScreenState.asStateFlow()

    private val _effectFlow = MutableSharedFlow<TodoItemDetailsScreenUiEffects>()
    val effectFlow = _effectFlow.asSharedFlow()


    private val todoItem = MutableStateFlow(TodoItem.defaultTodoItem)

    private var loadingTodoItemJob: Job? = null
    private var savingTodoItemJob: Job? = null
    private var deletionTodoItemJob: Job? = null

    private val id: String? = navController
        .getBackStackEntry("${Screen.TodoItemDetailsScreen.route}/{id}")
        .arguments
        ?.getString("id")

    init {
        loadTodoItem()
    }

    fun loadTodoItem() {
        if (id == null) {
            _todoItemDetailsScreenState.value =
                TodoItemDetailsScreenState.Success(TodoItemDetailsUiModel())
            return
        }
        loadingTodoItemJob?.cancel()
        loadingTodoItemJob = viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getItem(id)
            if (result.isSuccess){
                todoItem.value = result.getOrThrow()
                _todoItemDetailsScreenState.value =
                    TodoItemDetailsScreenState.Success(todoItem.value.toTodoItemDetailsUiModel())
            } else {
                _todoItemDetailsScreenState.value =
                    TodoItemDetailsScreenState.Error(result.exceptionOrNull()?.message ?: UNKNOWN_MESSAGE)
            }
        }
    }

    fun updateText(text: String) {
        val currentTodoItemDetailsScreenState = todoItemDetailsScreenState.value
        if (currentTodoItemDetailsScreenState is TodoItemDetailsScreenState.Success){
            _todoItemDetailsScreenState.value = TodoItemDetailsScreenState.Success(
                currentTodoItemDetailsScreenState.todoItemDetailsUiModel.copy(
                    text = text
                )
            )
        }
    }

    fun updateImportance(importance: Importance) {
        val currentTodoItemDetailsScreenState = todoItemDetailsScreenState.value
        if (currentTodoItemDetailsScreenState is TodoItemDetailsScreenState.Success){
            _todoItemDetailsScreenState.value = TodoItemDetailsScreenState.Success(
                currentTodoItemDetailsScreenState.todoItemDetailsUiModel.copy(
                    importance = importance
                )
            )
        }
    }

    fun updateDeadline(deadline: Long?) {
        val currentTodoItemDetailsScreenState = todoItemDetailsScreenState.value
        if (currentTodoItemDetailsScreenState is TodoItemDetailsScreenState.Success){
            _todoItemDetailsScreenState.value = TodoItemDetailsScreenState.Success(
                currentTodoItemDetailsScreenState.todoItemDetailsUiModel.copy(
                    deadline = DateFormatting.toFormattedDate(deadline)
                )
            )
        }
    }

    fun saveItem() {
        savingTodoItemJob?.cancel()
        val currentDateMillis = Calendar.getInstance().timeInMillis
        savingTodoItemJob = viewModelScope.launch {
            val currentTodoItemDetailsScreenState = todoItemDetailsScreenState.value
            if (currentTodoItemDetailsScreenState is TodoItemDetailsScreenState.Success) {
                val item = TodoItem(
                    id = todoItem.value.id.ifEmpty {
                        generateId()
                    },
                    text = currentTodoItemDetailsScreenState.todoItemDetailsUiModel.text,
                    deadline = DateFormatting.toDateLong(currentTodoItemDetailsScreenState.todoItemDetailsUiModel.deadline),
                    importance = currentTodoItemDetailsScreenState.todoItemDetailsUiModel.importance,
                    isCompleted = if (todoItem.value.id.isEmpty()) {
                        false
                    } else {
                        todoItem.value.isCompleted
                    },
                    dateOfCreation = if (todoItem.value.dateOfCreation == 0L) {
                        currentDateMillis
                    } else {
                        todoItem.value.dateOfCreation
                    },
                    dateOfChange = currentDateMillis,
                )

                val result = if (todoItem.value.id.isEmpty()){
                    repository.addTodoItem(item)
                } else {
                    repository.updateTodoItem(item)
                }

                if (result.isSuccess){
                    navigateToItems()
                } else {
                    _effectFlow.emit(TodoItemDetailsScreenUiEffects.ShowErrorMessage(result.exceptionOrNull()?.message ?: UNKNOWN_MESSAGE))
                }

            }
        }
    }


    fun navigateToItems() {
        loadingTodoItemJob?.cancel()
        navController.popBackStack()
    }

    fun deleteTodoItem() {
        deletionTodoItemJob?.cancel()
        deletionTodoItemJob = viewModelScope.launch {
            val result = repository.deleteItem(todoItem.value.id)
            if (result.isSuccess){
                navigateToItems()
            } else {
                _effectFlow.emit(TodoItemDetailsScreenUiEffects.ShowErrorMessage(result.exceptionOrNull()?.message ?: UNKNOWN_MESSAGE))
            }
        }
    }

    private fun TodoItem.toTodoItemDetailsUiModel(): TodoItemDetailsUiModel {
        return TodoItemDetailsUiModel(
            id = id,
            text = text,
            importance = importance,
            deadline = DateFormatting.toFormattedDate(deadline),
        )
    }
}

