package com.example.todo.ui.userThemeChoiceScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.example.todo.MainActivity
import com.example.todo.di.userThemeChoiceScreen.UserThemeChoiceFragmentComponent
import com.example.todo.ui.design.theme.ToDoThemeWithUserChoice
import com.example.todo.ui.userThemeChoiceScreen.composables.UserThemeChoiceScreen
import javax.inject.Inject

class UserThemeChoiceFragment : Fragment() {

    private lateinit var component: UserThemeChoiceFragmentComponent

    @Inject
    lateinit var presenter: UserThemeChoicePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component = (activity as MainActivity)
            .mainActivityComponent
            .userThemeChoiceFragmentComponent()
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
                    UserThemeChoiceScreen(presenter = presenter)
                }
            }
        }
    }

}