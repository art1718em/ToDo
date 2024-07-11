package com.example.todo.app

import android.app.Application
import com.example.todo.di.app.AppComponent
import com.example.todo.di.app.DaggerAppComponent

class App: Application() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
    }

}