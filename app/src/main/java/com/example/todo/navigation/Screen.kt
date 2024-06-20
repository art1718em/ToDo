package com.example.todo.navigation

sealed class Screen(val route: String) {
    data object TodoItemsScreen : Screen("todo_items_screen")
}