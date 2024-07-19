package com.example.todo.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.todo.domain.model.UserThemeChoice
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class PreferencesManager(private val dataStore: DataStore<Preferences>) {

    companion object {
        val SELECTED_OPTION_KEY = stringPreferencesKey("selected_option")
    }

    val selectedUserThemeChoice: StateFlow<UserThemeChoice> = dataStore.data
        .map { preferences ->
            when (preferences[SELECTED_OPTION_KEY]) {
                "lightThemeChoice" -> UserThemeChoice.LightThemeChoice
                "darkThemeChoice" -> UserThemeChoice.DarkThemeChoice
                else -> UserThemeChoice.SystemThemeChoice
            }
        }
        .stateIn(
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            started = SharingStarted.Eagerly,
            initialValue = UserThemeChoice.SystemThemeChoice
        )

    suspend fun saveSelectedUserThemeChoice(userThemeChoice: UserThemeChoice) {
        dataStore.edit { preferences ->
            preferences[SELECTED_OPTION_KEY] = userThemeChoice.userThemeChoiceString
        }
    }

}