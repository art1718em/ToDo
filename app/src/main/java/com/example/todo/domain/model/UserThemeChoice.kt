package com.example.todo.domain.model

sealed class UserThemeChoice(val userThemeChoiceString: String) {
    data object DarkThemeChoice : UserThemeChoice("darkThemeChoice")
    data object LightThemeChoice : UserThemeChoice("lightThemeChoice")
    data object SystemThemeChoice : UserThemeChoice("systemThemeChoice")
}