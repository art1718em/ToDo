package com.example.todo.ui.userThemeChoiceScreen.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.todo.R
import com.example.todo.domain.model.UserThemeChoice
import com.example.todo.ui.design.theme.blue

@Composable
fun UserThemeChoiceSelector(
    currentUserThemeChoice: UserThemeChoice,
    selectedUserThemeChoice: UserThemeChoice,
    onChooseItem: (UserThemeChoice) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        content = function(selectedUserThemeChoice, currentUserThemeChoice, onChooseItem),
    )
}

@Composable
private fun function(
    selectedUserThemeChoice: UserThemeChoice,
    currentUserThemeChoice: UserThemeChoice,
    onChooseItem: (UserThemeChoice) -> Unit
): @Composable() (RowScope.() -> Unit) =
    {
        RadioButton(
            colors = RadioButtonColors(
                selectedColor = MaterialTheme.colorScheme.blue,
                unselectedColor = MaterialTheme.colorScheme.outline,
                disabledSelectedColor = MaterialTheme.colorScheme.outline,
                disabledUnselectedColor = MaterialTheme.colorScheme.outline,
            ),
            selected = selectedUserThemeChoice == currentUserThemeChoice,
            onClick = { onChooseItem(currentUserThemeChoice) },
        )

        Text(
            text = stringResource(
                id = when (currentUserThemeChoice) {
                    is UserThemeChoice.DarkThemeChoice -> R.string.always_dark
                    is UserThemeChoice.LightThemeChoice -> R.string.always_light
                    is UserThemeChoice.SystemThemeChoice -> R.string.like_in_system
                }
            ),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimary,
        )
    }