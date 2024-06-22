package com.example.todo.data.repository

import com.example.todo.domain.model.Importance
import com.example.todo.domain.model.TodoItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TodoItemsRepository @Inject constructor(){

    private val _todoItems = MutableStateFlow(
        listOf(
            TodoItem(
                id = UUID.randomUUID().toString(),
                text = "Купить продукты",
                deadline = "01 июл. 2024",
                importance = Importance.High,
                isCompleted = false,
                dateOfCreation = "20 июн. 2024",
                dateOfChange = "21 июн. 2024",
            ),
            TodoItem(
                id = UUID.randomUUID().toString(),
                text = "Закончить отчет по проекту",
                deadline = "03 июл. 2024",
                importance = Importance.High,
                isCompleted = false,
                dateOfCreation = "15 июн. 2024",
                dateOfChange = "19 июн. 2024",
            ),
            TodoItem(
                id = UUID.randomUUID().toString(),
                text = "Записаться на прием к стоматологу",
                deadline = "10 июл. 2024",
                importance = Importance.Usual,
                isCompleted = false,
                dateOfCreation = "22 июн. 2024",
                dateOfChange = null
            ),
            TodoItem(
                id = UUID.randomUUID().toString(),
                text = "Запланировать поездку на выходные",
                deadline = null,
                importance = Importance.Usual,
                isCompleted = false,
                dateOfCreation = "10 июн. 2024",
                dateOfChange = "18 июн. 2024",
            ),
            TodoItem(
                id = UUID.randomUUID().toString(),
                text = "Оплатить коммунальные счета",
                deadline = "05 июл. 2024",
                importance = Importance.High,
                isCompleted = true,
                dateOfCreation = "05 июн. 2024",
                dateOfChange = "07 июл. 2024"
            ),
            TodoItem(
                id = UUID.randomUUID().toString(),
                text = "Прочитать новую книгу",
                deadline = null,
                importance = Importance.Low,
                isCompleted = false,
                dateOfCreation = "15 июн. 2024",
                dateOfChange = null
            ),
            TodoItem(
                id = UUID.randomUUID().toString(),
                text = "Организовать фотоальбом",
                deadline = "20 июл. 2024",
                importance = Importance.Usual,
                isCompleted = false,
                dateOfCreation = "01 июн. 2024",
                dateOfChange = "12 июн. 2024"
            ),
            TodoItem(
                id = UUID.randomUUID().toString(),
                text = "Посетить занятие по йоге",
                deadline = "15 июл. 2024",
                importance = Importance.Low,
                isCompleted = true,
                dateOfCreation = "10 июн. 2024",
                dateOfChange = "11 июн. 2024"
            ),
            TodoItem(
                id = UUID.randomUUID().toString(),
                text = "Обновить резюме",
                deadline = null,
                importance = Importance.High,
                isCompleted = false,
                dateOfCreation = "05 июн. 2024",
                dateOfChange = "08 июн. 2024"
            ),
            TodoItem(
                id = UUID.randomUUID().toString(),
                text = "Убраться в гараже",
                deadline = "08 июл. 2024",
                importance = Importance.Usual,
                isCompleted = false,
                dateOfCreation = "20 июн. 2024",
                dateOfChange = "21 июн. 2024"
            ),
            TodoItem(
                id = UUID.randomUUID().toString(),
                text = "Подготовить презентацию",
                deadline = "01 июл. 2024",
                importance = Importance.Low,
                isCompleted = false,
                dateOfCreation = "18 июн. 2024",
                dateOfChange = "20 июн. 2024"
            ),
        )
    )
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
            list.also { items ->
                items.forEachIndexed { index: Int, todoItem: TodoItem ->
                    if (todoItem.id == item.id) {
                        isSave = true
                        items[index] = item
                    }
                }
                if (!isSave){
                    items.add(
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