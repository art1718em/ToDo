package com.example.todo.ui.userThemeChoiceScreen

import com.example.todo.domain.model.UserThemeChoice
import javax.inject.Inject

class UserThemeChoicePresenter @Inject constructor(
    private val userThemeChoiceViewModel: UserThemeChoiceViewModel,
) {

    val userThemeChoice = userThemeChoiceViewModel.userThemeChoice

    fun chooseUserTheme(userThemeChoice: UserThemeChoice){
        userThemeChoiceViewModel.chooseUserTheme(userThemeChoice)
    }

    fun navigateBack(){
        userThemeChoiceViewModel.navigateBack()
    }
}