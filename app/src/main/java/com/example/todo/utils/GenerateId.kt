package com.example.todo.utils

import java.util.UUID

fun generateId(): String {
    return UUID.randomUUID().toString()
}