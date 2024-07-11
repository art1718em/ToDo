package com.example.todo.ui.todoItemDetailsScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import com.example.todo.MainActivity
import com.example.todo.R
import com.example.todo.di.todoItemDetailsScreen.TodoItemDetailsFragmentComponent
import com.example.todo.ui.design.theme.ToDoTheme
import com.example.todo.ui.todoItemDetailsScreen.composables.TodoItemDetailsScreen
import com.example.todo.ui.todoItemsScreen.composables.TodoItemsScreen
import javax.inject.Inject

class TodoItemDetailsFragment : Fragment() {

    private lateinit var component: TodoItemDetailsFragmentComponent
    @Inject
    lateinit var presenter: TodoItemDetailsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component = (activity as MainActivity)
            .mainActivityComponent
            .todoItemDetailsFragmentComponent()
        component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                ToDoTheme {
                    TodoItemDetailsScreen(presenter = presenter)
                }
            }
        }
    }


}