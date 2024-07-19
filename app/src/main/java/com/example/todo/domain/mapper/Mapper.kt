package com.example.todo.domain.mapper

import com.example.todo.data.local.TodoItemEntity
import com.example.todo.data.network.dto.TodoItemPost
import com.example.todo.data.network.dto.TodoItemDto
import com.example.todo.domain.model.Importance
import com.example.todo.domain.model.TodoItem
import javax.inject.Inject

class Mapper @Inject constructor() {

    fun mapModelToPost(todoItem: TodoItem): TodoItemPost {
        return TodoItemPost(
            status = "ok",
            todoItemDto = mapModelToDto(todoItem)
        )
    }

    fun mapModelToDto(todoItem: TodoItem): TodoItemDto {
        return TodoItemDto(
            id = todoItem.id,
            text = todoItem.text,
            importance = todoItem.importance.toStringImportance(),
            color = "#FFFFFF",
            deadLine = todoItem.deadline,
            isCompleted = todoItem.isCompleted,
            dateOfCreation = todoItem.dateOfCreation,
            dateOfChange = todoItem.dateOfChange,
            user = "1",
        )
    }

    fun mapDtoToModel(todoItemDto: TodoItemDto): TodoItem {
        return TodoItem(
            id = todoItemDto.id,
            text = todoItemDto.text,
            importance = todoItemDto.importance.toImportance(),
            deadline = todoItemDto.deadLine,
            isCompleted = todoItemDto.isCompleted,
            dateOfCreation = todoItemDto.dateOfCreation,
            dateOfChange = todoItemDto.dateOfChange,
        )
    }

    fun mapEntityToModel(todoItemEntity: TodoItemEntity): TodoItem {
        return TodoItem(
            id = todoItemEntity.id,
            text = todoItemEntity.text,
            importance = todoItemEntity.importance.toImportance(),
            deadline = todoItemEntity.deadline,
            isCompleted = todoItemEntity.isCompleted,
            dateOfCreation = todoItemEntity.dateOfCreation,
            dateOfChange = todoItemEntity.dateOfChange,
        )
    }

    fun mapModelToEntity(todoItem: TodoItem): TodoItemEntity{
        return TodoItemEntity(
            id = todoItem.id,
            text = todoItem.text,
            importance = todoItem.importance.toStringImportance(),
            deadline = todoItem.deadline,
            isCompleted = todoItem.isCompleted,
            dateOfCreation = todoItem.dateOfCreation,
            dateOfChange = todoItem.dateOfChange,
        )
    }

}

private fun String.toImportance(): Importance = when (this.lowercase()) {
    "low" -> Importance.Low
    "basic" -> Importance.Usual
    "important" -> Importance.High
    else -> throw IllegalArgumentException("Unknown importance level: $this")
}

private fun Importance.toStringImportance(): String = when (this) {
    is Importance.Low -> "low"
    is Importance.Usual -> "basic"
    is Importance.High -> "important"
}