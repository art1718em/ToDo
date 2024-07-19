package com.example.todo

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import com.example.todo.app.App
import com.example.todo.data.network.WorkScheduler
import com.example.todo.di.activity.MainActivityComponent
import com.example.todo.di.activity.MainActivityModule
import com.example.todo.di.activity.MainActivityScope
import com.example.todo.ui.todoItemsScreen.TodoItemsFragment
import javax.inject.Inject

class MainActivity: FragmentActivity() {

    lateinit var mainActivityComponent: MainActivityComponent
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        mainActivityComponent = (applicationContext as App).appComponent.mainActivityComponent(
            MainActivityModule(this)
        )
        mainActivityComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        WorkScheduler.schedulerWork(this)


        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(R.id.fragment_container, TodoItemsFragment())
            }
        }
    }
}
