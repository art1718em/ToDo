package com.example.todo.ui.todoItemsScreen.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo.R
import com.example.todo.domain.model.Importance
import com.example.todo.ui.design.BodyText
import com.example.todo.ui.design.ErrorScreen
import com.example.todo.ui.design.LargeTitleText
import com.example.todo.ui.design.ProgressBar
import com.example.todo.ui.design.theme.ToDoTheme
import com.example.todo.ui.design.theme.blue
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

        is TodoItemsScreenState.Error -> ErrorScreen(
            onUpdate = presenter::loadTodoItems,
            message = todoItemsScreenState.message,
        )
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

            ElevatedLazyColumn(
                todoItems = todoItems,
                onDeleteItem = onDeleteItem,
                onCheckedChange = onCheckedChange,
                onNavigateToDetails = onNavigateToDetails,
            )
        }
    }
}

@Preview
@Composable
fun ListTodoItemsPreview(){
    ToDoTheme {
        ListTodoItems(
            todoItems = listOf(
                TodoItemUiModel(
                    id = "2334",
                    text = "Надо сделать",
                    isCompleted = true,
                    deadline = null,
                    importance = Importance.Usual,
                )
            ),
            countOfCompletedItems = 1,
            isHiddenCompletedItems = false,
            onCheckedChange = { _, _  -> },
            onChangeHiddenCompletedItems = {  },
            onDeleteItem = {  },
            onNavigateToDetails = {  },
        )
    }
}

@Preview
@Composable
fun ListTodoItemsDarkThemePreview(){
    ToDoTheme(
        darkTheme = true
    ) {
        ListTodoItems(
            todoItems = listOf(
                TodoItemUiModel(
                    id = "2334",
                    text = "Надо сделать",
                    isCompleted = true,
                    deadline = null,
                    importance = Importance.Usual,
                )
            ),
            countOfCompletedItems = 1,
            isHiddenCompletedItems = false,
            onCheckedChange = { _, _  -> },
            onChangeHiddenCompletedItems = {  },
            onDeleteItem = {  },
            onNavigateToDetails = {  },
        )
    }
}