package com.example.todo.di.app

import com.example.todo.MainActivity
import com.example.todo.data.repository.TodoItemsRepository
import com.example.todo.di.activity.MainActivityComponent
import com.example.todo.di.activity.MainActivityModule
import com.example.todo.di.network.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Component(modules = [NetworkModule::class])
@AppScope
interface AppComponent {
    fun mainActivityComponent(activityModule: MainActivityModule): MainActivityComponent
}