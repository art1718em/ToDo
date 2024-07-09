package com.example.todo.di.todoItemsScreen

import androidx.navigation.NavController
import com.example.todo.data.repository.TodoItemsRepository
import com.example.todo.di.network.NetworkModule
import com.example.todo.ui.todoItemsScreen.TodoItemsPresenter
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface TodoItemsScreenComponent {
    fun todoItemsPresenter(): TodoItemsPresenter

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance navController: NavController,
            @BindsInstance todoItemsRepository: TodoItemsRepository
        ): TodoItemsScreenComponent
    }
}
