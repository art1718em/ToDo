package com.example.todo.di.app

import com.example.todo.MainActivity
import com.example.todo.data.repository.TodoItemsRepository
import dagger.Component
import javax.inject.Singleton

@Component
@Singleton
interface AppComponent {
    fun inject(activity: MainActivity)
    fun todoItemsRepository(): TodoItemsRepository
}