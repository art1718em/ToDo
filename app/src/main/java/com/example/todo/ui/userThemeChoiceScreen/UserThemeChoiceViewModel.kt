package com.example.todo.ui.userThemeChoiceScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.data.preferences.PreferencesManager
import com.example.todo.domain.model.UserThemeChoice
import com.example.todo.navigation.FragmentNavigation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserThemeChoiceViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager,
    private val navigation: FragmentNavigation,
): ViewModel() {

    private val _userThemeChoice = MutableStateFlow<UserThemeChoice>(UserThemeChoice.SystemThemeChoice)
    val userThemeChoice = _userThemeChoice.asStateFlow()

    init {
        collectUserThemeChoice()
    }

    fun chooseUserTheme(userThemeChoice: UserThemeChoice){
        viewModelScope.launch(Dispatchers.Default) {
            preferencesManager.saveSelectedUserThemeChoice(userThemeChoice)
        }
    }

    fun navigateBack(){
        navigation.navigateBack()
    }

    private fun collectUserThemeChoice(){
        viewModelScope.launch {
            preferencesManager.selectedUserThemeChoice.collect{
                _userThemeChoice.value = it
            }
        }
    }
}