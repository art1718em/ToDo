package com.example.todo.di.todoItemsScreen

import androidx.navigation.NavController
import com.example.todo.ui.todoItemsScreen.TodoItemsPresenter
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component
@Singleton
interface TodoItemsScreenComponent {

    fun todoItemsPresenter(): TodoItemsPresenter

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance navController: NavController): TodoItemsScreenComponent
    }
}