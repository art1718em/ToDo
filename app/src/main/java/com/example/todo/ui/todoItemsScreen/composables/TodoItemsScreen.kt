package com.example.todo.ui.todoItemsScreen.composables

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo.R
import com.example.todo.domain.model.Importance
import com.example.todo.ui.design.ErrorScreen
import com.example.todo.ui.design.ProgressBar
import com.example.todo.ui.design.theme.ToDoTheme
import com.example.todo.ui.design.theme.blue
import com.example.todo.ui.design.theme.white
import com.example.todo.ui.todoItemsScreen.TodoItemsPresenter
import com.example.todo.ui.todoItemsScreen.state.TodoItemUiModel
import com.example.todo.ui.todoItemsScreen.state.TodoItemsScreenState
import com.example.todo.ui.todoItemsScreen.state.TodoItemsScreenUiEffects
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun TodoItemsScreen(
    presenter: TodoItemsPresenter,
) {

    val todoItemsScreenState = presenter.todoItemsScreenUiState.collectAsState().value

    ShowUiEffectIfNeeded(todoItemsScreenUiEffects = presenter.todoItemsScreenUiEffects)

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
            onRefresh = presenter::loadTodoItems,
            onClickSettings = presenter::navigateToUserThemeChoice,
        )

        is TodoItemsScreenState.Error -> ErrorScreen(
            onUpdate = presenter::loadTodoItems,
            message = todoItemsScreenState.message,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListTodoItems(
    todoItems: List<TodoItemUiModel>,
    countOfCompletedItems: Int,
    isHiddenCompletedItems: Boolean,
    onCheckedChange: (String, Boolean) -> Unit,
    onChangeHiddenCompletedItems: (Boolean) -> Unit,
    onDeleteItem: (String) -> Unit,
    onNavigateToDetails: (String?) -> Unit,
    onRefresh: () -> Unit,
    onClickSettings: () -> Unit,
) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val scrollState = rememberLazyListState()

    Scaffold(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .nestedScroll(scrollBehavior.nestedScrollConnection),
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
        topBar = {
            TodoItemsToolBar(
                scrollBehavior = scrollBehavior,
                countOfCompletedItems = countOfCompletedItems,
                isHiddenCompletedItems = isHiddenCompletedItems,
                onChangeHiddenCompletedItems = onChangeHiddenCompletedItems,
                scrollState = scrollState,
                onClickSettings = onClickSettings,
            )
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
        ) {
            ElevatedLazyColumn(
                state = scrollState,
                todoItems = todoItems,
                onDeleteItem = onDeleteItem,
                onCheckedChange = onCheckedChange,
                onNavigateToDetails = onNavigateToDetails,
                onRefresh = onRefresh,
            )
        }


    }
}

@Composable
private fun ShowUiEffectIfNeeded(
    todoItemsScreenUiEffects: SharedFlow<TodoItemsScreenUiEffects>,
) {

    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        todoItemsScreenUiEffects.collect { todoItemsScreenUiEffect ->
            when (todoItemsScreenUiEffect) {
                is TodoItemsScreenUiEffects.ShowErrorMessage -> {
                    Toast.makeText(
                        context,
                        todoItemsScreenUiEffect.message,
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
        }
    }
}

@Preview
@Composable
fun ListTodoItemsPreview() {
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
            onCheckedChange = { _, _ -> },
            onChangeHiddenCompletedItems = { },
            onDeleteItem = { },
            onNavigateToDetails = { },
            onRefresh = { },
            onClickSettings = { },
        )
    }
}

@Preview
@Composable
fun ListTodoItemsDarkThemePreview() {
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
            onCheckedChange = { _, _ -> },
            onChangeHiddenCompletedItems = { },
            onDeleteItem = { },
            onNavigateToDetails = { },
            onRefresh = { },
            onClickSettings = { },
        )
    }
}

