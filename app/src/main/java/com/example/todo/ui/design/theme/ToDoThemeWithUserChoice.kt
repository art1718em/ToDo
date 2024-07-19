package com.example.todo.ui.design.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import com.example.todo.domain.model.UserThemeChoice

@Composable
fun ToDoThemeWithUserChoice(
    userThemeChoice: UserThemeChoice,
    content: @Composable () -> Unit,
) {
    ToDoTheme(
        darkTheme = when (userThemeChoice) {
            is UserThemeChoice.LightThemeChoice -> false
            is UserThemeChoice.DarkThemeChoice -> true
            is UserThemeChoice.SystemThemeChoice -> isSystemInDarkTheme()
        },
        content = content,
    )
}