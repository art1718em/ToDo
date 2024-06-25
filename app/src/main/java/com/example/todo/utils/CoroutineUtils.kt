package com.example.todo.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun <T> Flow<T>.collectIn(scope: CoroutineScope, action: (T) -> Unit){
    scope.launch {
        collect(action)
    }
}