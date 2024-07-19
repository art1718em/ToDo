package com.example.todo.ui.design.theme

object ToDoThemeProvider {
    var current: ToDoThemeColors = ToDoThemeColors()
}

data class ToDoThemeColors(
    var darkTheme: Boolean = false
)