package com.example.todo.data.repository

import com.example.todo.domain.model.Importance
import com.example.todo.domain.model.TodoItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TodoItemsRepository @Inject constructor(){

    private val todoItems = mutableListOf(
        TodoItem(
            "1", "Купить что-то", "24.07.24", Importance.Usual, false, "", ""
        ),
        TodoItem(
            "2", "Купить что-то", "", Importance.Low, true, "", ""
        ),
        TodoItem(
            "3", "Купить что-то, где-то, зачем-то, но зачем не очень понятно, но точно чтобы показать как обрабатывается", "", Importance.High, false, "", ""
        )
    )

    fun getTodoItems(): Result<List<TodoItem>>{
        return Result.success(todoItems)
    }

    fun updateChecked(id: String, isCompleted: Boolean){
        for (i in todoItems.indices ){
            if (todoItems[i].id == id){
                todoItems[i] = todoItems[i].copy(
                    isCompleted = isCompleted
                )
            }
        }
    }
}