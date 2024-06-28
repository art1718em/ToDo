package com.example.todo.ui.todoItemsScreen.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.todo.R
import com.example.todo.ui.design.BodyText
import com.example.todo.ui.design.LargeTitleText
import com.example.todo.ui.design.theme.blue

@Composable
fun Header(
    countOfCompletedItems: Int,
    isHiddenCompletedItems: Boolean,
    onChangeHiddenCompletedItems: (Boolean) -> Unit,
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 60.dp,
                end = 24.dp
            ),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            LargeTitleText(
                text = stringResource(id = R.string.my_things),
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                BodyText(
                    text = stringResource(id = R.string.completed) + " $countOfCompletedItems",
                    color = MaterialTheme.colorScheme.onTertiary,
                    isLineThrough = false,
                )

                IconButton(
                    onClick = {
                        onChangeHiddenCompletedItems(!isHiddenCompletedItems)
                    },
                ) {
                    Icon(
                        modifier = Modifier
                            .size(24.dp),
                        painter = painterResource(
                            id = if (!isHiddenCompletedItems) {
                                R.drawable.ic_eye_visible
                            } else {
                                R.drawable.ic_eye_invisible
                            }
                        ),
                        contentDescription = stringResource(id = R.string.icon_eye),
                        tint = MaterialTheme.colorScheme.blue,
                    )
                }
            }
        }
    }
}