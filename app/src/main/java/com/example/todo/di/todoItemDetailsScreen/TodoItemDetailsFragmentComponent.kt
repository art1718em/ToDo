package com.example.todo.di.todoItemDetailsScreen

import androidx.navigation.NavController
import com.example.todo.data.repository.TodoItemsRepository
import com.example.todo.ui.todoItemDetailsScreen.TodoItemDetailsFragment
import com.example.todo.ui.todoItemDetailsScreen.TodoItemDetailsPresenter
import com.example.todo.ui.todoItemsScreen.TodoItemsFragment
import dagger.BindsInstance
import dagger.Subcomponent

@TodoItemDetailsFragmentScope
@Subcomponent
interface TodoItemDetailsFragmentComponent {
    fun inject(fragment: TodoItemDetailsFragment)
}