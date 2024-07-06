package com.example.todo.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TodoItemPost(
    @SerialName("status")
    val status : String,
    @SerialName("element")
    val todoItemDto : TodoItemDto
)