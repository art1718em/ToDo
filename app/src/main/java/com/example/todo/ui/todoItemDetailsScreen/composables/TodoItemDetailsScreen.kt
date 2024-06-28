package com.example.todo.ui.todoItemDetailsScreen.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo.R
import com.example.todo.domain.model.Importance
import com.example.todo.ui.design.BodyText
import com.example.todo.ui.design.ErrorScreen
import com.example.todo.ui.design.ProgressBar
import com.example.todo.ui.design.theme.ToDoTheme
import com.example.todo.ui.design.theme.disable
import com.example.todo.ui.design.theme.red
import com.example.todo.ui.todoItemDetailsScreen.TodoItemDetailsPresenter
import com.example.todo.ui.todoItemDetailsScreen.state.TodoItemDetailsScreenState
import com.example.todo.ui.todoItemDetailsScreen.state.TodoItemDetailsUiModel
import com.example.todo.ui.todoItemsScreen.composables.ListTodoItems
import com.example.todo.ui.todoItemsScreen.state.TodoItemsScreenState
import com.vanpra.composematerialdialogs.rememberMaterialDialogState

@Composable
fun TodoItemDetailsScreen(
    presenter: TodoItemDetailsPresenter,
) {

    val todoItemDetailsScreenState = presenter.todoItemDetailsScreenState.collectAsState().value

    when (todoItemDetailsScreenState) {
        is TodoItemDetailsScreenState.Loading -> ProgressBar()
        is TodoItemDetailsScreenState.Success -> TodoItemDetails(
            todoItemDetailsUiModel = todoItemDetailsScreenState.todoItemDetailsUiModel,
            onNavigateToItems = presenter::navigateToItems,
            onAddItem = presenter::addItem,
            onUpdateText = presenter::updateText,
            onUpdateImportance = presenter::updateImportance,
            onUpdateDeadline = presenter::updateDeadline,
            onDeleteItem = presenter::deleteTodoItem,
        )
        is TodoItemDetailsScreenState.Error -> ErrorScreen(
            onUpdate = presenter::loadTodoItem,
            message =todoItemDetailsScreenState.message,
        )
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoItemDetails(
    todoItemDetailsUiModel: TodoItemDetailsUiModel,
    onNavigateToItems: () -> Unit,
    onAddItem: () -> Unit,
    onUpdateText: (String) -> Unit,
    onUpdateImportance: (Importance) -> Unit,
    onUpdateDeadline: (Long?) -> Unit,
    onDeleteItem: () -> Unit,
){

    var switchState by remember { mutableStateOf(todoItemDetailsUiModel.deadline != null) }

    val dateDialogState = rememberMaterialDialogState()

    var expanded by remember { mutableStateOf(false) }

    val behavior = TopAppBarDefaults.pinnedScrollBehavior()
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TodoItemDetailsToolBar(
                scrollState = scrollState,
                behavior = behavior,
                onNavigateToItems = onNavigateToItems,
                onAddItem = onAddItem,
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(scrollState),
        ) {
            ElevatedTextField(
                paddingValues = paddingValues,
                text = todoItemDetailsUiModel.text,
                onUpdate = onUpdateText,
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
            ) {

                ImportanceSelector(
                    expanded = expanded,
                    importance = todoItemDetailsUiModel.importance,
                    onOpenDateDialog = { expanded = true },
                    onCloseDateDialog = { expanded = false },
                    onUpdateImportance = onUpdateImportance,
                )

                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = MaterialTheme.colorScheme.outline,
                    thickness = 0.5.dp,
                )

                DeadlineSelector(
                    deadline = todoItemDetailsUiModel.deadline,
                    switchState = switchState,
                    onUpdateDeadline = onUpdateDeadline,
                    onSwitchToFalse = {
                        switchState = false
                    },
                    onSwitchToTrue = {
                        switchState = true
                        dateDialogState.show()
                    },
                    onOpenDateDialog = {
                        dateDialogState.show()
                    }
                )

                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = MaterialTheme.colorScheme.outline,
                    thickness = 0.5.dp,
                )
                Spacer(
                    modifier = Modifier
                        .height(8.dp),
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 14.dp)
                        .takeIf { todoItemDetailsUiModel.id.isNotEmpty() }
                        ?.clickable {
                            onDeleteItem()
                        } ?: Modifier,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        modifier = Modifier
                            .padding(end = 12.dp),
                        tint = if (todoItemDetailsUiModel.id.isNotEmpty()) {
                            MaterialTheme.colorScheme.red
                        } else {
                            MaterialTheme.colorScheme.disable
                        },
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(id = R.string.icon_delete),
                    )
                    BodyText(
                        text = stringResource(id = R.string.delete),
                        color = if (todoItemDetailsUiModel.id.isNotEmpty()) {
                            MaterialTheme.colorScheme.red
                        } else {
                            MaterialTheme.colorScheme.disable
                        },
                        isLineThrough = false,
                    )
                }
            }

        }
    }

    DateMaterialDialog(
        dateDialogState = dateDialogState,
        onUpdateDate = onUpdateDeadline,
        onDismissDialog = { switchState = false }
    )
}

@Preview(showBackground = true)
@Composable
fun TodoItemDetailsPreview(){
    ToDoTheme {
        TodoItemDetails(
            TodoItemDetailsUiModel(
                id = stringResource(id = R.string.text),
                text = stringResource(id = R.string.text),
                deadline = stringResource(id = R.string.text),
                importance = Importance.Usual,
            ),
            onAddItem = {  },
            onDeleteItem = {  },
            onNavigateToItems = {  },
            onUpdateImportance = {  },
            onUpdateText = {  },
            onUpdateDeadline = {  },
        )
    }
}