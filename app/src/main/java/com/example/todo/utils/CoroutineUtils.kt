package com.example.todo.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun <T> Flow<T>.collectIn(scope: CoroutineScope, action: (T) -> Unit): Job{
    return scope.launch {
        collect(action)
    }
}