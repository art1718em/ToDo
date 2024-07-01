package com.example.todo.ui.todoItemsScreen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.todo.R
import com.example.todo.domain.model.Importance
import com.example.todo.ui.design.theme.gray
import com.example.todo.ui.design.theme.green
import com.example.todo.ui.design.theme.pink
import com.example.todo.ui.design.theme.red
import com.example.todo.ui.design.theme.white
import com.example.todo.ui.todoItemsScreen.state.TodoItemUiModel

@Composable
fun TodoItemRow(
    todoItem: TodoItemUiModel,
    onCheckedChange: (String, Boolean) -> Unit,
    onNavigateToDetails: (String?) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Checkbox(
            modifier = Modifier
                .padding(start = 4.dp)
                .clip(RoundedCornerShape(2.dp)),
            checked = todoItem.isCompleted,
            onCheckedChange = { isChecked ->
                onCheckedChange(todoItem.id, isChecked)
            },
            colors = CheckboxColors(
                checkedCheckmarkColor = MaterialTheme.colorScheme.white,
                checkedBoxColor = MaterialTheme.colorScheme.green,
                checkedBorderColor = MaterialTheme.colorScheme.green,
                uncheckedCheckmarkColor = MaterialTheme.colorScheme.white,
                uncheckedBoxColor = if (todoItem.importance is Importance.High) {
                    MaterialTheme.colorScheme.pink
                } else {
                    MaterialTheme.colorScheme.surface
                },
                uncheckedBorderColor = if (todoItem.importance is Importance.High) {
                    MaterialTheme.colorScheme.red
                } else {
                    MaterialTheme.colorScheme.outline
                },
                disabledBorderColor = MaterialTheme.colorScheme.white,
                disabledIndeterminateBorderColor = MaterialTheme.colorScheme.white,
                disabledCheckedBoxColor = MaterialTheme.colorScheme.white,
                disabledUncheckedBoxColor = MaterialTheme.colorScheme.white,
                disabledUncheckedBorderColor = MaterialTheme.colorScheme.white,
                disabledIndeterminateBoxColor = MaterialTheme.colorScheme.white,
            )
        )
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(vertical = 12.dp),
        ) {
            when (todoItem.importance) {
                is Importance.High -> Icon(
                    modifier = Modifier.padding(top = 2.dp),
                    painter = painterResource(id = R.drawable.ic_high_importance),
                    contentDescription = stringResource(id = R.string.icon_high_importance),
                    tint = MaterialTheme.colorScheme.red,
                )

                is Importance.Low -> Icon(
                    modifier = Modifier.padding(top = 2.dp),
                    painter = painterResource(id = R.drawable.ic_low_importance),
                    contentDescription = stringResource(id = R.string.icon_low_importance),
                    tint = MaterialTheme.colorScheme.gray,
                )

                is Importance.Usual -> {}
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 4.dp),
            ) {
                Text(
                    text = todoItem.text,
                    style = if (todoItem.isCompleted) MaterialTheme.typography.bodySmall else MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                )

                Text(
                    text = todoItem.deadline ?: "",
                    color = MaterialTheme.colorScheme.onTertiary,
                    style = MaterialTheme.typography.headlineMedium,
                )
            }
        }
        IconButton(
            modifier = Modifier
                .padding(
                    end = 16.dp,
                    bottom = 12.dp,
                    start = 12.dp,
                ),
            onClick = {
                onNavigateToDetails(todoItem.id)
            },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_info),
                contentDescription = stringResource(id = R.string.icon_information),
                tint = MaterialTheme.colorScheme.onTertiary,
            )
        }
    }
}