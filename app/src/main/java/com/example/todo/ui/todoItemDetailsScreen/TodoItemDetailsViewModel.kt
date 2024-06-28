package com.example.todo.ui.todoItemDetailsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.todo.data.repository.TodoItemsRepository
import com.example.todo.domain.model.Importance
import com.example.todo.domain.model.TodoItem
import com.example.todo.navigation.Screen
import com.example.todo.ui.todoItemDetailsScreen.state.TodoItemDetailsScreenState
import com.example.todo.ui.todoItemDetailsScreen.state.TodoItemDetailsUiModel
import com.example.todo.utils.DateFormatting
import com.example.todo.utils.generateId
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
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

    private val todoItemDetailsUiModel = MutableStateFlow(TodoItemDetailsUiModel())

    private val todoItem = MutableStateFlow(TodoItem())

    private var loadingTodoItemJob: Job? = null
    private var savingTodoItemJob: Job? = null
    private var deletionTodoItemJob: Job? = null

    private val id: String? = navController
        .getBackStackEntry("${Screen.TodoItemDetailsScreen.route}/{id}")
        .arguments
        ?.getString("id")

    init {
        if (id != null)
            loadTodoItem()
        else
            _todoItemDetailsScreenState.value =
                TodoItemDetailsScreenState.Success(TodoItemDetailsUiModel())
    }

    fun loadTodoItem() {
        loadingTodoItemJob?.cancel()
        loadingTodoItemJob = viewModelScope.launch(Dispatchers.IO) {
            todoItem.value = repository.getItem(id!!)
            _todoItemDetailsScreenState.value =
                TodoItemDetailsScreenState.Success(todoItem.value.toTodoItemDetailsUiModel())
            if (_todoItemDetailsScreenState.value is TodoItemDetailsScreenState.Success) {
                todoItemDetailsUiModel.value = todoItem.value.toTodoItemDetailsUiModel()
            }
        }
    }

    fun updateText(text: String) {
        todoItemDetailsUiModel.value = todoItemDetailsUiModel.value.copy(
            text = text
        )
        _todoItemDetailsScreenState.value = TodoItemDetailsScreenState.Success(
            todoItemDetailsUiModel = todoItemDetailsUiModel.value
        )

    }

    fun updateImportance(importance: Importance) {
        todoItemDetailsUiModel.value = todoItemDetailsUiModel.value.copy(
            importance = importance,
        )
        _todoItemDetailsScreenState.value = TodoItemDetailsScreenState.Success(
            todoItemDetailsUiModel = todoItemDetailsUiModel.value
        )
    }

    fun updateDeadline(deadline: Long?) {
        todoItemDetailsUiModel.value = todoItemDetailsUiModel.value.copy(
            deadline = DateFormatting.toFormattedDate(deadline),
        )
        _todoItemDetailsScreenState.value = TodoItemDetailsScreenState.Success(
            todoItemDetailsUiModel = todoItemDetailsUiModel.value
        )
    }

    fun saveItem() {
        savingTodoItemJob?.cancel()
        val currentDateMillis = Calendar.getInstance().timeInMillis
        savingTodoItemJob = viewModelScope.launch(Dispatchers.IO) {
            repository.saveItem(
                TodoItem(
                    id = todoItem.value.id.ifEmpty {
                        generateId()
                    },
                    text = todoItemDetailsUiModel.value.text,
                    deadline = DateFormatting.toDateLong(todoItemDetailsUiModel.value.deadline),
                    importance = todoItemDetailsUiModel.value.importance,
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
                    dateOfChange = if (todoItem.value.id.isEmpty()) {
                        null
                    } else {
                        currentDateMillis
                    },
                )
            )
        }
        navigateToItems()
    }

    fun navigateToItems() {
        loadingTodoItemJob?.cancel()
        navController.popBackStack()
    }

    fun deleteTodoItem() {
        deletionTodoItemJob?.cancel()
        deletionTodoItemJob = viewModelScope.launch(Dispatchers.IO) {
            repository.deleteItem(todoItem.value.id)
        }
        navigateToItems()
    }
}

fun TodoItem.toTodoItemDetailsUiModel(): TodoItemDetailsUiModel {
    return TodoItemDetailsUiModel(
        id = id,
        text = text,
        importance = importance,
        deadline = DateFormatting.toFormattedDate(deadline),
    )
}