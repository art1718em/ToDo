package com.example.todo.di.app

import com.example.todo.MainActivity
import dagger.Component
import javax.inject.Singleton

@Component
@Singleton
interface AppComponent {
    fun inject(activity: MainActivity)
}