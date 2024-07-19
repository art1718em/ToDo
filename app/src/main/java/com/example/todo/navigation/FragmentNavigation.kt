package com.example.todo.navigation

import androidx.fragment.app.commit
import com.example.todo.MainActivity
import com.example.todo.R
import com.example.todo.di.activity.MainActivityScope
import com.example.todo.ui.todoItemDetailsScreen.TodoItemDetailsFragment
import com.example.todo.ui.userThemeChoiceScreen.UserThemeChoiceFragment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Provider

@MainActivityScope
class FragmentNavigation @Inject constructor(
    private val activity: Provider<MainActivity>,
) {

    private val _todoItemId = MutableStateFlow<String?>(null)
    val todoItemId = _todoItemId.asStateFlow()

    private val fragmentManager
        get() = activity.get().supportFragmentManager.takeIf { it.isDestroyed.not() }

    fun navigateToTodoItemDetailsFragment(id: String?) {
        _todoItemId.value = id
        fragmentManager?.commit {
            replace(R.id.fragment_container, TodoItemDetailsFragment())
            addToBackStack(null)
        }
    }

    fun navigateToUserThemeChoiceFragment() {
        fragmentManager?.commit {
            replace(R.id.fragment_container, UserThemeChoiceFragment())
            addToBackStack(null)
        }
    }

    fun navigateBack() {
        fragmentManager?.popBackStack()
    }
}