package com.example.todo.domain.interactor

import com.example.todo.data.network.InternetConnection
import com.example.todo.data.repository.LocalTodoItemsRepository
import com.example.todo.data.repository.NetworkTodoItemsRepository
import com.example.todo.di.activity.MainActivityScope
import com.example.todo.domain.model.TodoItem
import com.example.todo.utils.NO_INTERNET
import com.example.todo.utils.SYNCHRONIZED_MESSAGE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@MainActivityScope
class TodoItemsInteractor @Inject constructor(
    private val networkTodoItemsRepository: NetworkTodoItemsRepository,
    private val localTodoItemsRepository: LocalTodoItemsRepository,
    private val internetConnection: InternetConnection,
) {

    private var todoItems = emptyList<TodoItem>()

    private val _todoItemsResult = MutableStateFlow<Result<List<TodoItem>>?>(null)
    val todoItemsResult = _todoItemsResult.asStateFlow()

    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

    private var isNetworkAvailable: Boolean = false

    init {
        collectNetworkState()
    }

    suspend fun getTodoItems() {
        withContext(Dispatchers.IO) {
            _todoItemsResult.value = null
            var currentTodoItems = localTodoItemsRepository.getTodoItems()
            if (isNetworkAvailable) {
                val result = if (currentTodoItems.isNotEmpty()) {
                    networkTodoItemsRepository.patchTodoItems(currentTodoItems)
                } else {
                    networkTodoItemsRepository.getTodoItems()
                }
                if (result.isSuccess) {
                    val items = result.getOrNull()
                    if (items != null) {
                        localTodoItemsRepository.upsertList(items)
                        currentTodoItems = items
                        _toastMessage.emit(SYNCHRONIZED_MESSAGE)
                    }
                }
            } else {
                _toastMessage.emit(NO_INTERNET)
            }
            todoItems = currentTodoItems
            _todoItemsResult.value = Result.success(currentTodoItems)
        }
    }

    suspend fun updateTodoItem(todoItem: TodoItem) {
        withContext(Dispatchers.IO) {
            localTodoItemsRepository.upsertTodoItem(todoItem)
            if (isNetworkAvailable) {
                networkTodoItemsRepository.updateTodoItem(todoItem)
            }
            todoItems = todoItems.map {
                if (it.id == todoItem.id){
                    todoItem
                } else{
                    it
                }
            }
            _todoItemsResult.value = Result.success(todoItems)
        }
    }

    suspend fun addTodoItem(todoItem: TodoItem) {
        withContext(Dispatchers.IO) {
            localTodoItemsRepository.upsertTodoItem(todoItem)
            if (isNetworkAvailable) {
                networkTodoItemsRepository.addTodoItem(todoItem)
            }
            todoItems = todoItems + todoItem
            _todoItemsResult.value = Result.success(todoItems)
        }
    }

    suspend fun deleteTodoItem(id: String) {
        withContext(Dispatchers.IO) {
            localTodoItemsRepository.deleteItem(id)
            if (isNetworkAvailable) {
                networkTodoItemsRepository.deleteItem(id)
            }
            todoItems = todoItems.filterNot { it.id == id }
        }
        _todoItemsResult.value = Result.success(todoItems)
    }

    suspend fun updateCompleted(
        id: String,
        isCompleted: Boolean,
        dateOfChange: Long
    ) {
        withContext(Dispatchers.IO) {

            val newItem = todoItems
                .firstOrNull { it.id == id }
                ?.copy(
                    isCompleted = isCompleted,
                    dateOfChange = dateOfChange,
                )
                ?: return@withContext

            localTodoItemsRepository.upsertTodoItem(newItem)
            if (isNetworkAvailable) {
                networkTodoItemsRepository.updateTodoItem(newItem)
            }
            todoItems = todoItems.map {
                if (it.id == newItem.id){
                    newItem
                } else{
                    it
                }
            }
            _todoItemsResult.value = Result.success(todoItems)
        }
    }

    suspend fun getItem(id: String): Result<TodoItem> {
        return withContext(Dispatchers.IO) {
            Result.success(localTodoItemsRepository.getItem(id))
        }
    }

    private fun collectNetworkState() {
        CoroutineScope(Dispatchers.IO).launch {
            internetConnection.observeNetworkState().collectLatest { networkState ->
                isNetworkAvailable = networkState
                if (isNetworkAvailable) {
                    getTodoItems()
                } else {
                    _toastMessage.emit(NO_INTERNET)
                }
            }
        }
    }

}