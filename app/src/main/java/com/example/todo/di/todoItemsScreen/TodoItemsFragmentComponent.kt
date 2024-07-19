package com.example.todo.di.todoItemsScreen

import com.example.todo.ui.todoItemsScreen.TodoItemsFragment
import dagger.Subcomponent

@TodoItemsFragmentScope
@Subcomponent
interface TodoItemsFragmentComponent {
    fun inject(fragment: TodoItemsFragment)
}
