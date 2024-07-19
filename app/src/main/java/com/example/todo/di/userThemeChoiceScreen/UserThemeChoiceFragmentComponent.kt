package com.example.todo.di.userThemeChoiceScreen

import com.example.todo.ui.userThemeChoiceScreen.UserThemeChoiceFragment
import dagger.Subcomponent

@UserThemeChoiceFragmentScope
@Subcomponent
interface UserThemeChoiceFragmentComponent {
    fun inject(fragment: UserThemeChoiceFragment)
}