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
                deadline = 1719878400000L, // 01 июл. 2024
                importance = Importance.High,
                isCompleted = false,
                dateOfCreation = 1718822400000L, // 20 июн. 2024
                dateOfChange = 1718908800000L  // 21 июн. 2024
            ),
            TodoItem(
                id = UUID.randomUUID().toString(),
                text = "Закончить отчет по проекту",
                deadline = 1720137600000L, // 03 июл. 2024
                importance = Importance.High,
                isCompleted = false,
                dateOfCreation = 1718409600000L, // 15 июн. 2024
                dateOfChange = 1718835200000L  // 19 июн. 2024
            ),
            TodoItem(
                id = UUID.randomUUID().toString(),
                text = "Записаться на прием к стоматологу",
                deadline = 1720656000000L, // 10 июл. 2024
                importance = Importance.Usual,
                isCompleted = false,
                dateOfCreation = 1719004800000L, // 22 июн. 2024
                dateOfChange = null
            ),
            TodoItem(
                id = UUID.randomUUID().toString(),
                text = "Запланировать поездку на выходные",
                deadline = null,
                importance = Importance.Usual,
                isCompleted = false,
                dateOfCreation = 1718140800000L, // 10 июн. 2024
                dateOfChange = 1718769600000L  // 18 июн. 2024
            ),
            TodoItem(
                id = UUID.randomUUID().toString(),
                text = "Оплатить коммунальные счета",
                deadline = 1720272000000L, // 05 июл. 2024
                importance = Importance.High,
                isCompleted = true,
                dateOfCreation = 1717651200000L, // 05 июн. 2024
                dateOfChange = 1720416000000L  // 07 июл. 2024
            ),
            TodoItem(
                id = UUID.randomUUID().toString(),
                text = "Прочитать новую книгу",
                deadline = null,
                importance = Importance.Low,
                isCompleted = false,
                dateOfCreation = 1718409600000L, // 15 июн. 2024
                dateOfChange = null
            ),
            TodoItem(
                id = UUID.randomUUID().toString(),
                text = "Организовать фотоальбом",
                deadline = 1721433600000L, // 20 июл. 2024
                importance = Importance.Usual,
                isCompleted = false,
                dateOfCreation = 1717286400000L, // 01 июн. 2024
                dateOfChange = 1718160000000L  // 12 июн. 2024
            ),
            TodoItem(
                id = UUID.randomUUID().toString(),
                text = "Посетить занятие по йоге",
                deadline = 1721088000000L, // 15 июл. 2024
                importance = Importance.Low,
                isCompleted = true,
                dateOfCreation = 1718140800000L, // 10 июн. 2024
                dateOfChange = 1718227200000L  // 11 июн. 2024
            ),
            TodoItem(
                id = UUID.randomUUID().toString(),
                text = "Обновить резюме",
                deadline = null,
                importance = Importance.High,
                isCompleted = false,
                dateOfCreation = 1717651200000L, // 05 июн. 2024
                dateOfChange = 1717900800000L  // 08 июн. 2024
            ),
            TodoItem(
                id = UUID.randomUUID().toString(),
                text = "Убраться в гараже",
                deadline = 1720396800000L, // 08 июл. 2024
                importance = Importance.Usual,
                isCompleted = false,
                dateOfCreation = 1718822400000L, // 20 июн. 2024
                dateOfChange = 1718908800000L  // 21 июн. 2024
            ),
            TodoItem(
                id = UUID.randomUUID().toString(),
                text = "Подготовить презентацию",
                deadline = 1719878400000L, // 01 июл. 2024
                importance = Importance.Low,
                isCompleted = false,
                dateOfCreation = 1718659200000L, // 18 июн. 2024
                dateOfChange = 1718835200000L  // 20 июн. 2024
            )
        )
    )
    val todoItems: StateFlow<List<TodoItem>> get()= _todoItems


    fun updateChecked(id: String, isCompleted: Boolean){
        _todoItems.update {
            it.map { item ->
                if (item.id == id) item.copy(isCompleted = isCompleted) else item
            }
        }
    }

    fun saveItem(todoItem: TodoItem) {
        val updatedList = _todoItems.value.map { item ->
            if (item.id == todoItem.id) {
                todoItem
            } else {
                item
            }
        }

        if (updatedList.none { it.id == todoItem.id }) {
            _todoItems.value = updatedList + todoItem
        } else {
            _todoItems.value = updatedList
        }
    }

    fun deleteItem(id: String){
        _todoItems.update { currentList ->
            currentList.filterNot { it.id == id }
        }
    }


    // TODO выкидывать Error, если не нашли id
    fun getItem(id: String): TodoItem{
        todoItems.value.forEach {
            if (it.id == id)
                return it
        }
        return TodoItem()
    }
}