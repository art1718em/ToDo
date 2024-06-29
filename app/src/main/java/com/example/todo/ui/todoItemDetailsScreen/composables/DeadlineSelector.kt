package com.example.todo.ui.todoItemDetailsScreen.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo.R
import com.example.todo.ui.design.theme.ToDoTheme
import com.example.todo.ui.design.theme.blue
import com.example.todo.ui.design.theme.switchThumbUnchecked
import com.example.todo.ui.design.theme.switchTrackChecked
import com.example.todo.ui.design.theme.switchTrackUnchecked

@Composable
fun DeadlineSelector(
    deadline: String?,
    switchState: Boolean,
    onUpdateDeadline: (Long?) -> Unit,
    onSwitchToFalse: () -> Unit,
    onSwitchToTrue: () -> Unit,
    onOpenDateDialog: () -> Unit,
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 16.dp,
                bottom = 40.dp,
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = if (switchState){
                Modifier
                    .clickable {
                        onOpenDateDialog()
                    }
            } else {
                Modifier
            },
        ) {
            Text(
                text = stringResource(id = R.string.make_it_to),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary,
            )

            Text(
                text = deadline ?: "",
                color = MaterialTheme.colorScheme.blue,
                style = MaterialTheme.typography.headlineMedium,
            )
        }
        Switch(
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colorScheme.blue,
                checkedTrackColor = MaterialTheme.colorScheme.switchTrackChecked,
                uncheckedThumbColor = MaterialTheme.colorScheme.switchThumbUnchecked,
                uncheckedTrackColor = MaterialTheme.colorScheme.switchTrackUnchecked,
                ),
            checked = (deadline != null || switchState),
            onCheckedChange = {
                if (switchState) {
                    onSwitchToFalse()
                    onUpdateDeadline(null)
                } else {
                    onSwitchToTrue()
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DeadlineSelectorPreview(){
    ToDoTheme {
        DeadlineSelector(
            deadline = "22 июн. 2024",
            switchState = true,
            onUpdateDeadline = {  },
            onSwitchToFalse = {  },
            onSwitchToTrue = {  },
            onOpenDateDialog = {  }
        )
    }
}