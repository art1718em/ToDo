package com.example.todo.ui.todoItemsScreen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo.R
import com.example.todo.domain.model.Importance
import com.example.todo.ui.design.BodyText
import com.example.todo.ui.design.theme.ToDoTheme
import com.example.todo.ui.todoItemsScreen.state.TodoItemUiModel

@Composable
fun ElevatedLazyColumn(
    todoItems: List<TodoItemUiModel>,
    onDeleteItem: (String) -> Unit,
    onCheckedChange: (String, Boolean) -> Unit,
    onNavigateToDetails: (String?) -> Unit,
){
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 8.dp
        ),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        LazyColumn(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface),
        ) {
            items(items = todoItems, key = {it.id}) { todoItem ->
                SwipeContainer(
                    id = todoItem.id,
                    isCompleted = todoItem.isCompleted,
                    onDelete = onDeleteItem,
                    onCheckedChange = onCheckedChange
                ) {
                    TodoItemRow(
                        todoItem = todoItem,
                        onCheckedChange = onCheckedChange,
                        onNavigateToDetails = onNavigateToDetails,
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(start = 52.dp, bottom = 20.dp, top = 12.dp),
                ) {
                    TextButton(
                        onClick = { onNavigateToDetails(null) }
                    ) {
                        BodyText(
                            text = stringResource(id = R.string.text_new),
                            color = MaterialTheme.colorScheme.onTertiary,
                            isLineThrough = false,
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ElevatedLazyColumnPreview(){
    ToDoTheme {
        ElevatedLazyColumn(
            todoItems = listOf(
                TodoItemUiModel(
                    id = stringResource(id = R.string.text),
                    text = stringResource(id = R.string.text),
                    isCompleted = true,
                    deadline = stringResource(id = R.string.text),
                    importance = Importance.Usual,
                )
            ),
            onDeleteItem = {  },
            onCheckedChange = { _, _ -> },
            onNavigateToDetails = {  },
        )
    }
}