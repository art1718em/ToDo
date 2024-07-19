package com.example.todo.ui.userThemeChoiceScreen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.todo.R
import com.example.todo.domain.model.UserThemeChoice
import com.example.todo.ui.userThemeChoiceScreen.UserThemeChoicePresenter

@Composable
fun UserThemeChoiceScreen(
    presenter: UserThemeChoicePresenter,
) {
    val userThemeChoice = presenter.userThemeChoice.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(
                horizontal = 8.dp,
                vertical = 16.dp,
            ),
    ) {

         Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = presenter::navigateBack,
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.icon_arrow_back),
                    tint = MaterialTheme.colorScheme.onPrimary,
                )
            }

            Box(modifier = Modifier.weight(1f)) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = stringResource(id = R.string.choose_theme),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            }
        }


        UserThemeChoiceSelector(
            currentUserThemeChoice = UserThemeChoice.LightThemeChoice,
            selectedUserThemeChoice = userThemeChoice,
            onChooseItem = presenter::chooseUserTheme,
        )

        UserThemeChoiceSelector(
            currentUserThemeChoice = UserThemeChoice.DarkThemeChoice,
            selectedUserThemeChoice = userThemeChoice,
            onChooseItem = presenter::chooseUserTheme,
        )

        UserThemeChoiceSelector(
            currentUserThemeChoice = UserThemeChoice.SystemThemeChoice,
            selectedUserThemeChoice = userThemeChoice,
            onChooseItem = presenter::chooseUserTheme,
        )

    }
}