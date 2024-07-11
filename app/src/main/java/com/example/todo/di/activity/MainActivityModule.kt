package com.example.todo.di.activity

import com.example.todo.MainActivity
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule(private val activity: MainActivity) {

    @Provides
    fun provideMainActivity(): MainActivity {
        return activity
    }
}