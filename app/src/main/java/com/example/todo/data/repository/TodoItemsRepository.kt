package com.example.todo.data.repository

import android.util.Log
import com.example.todo.domain.model.Importance
import com.example.todo.domain.model.TodoItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TodoItemsRepository @Inject constructor(){

    private val _todoItems = MutableStateFlow(listOf(
        TodoItem(
            "0", "Купить что-то", "24.07.24", Importance.Usual, false, "", ""
        ),
        TodoItem(
            "1", "Купить что-то", null, Importance.Low, true, "", ""
        ),
        TodoItem(
            "2", "Купить что-то, где-то, зачем-то, но зачем не очень понятно, но точно чтобы показать как обрабатывается", null, Importance.High, false, "", ""
        )
    ))
    val todoItems :StateFlow<List<TodoItem>> get()= _todoItems


    fun updateChecked(id: String, isCompleted: Boolean){
        _todoItems.update {
            val list = it.toMutableList()
            list.forEachIndexed { index: Int, todoItem: TodoItem ->
                if (todoItem.id == id) {
                    list[index] = todoItem.copy(
                        isCompleted = isCompleted,
                    )
                }
            }
            list
        }
    }

    fun saveItem(item:TodoItem){
        var isSave = false
        _todoItems.update {
            val list = it.toMutableList()
            list.also {
                it.forEachIndexed { index: Int, todoItem: TodoItem ->
                    if (todoItem.id == item.id) {
                        isSave = true
                        it[index] = item
                    }
                }
                if (!isSave){
                    it.add(
                        item.copy(
                            id = it.size.toString()
                        )
                    )
                }
            }
        }
    }

    fun deleteItem(id: String){
        _todoItems.update { currentList ->
            currentList.filterNot { it.id == id }
        }
    }


    fun getItem(id: String): TodoItem{
        todoItems.value.forEach {
            if (it.id == id)
                return it
        }
        return TodoItem()
    }
}