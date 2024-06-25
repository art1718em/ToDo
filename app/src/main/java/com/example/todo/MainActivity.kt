package com.example.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.todo.app.App
import com.example.todo.navigation.Navigation
import com.example.todo.ui.design.theme.ToDoTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as App).appComponent.inject(this)

        setContent {
            ToDoTheme {
                Navigation()
            }
        }
    }
}
