package com.example.todo.di.app

import android.content.Context
import com.example.todo.di.activity.MainActivityComponent
import com.example.todo.di.activity.MainActivityModule
import com.example.todo.di.network.NetworkModule
import dagger.BindsInstance
import dagger.Component

@Component(modules = [NetworkModule::class])
@AppScope
interface AppComponent {
    fun mainActivityComponent(activityModule: MainActivityModule): MainActivityComponent
    
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context) : AppComponent
    }
}