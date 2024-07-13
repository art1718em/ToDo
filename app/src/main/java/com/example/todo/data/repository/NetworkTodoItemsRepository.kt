package com.example.todo.data.repository

import com.example.todo.data.local.TodoItemEntity
import com.example.todo.data.network.TodoItemApi
import com.example.todo.data.network.dto.PatchPost
import com.example.todo.di.activity.MainActivityScope
import com.example.todo.domain.mapper.Mapper
import com.example.todo.domain.model.TodoItem
import com.example.todo.utils.NOT_FOUND_ITEM_MESSAGE
import com.example.todo.utils.safeTry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@MainActivityScope
class NetworkTodoItemsRepository @Inject constructor(
    private val todoItemApi: TodoItemApi,
    private val mapper: Mapper,
){

    suspend fun getTodoItems(): Result<List<TodoItem>>{
        return withContext(Dispatchers.IO) {
            safeTry {
                val list = todoItemApi.getList()
                Result.success(list.map { mapper.mapDtoToModel(it) })
            }
        }
    }

    suspend fun addTodoItem(item: TodoItem): Result<Unit>{
       return withContext(Dispatchers.IO){
           safeTry {
               val revision = todoItemApi.getRevision()
               todoItemApi.addTodo(mapper.mapModelToPost(item), revision.toString())
               Result.success(Unit)
           }
        }
    }

    suspend fun updateTodoItem(item: TodoItem): Result<Unit>{
        return withContext(Dispatchers.IO) {
             safeTry {
                val revision = todoItemApi.getRevision()
                todoItemApi.updateTodo(mapper.mapModelToPost(item), revision.toString())
                Result.success(Unit)
            }
        }
    }

    suspend fun deleteItem(id: String): Result<Unit>{
        return withContext(Dispatchers.IO){
            safeTry {
                val revision = todoItemApi.getRevision()
                todoItemApi.deleteTodo(id, revision.toString())
                Result.success(Unit)
            }
        }
    }

    suspend fun patchTodoItems(list: List<TodoItem>): Result<List<TodoItem>>{
        return withContext(Dispatchers.IO){
            safeTry {
                val revision = todoItemApi.getRevision()
                val dtos = todoItemApi.patchTodo(
                    patchPost = PatchPost(
                        todoItemDtos = list.map { mapper.mapModelToDto(it) },
                        status = "ok"
                    ),
                    revision
                )
                Result.success(
                    dtos.map { mapper.mapDtoToModel(it) }
                )
            }
        }
    }
}