package com.example.todo.di.appInformationScreen

import com.example.todo.di.app.AppScope
import com.example.todo.ui.appInformationScreen.AppInformationFragment
import com.example.todo.ui.userThemeChoiceScreen.UserThemeChoiceFragment
import dagger.Subcomponent

@AppInformationFragmentScope
@Subcomponent
interface AppInformationFragmentComponent {
    fun inject(fragment: AppInformationFragment)
}