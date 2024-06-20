package com.example.todo.app

import android.app.Application
import com.example.todo.di.app.AppComponent
import com.example.todo.di.app.DaggerAppComponent

class App: Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.create()
    }

}