package com.example.todo.data.repository

import com.example.todo.data.local.TodoItemDao
import com.example.todo.di.activity.MainActivityScope
import com.example.todo.domain.mapper.Mapper
import com.example.todo.domain.model.TodoItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@MainActivityScope
class LocalTodoItemsRepository @Inject constructor(
    private val dao: TodoItemDao,
    private val mapper: Mapper,
) {

    suspend fun getTodoItems(): List<TodoItem>{
        return withContext(Dispatchers.IO){
            dao.getListTodoItems().map {item -> mapper.mapEntityToModel(item) }
        }
    }

    suspend fun upsertTodoItem(todoItem: TodoItem){
        withContext(Dispatchers.IO){
            dao.upsertTodoItem(
                mapper.mapModelToEntity(todoItem)
            )
            getTodoItems()
        }
    }

    suspend fun upsertList(todoItems: List<TodoItem>){
        withContext(Dispatchers.IO){
            dao.upsertList(
                todoItems.map { mapper.mapModelToEntity(it) }
            )
            getTodoItems()
        }
    }

    suspend fun getItem(id: String): TodoItem{
        return withContext(Dispatchers.IO){
            mapper.mapEntityToModel(dao.getTodoItem(id))
        }
    }

    suspend fun deleteItem(id: String){
        withContext(Dispatchers.IO){
            dao.deleteTodoItem(id)
            getTodoItems()
        }
    }
}