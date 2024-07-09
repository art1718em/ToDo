package com.example.todo.domain.model

import androidx.compose.runtime.Immutable

@Immutable
sealed interface Importance {
    data object Low : Importance
    data object Usual : Importance
    data object High : Importance
}