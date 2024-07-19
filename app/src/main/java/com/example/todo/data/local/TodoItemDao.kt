package com.example.todo.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.todo.di.activity.MainActivityScope
import com.example.todo.domain.model.TodoItem
import kotlinx.coroutines.flow.Flow

@Dao
@MainActivityScope
interface TodoItemDao {

    @Upsert
    suspend fun upsertTodoItem(todoItemEntity: TodoItemEntity)

    @Upsert
    suspend fun upsertList(item : List<TodoItemEntity>)

    @Query("UPDATE todoItems SET isCompleted= :isCompleted WHERE id= :id")
    suspend fun updateCompleted(id: String, isCompleted: Boolean)

    @Query("DELETE FROM todoItems WHERE id in (:id)")
    suspend fun deleteTodoItem(id: String)

    @Query("SELECT * FROM todoItems")
    fun getListTodoItems(): List<TodoItemEntity>

    @Query("SELECT * FROM todoItems WHERE id= :id")
    fun getTodoItem(id: String): TodoItemEntity

}