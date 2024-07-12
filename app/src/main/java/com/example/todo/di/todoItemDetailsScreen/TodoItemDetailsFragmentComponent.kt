package com.example.todo.di.todoItemDetailsScreen

import com.example.todo.ui.todoItemDetailsScreen.TodoItemDetailsFragment
import dagger.Subcomponent

@TodoItemDetailsFragmentScope
@Subcomponent
interface TodoItemDetailsFragmentComponent {
    fun inject(fragment: TodoItemDetailsFragment)
}