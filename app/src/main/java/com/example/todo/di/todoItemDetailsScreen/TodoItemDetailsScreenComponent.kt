package com.example.todo.di.todoItemDetailsScreen

import androidx.navigation.NavController
import com.example.todo.data.repository.TodoItemsRepository
import com.example.todo.di.app.AppComponent
import com.example.todo.ui.todoItemDetailsScreen.TodoItemDetailsPresenter
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component
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