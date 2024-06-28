package com.example.todo.ui.todoItemsScreen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.todo.R
import com.example.todo.domain.model.Importance
import com.example.todo.ui.design.BodyText
import com.example.todo.ui.design.LargeTitleText
import com.example.todo.ui.design.ProgressBar
import com.example.todo.ui.design.SubheadText
import com.example.todo.ui.design.SwipeContainer
import com.example.todo.ui.design.theme.blue
import com.example.todo.ui.design.theme.gray
import com.example.todo.ui.design.theme.green
import com.example.todo.ui.design.theme.pink
import com.example.todo.ui.design.theme.red
import com.example.todo.ui.design.theme.white
import com.example.todo.ui.todoItemsScreen.TodoItemsPresenter
import com.example.todo.ui.todoItemsScreen.state.TodoItemUiModel
import com.example.todo.ui.todoItemsScreen.state.TodoItemsScreenState

@Composable
fun TodoItemsScreen(
    presenter: TodoItemsPresenter,
) {

    val todoItemsScreenState = presenter.todoItemsScreenUiState.collectAsState().value
    when (todoItemsScreenState) {
        is TodoItemsScreenState.Loading -> ProgressBar()
        is TodoItemsScreenState.Success -> ListTodoItems(
            todoItems = todoItemsScreenState.todoItems,
            countOfCompletedItems = todoItemsScreenState.countOfCompletedItems,
            isHiddenCompletedItems = todoItemsScreenState.isHiddenCompletedItems,
            onCheckedChange = presenter::updateIsCompleted,
            onChangeHiddenCompletedItems = presenter::changeHiddenCompletedItems,
            onNavigateToDetails = presenter::navigateToItemDetails,
            onDeleteItem = presenter::deleteItem,
        )

        is TodoItemsScreenState.Error -> {}
    }
}

@Composable
fun ListTodoItems(
    todoItems: List<TodoItemUiModel>,
    countOfCompletedItems: Int,
    isHiddenCompletedItems: Boolean,
    onCheckedChange: (String, Boolean) -> Unit,
    onChangeHiddenCompletedItems: (Boolean) -> Unit,
    onDeleteItem: (String) -> Unit,
    onNavigateToDetails: (String?) -> Unit,
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.blue,
                contentColor = MaterialTheme.colorScheme.white,
                modifier = Modifier
                    .padding(bottom = 16.dp, end = 8.dp)
                    .size(56.dp),
                onClick = {
                    onNavigateToDetails(null)
                },
                shape = CircleShape,
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(id = R.string.icon_add),
                )
            }
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            Spacer(
                modifier = Modifier
                    .height(60.dp),
            )

            Header(
                countOfCompletedItems = countOfCompletedItems,
                isHiddenCompletedItems = isHiddenCompletedItems,
                onChangeHiddenCompletedItems = onChangeHiddenCompletedItems,
            )

            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues)
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
                        SwipeContainer(todoItemUiModel = todoItem, onDelete = onDeleteItem, onCheckedChange = onCheckedChange) {
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
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

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
                BodyText(
                    text = todoItem.text,
                    maxLines = 3,
                    textStyle = TextStyle(
                        textDecoration = if (todoItem.isCompleted) {
                            TextDecoration.LineThrough
                        } else {
                            TextDecoration.None
                        }
                    ),
                )

                SubheadText(
                    text = todoItem.deadline ?: "",
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
