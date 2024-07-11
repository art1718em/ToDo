package com.example.todo.ui.todoItemsScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import com.example.todo.MainActivity
import com.example.todo.di.todoItemsScreen.TodoItemsFragmentComponent
import com.example.todo.ui.design.theme.ToDoTheme
import com.example.todo.ui.todoItemsScreen.composables.TodoItemsScreen
import javax.inject.Inject


class TodoItemsFragment : Fragment() {

    private lateinit var component: TodoItemsFragmentComponent

    @Inject
    lateinit var presenter: TodoItemsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component = (activity as MainActivity)
            .mainActivityComponent
            .todoItemsFragmentComponent()
        component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                ToDoTheme {
                    TodoItemsScreen(presenter = presenter)
                }
            }
        }
    }

}