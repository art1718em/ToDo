package com.example.todo.data.repository

import com.example.todo.data.network.TodoItemApi
import com.example.todo.domain.mapper.Mapper
import com.example.todo.domain.model.TodoItem
import com.example.todo.utils.NOT_FOUND_ITEM_MESSAGE
import com.example.todo.utils.safeTry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TodoItemsRepository @Inject constructor(
    private val todoItemApi: TodoItemApi,
    private val mapper: Mapper,
){

    private var todoItems = emptyList<TodoItem>()

    private val _resultTodoItems = MutableStateFlow<Result<List<TodoItem>>?>(null)
    val resultTodoItems: StateFlow<Result<List<TodoItem>>?> get()= _resultTodoItems

    suspend fun getTodoItems(){
        withContext(Dispatchers.IO) {
            _resultTodoItems.value = safeTry {
                _resultTodoItems.value = null
                val list = todoItemApi.getList()
                todoItems = list.map { mapper.mapDtoToModel(it) }
                Result.success(todoItems)
            }
        }
    }

    suspend fun addTodoItem(item: TodoItem): Result<Unit>{
       return withContext(Dispatchers.IO){
           safeTry {
               val revision = todoItemApi.getRevision()
               todoItemApi.addTodo(mapper.mapModelToPost(item), revision.toString())
               todoItems += item
               _resultTodoItems.value = Result.success(todoItems)
               Result.success(Unit)
           }
        }
    }

    suspend fun updateChecked(id: String, isCompleted: Boolean): Result<Unit>{
        val newItem = todoItems
            .firstOrNull { it.id == id }
            ?.copy(isCompleted = isCompleted)
            ?: return Result.failure(Exception(NOT_FOUND_ITEM_MESSAGE))

        return withContext(Dispatchers.IO){
             safeTry {
                val revision = todoItemApi.getRevision()
                 todoItemApi.updateTodo(mapper.mapModelToPost(newItem), revision.toString())

                todoItems = todoItems.map {
                    if (it.id == id) it.copy(isCompleted = isCompleted) else it
                }
                _resultTodoItems.value = Result.success(todoItems)
                Result.success(Unit)
            }
        }
    }

    suspend fun updateTodoItem(item: TodoItem): Result<Unit>{
        return withContext(Dispatchers.IO) {
             safeTry {
                val revision = todoItemApi.getRevision()
                todoItemApi.updateTodo(mapper.mapModelToPost(item), revision.toString())
                val todoItemDtoList = todoItemApi.getList()
                todoItems = todoItemDtoList.map { mapper.mapDtoToModel(it) }
                _resultTodoItems.value = Result.success(todoItems)
                Result.success(Unit)
            }
        }
    }

    fun getItem(todoId: String): Result<TodoItem> {
        return safeTry{
            val item = todoItems.find { it.id == todoId }
            item?.let {
                Result.success(item)
            }?: throw Exception(NOT_FOUND_ITEM_MESSAGE)
        }
    }

    suspend fun deleteItem(id: String): Result<Unit>{
        return withContext(Dispatchers.IO){
            safeTry {
                val revision = todoItemApi.getRevision()
                todoItemApi.deleteTodo(id, revision.toString())
                val todoItemDtoList = todoItemApi.getList()
                todoItems = todoItemDtoList.map { mapper.mapDtoToModel(it) }
                _resultTodoItems.value = Result.success(todoItems)
                Result.success(Unit)
            }
        }
    }
}