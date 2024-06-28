package com.example.todo.ui.todoItemDetailsScreen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.todo.R
import com.example.todo.domain.model.Importance
import com.example.todo.ui.design.BodyText
import com.example.todo.ui.design.SubheadText
import com.example.todo.ui.design.theme.ToDoTheme
import com.example.todo.ui.design.theme.elevated
import com.example.todo.ui.design.theme.red
import com.example.todo.ui.todoItemDetailsScreen.state.TodoItemDetailsUiModel

@Composable
fun ImportanceSelector(
    expanded: Boolean,
    importance: Importance,
    onOpenDateDialog: () -> Unit,
    onCloseDateDialog: () -> Unit,
    onUpdateImportance: (Importance) -> Unit,
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
    ) {
        Column(
            modifier = Modifier
                .clickable { onOpenDateDialog() }
        ) {
            BodyText(
                text = stringResource(id = R.string.importance),
                isLineThrough = false,
            )

            SubheadText(
                text = stringResource(
                    id = when (importance) {
                        is Importance.Usual -> R.string.no
                        is Importance.Low -> R.string.low
                        is Importance.High -> R.string.high
                    }
                ),
                color = when (importance) {
                    is Importance.High -> MaterialTheme.colorScheme.red
                    else -> MaterialTheme.colorScheme.onTertiary
                },
            )
        }
    }
    DropdownMenu(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.elevated),
        offset = DpOffset(x = 0.dp, y = 4.dp),
        expanded = expanded,
        onDismissRequest = { onCloseDateDialog() }
    ) {
        DropdownMenuItem(
            text = { Text(text = stringResource(id = R.string.no)) },
            onClick = {
                onCloseDateDialog()
                onUpdateImportance(Importance.Usual)
            },
            modifier = Modifier.padding(2.dp),
        )
        DropdownMenuItem(
            text = { Text(text = stringResource(id = R.string.low)) },
            onClick = {
                onCloseDateDialog()
                onUpdateImportance(Importance.Low)
            },
            modifier = Modifier.padding(2.dp)
        )
        DropdownMenuItem(
            text = {
                Text(
                    text = stringResource(id = R.string.high),
                    color = MaterialTheme.colorScheme.red,
                )
            },
            onClick = {
                onCloseDateDialog()
                onUpdateImportance(Importance.High)
            },
            modifier = Modifier.padding(2.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ImportanceSelectorPreview(){
    ToDoTheme {
        ImportanceSelector(
            expanded = false,
            importance = Importance.Usual,
            onUpdateImportance = {  },
            onOpenDateDialog = {  },
            onCloseDateDialog = {  },
        )
    }
}