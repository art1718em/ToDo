package com.example.todo.ui.todoItemDetailsScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.example.todo.MainActivity
import com.example.todo.di.todoItemDetailsScreen.TodoItemDetailsFragmentComponent
import com.example.todo.ui.design.theme.ToDoThemeWithUserChoice
import com.example.todo.ui.todoItemDetailsScreen.composables.TodoItemDetailsScreen
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
                val userThemeChoice = presenter.userThemeChoice.collectAsState().value
                ToDoThemeWithUserChoice(userThemeChoice = userThemeChoice) {
                    TodoItemDetailsScreen(presenter = presenter)
                }
            }
        }
    }
}