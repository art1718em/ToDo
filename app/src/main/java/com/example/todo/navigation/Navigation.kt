package com.example.todo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todo.di.todoItemsScreen.DaggerTodoItemsScreenComponent
import com.example.todo.ui.todoItemsScreen.composables.TodoItemsScreen

@Composable
fun Navigation(){

    val navController = rememberNavController()

    val todoItemsComponent = remember {
        DaggerTodoItemsScreenComponent.factory().create(navController)
    }

    NavHost(
        navController = navController,
        startDestination = Screen.TodoItemsScreen.route,
    ){
        composable(
            route = Screen.TodoItemsScreen.route,
        ){
            val presenter = remember {
                todoItemsComponent.todoItemsPresenter()
            }
            TodoItemsScreen(
                presenter = presenter,
            )
        }
    }

}