package com.example.todo.di.activity

import com.example.todo.MainActivity
import com.example.todo.di.todoItemDetailsScreen.TodoItemDetailsFragmentComponent
import com.example.todo.di.todoItemsScreen.TodoItemsFragmentComponent
import dagger.Subcomponent

@Subcomponent(modules = [MainActivityModule::class])
@MainActivityScope
interface MainActivityComponent {
    fun mainActivity(): MainActivity
    fun inject(activity: MainActivity)
    fun todoItemsFragmentComponent(): TodoItemsFragmentComponent
    fun todoItemDetailsFragmentComponent(): TodoItemDetailsFragmentComponent
}