package com.example.todo.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Response(
    @SerialName("revision")
    val revision: Int,
    @SerialName("status")
    val status: String,
    @SerialName("list")
    val todoItemDtos: List<TodoItemDto>,
)