package com.example.todo.di.todoItemsScreen

import androidx.navigation.NavController
import com.example.todo.data.repository.TodoItemsRepository
import com.example.todo.di.network.NetworkModule
import com.example.todo.ui.todoItemDetailsScreen.TodoItemDetailsPresenter
import com.example.todo.ui.todoItemsScreen.TodoItemsFragment
import com.example.todo.ui.todoItemsScreen.TodoItemsPresenter
import dagger.BindsInstance
import dagger.Component
import dagger.Subcomponent
import javax.inject.Singleton

@TodoItemsFragmentScope
@Subcomponent
interface TodoItemsFragmentComponent {
    fun inject(fragment: TodoItemsFragment)
}
