package com.example.todo.di.todoItemDetailsScreen

import androidx.navigation.NavController
import com.example.todo.data.repository.TodoItemsRepository
import com.example.todo.di.network.NetworkModule
import com.example.todo.ui.todoItemDetailsScreen.TodoItemDetailsPresenter
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface TodoItemDetailsScreenComponent {
    fun todoItemDetailsPresenter(): TodoItemDetailsPresenter

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance navController: NavController,
            @BindsInstance todoItemsRepository: TodoItemsRepository,
        ): TodoItemDetailsScreenComponent
    }
}