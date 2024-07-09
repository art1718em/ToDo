package com.example.todo.utils

import io.ktor.client.plugins.ResponseException

inline fun<T> safeTry(run: () -> Result<T>): Result<T> = try {
    run()
} catch (e: ResponseException) {
    Result.failure(Exception(e.response.status.description))
} catch (e: Exception) {
    Result.failure(e)
}