package com.example.todo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todo.app.App
import com.example.todo.di.todoItemDetailsScreen.DaggerTodoItemDetailsScreenComponent
import com.example.todo.di.todoItemsScreen.DaggerTodoItemsScreenComponent
import com.example.todo.ui.todoItemDetailsScreen.composables.TodoItemDetailsScreen
import com.example.todo.ui.todoItemsScreen.composables.TodoItemsScreen

@Composable
fun Navigation(){

    val navController = rememberNavController()

    val context = LocalContext.current

    val appComponent = (context.applicationContext as App).appComponent

    val todoItemsComponent = remember {
        DaggerTodoItemsScreenComponent.factory().create(navController, appComponent.todoItemsRepository())
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
        composable(
            route = "${Screen.TodoItemDetailsScreen.route}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType; nullable = true })
        ){
            val todoItemDetailsScreenComponent = remember {
                DaggerTodoItemDetailsScreenComponent.factory().create(navController, appComponent.todoItemsRepository())
            }

            val presenter = remember {
                todoItemDetailsScreenComponent.todoItemDetailsPresenter()
            }
            
            TodoItemDetailsScreen(presenter = presenter)
        }
    }

}