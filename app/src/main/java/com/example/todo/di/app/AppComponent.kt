package com.example.todo.di.app

import com.example.todo.MainActivity
import com.example.todo.data.repository.TodoItemsRepository
import com.example.todo.di.network.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Component(modules = [NetworkModule::class])
@Singleton
interface AppComponent {
    fun todoItemsRepository(): TodoItemsRepository
}