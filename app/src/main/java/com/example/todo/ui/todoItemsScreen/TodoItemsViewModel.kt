package com.example.todo.ui.todoItemsScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.data.preferences.PreferencesManager
import com.example.todo.di.todoItemsScreen.TodoItemsFragmentScope
import com.example.todo.domain.interactor.TodoItemsInteractor
import com.example.todo.domain.model.TodoItem
import com.example.todo.domain.model.UserThemeChoice
import com.example.todo.navigation.FragmentNavigation
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
import java.util.Calendar
import javax.inject.Inject

@TodoItemsFragmentScope
class TodoItemsViewModel @Inject constructor(
    private val interactor: TodoItemsInteractor,
    private val preferencesManager: PreferencesManager,
    private val fragmentNavigation: FragmentNavigation,
) : ViewModel() {

    private var todoItems = listOf<TodoItemUiModel>()

    private var loadingTodoItemsJob: Job? = null
    private var updatingIsCompletedTodoItemJob: Job? = null

    private val _todoItemsScreenUiState =
        MutableStateFlow<TodoItemsScreenState>(TodoItemsScreenState.Loading)
    val todoItemsScreenUiState = _todoItemsScreenUiState.asStateFlow()

    private val _effectFlow = MutableSharedFlow<TodoItemsScreenUiEffects>()
    val effectFlow = _effectFlow.asSharedFlow()

    private val _userThemeChoice =
        MutableStateFlow<UserThemeChoice>(UserThemeChoice.SystemThemeChoice)
    val userThemeChoice = _userThemeChoice.asStateFlow()

    init {
        loadTodoItems()
        collectTodoItems()
        collectMessages()
        collectUserThemeChoice()
    }

    private fun collectMessages() {
        viewModelScope.launch(Dispatchers.Default) {
            interactor.toastMessage.collect {
                _effectFlow.emit(TodoItemsScreenUiEffects.ShowErrorMessage(it))
            }
        }
    }

    private fun collectUserThemeChoice() {
        viewModelScope.launch(Dispatchers.Default) {
            preferencesManager.selectedUserThemeChoice.collect {
                _userThemeChoice.value = it
            }
        }
    }

    private fun collectTodoItems() {
        loadingTodoItemsJob?.cancel()
        loadingTodoItemsJob =
            interactor.todoItemsResult.collectIn(viewModelScope) { resultOfTodoItems ->
                val currentTodoItemsScreenUiState = todoItemsScreenUiState.value
                val isHidden = when (currentTodoItemsScreenUiState) {
                    is TodoItemsScreenState.Success -> currentTodoItemsScreenUiState.isHiddenCompletedItems
                    else -> false
                }
                if (resultOfTodoItems == null) {
                    _todoItemsScreenUiState.value = TodoItemsScreenState.Loading
                } else if (resultOfTodoItems.isSuccess) {
                    todoItems =
                        resultOfTodoItems.getOrDefault(emptyList()).map { it.toTodoItemsUiModel() }
                    _todoItemsScreenUiState.value = TodoItemsScreenState.Success(
                        todoItems = if (isHidden) {
                            todoItems.filter { !it.isCompleted }
                        } else {
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

    fun loadTodoItems() {
        viewModelScope.launch {
            Log.d("mytag", "vm")
            interactor.getTodoItems()
        }
    }

    fun updateIsCompeted(id: String, isChecked: Boolean) {
        updatingIsCompletedTodoItemJob?.cancel()
        val currentDateMillis = Calendar.getInstance().timeInMillis
        updatingIsCompletedTodoItemJob = viewModelScope.launch(Dispatchers.IO) {
            interactor.updateCompleted(
                id = id,
                isCompleted = isChecked,
                dateOfChange = currentDateMillis,
            )
        }
    }

    fun changeHiddenCompletedItems(isHiddenCompleted: Boolean) {
        _todoItemsScreenUiState.value = TodoItemsScreenState.Success(
            todoItems = if (isHiddenCompleted) {
                todoItems.filter { !it.isCompleted }
            } else {
                todoItems
            },
            countOfCompletedItems = todoItems.countCompletedItems(),
            isHiddenCompletedItems = isHiddenCompleted,
        )
    }

    fun deleteTodoItem(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.deleteTodoItem(id)
        }
    }

    fun navigateToTodoItemDetails(idOfTodoItem: String?) {
        fragmentNavigation.navigateToTodoItemDetailsFragment(idOfTodoItem)
    }

    fun navigateToUserThemeChoice() {
        fragmentNavigation.navigateToUserThemeChoiceFragment()
    }

}

fun TodoItem.toTodoItemsUiModel(): TodoItemUiModel {
    return TodoItemUiModel(
        id = id,
        text = text,
        isCompleted = isCompleted,
        importance = importance,
        deadline = DateFormatting.toFormattedDate(deadline),
    )
}