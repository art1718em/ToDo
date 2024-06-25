package com.example.todo.domain.model

sealed interface Importance {
    data object Low : Importance
    data object Usual : Importance
    data object High : Importance
}