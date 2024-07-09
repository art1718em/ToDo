package com.example.todo.ui.todoItemDetailsScreen.composables

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.todo.R
import com.example.todo.ui.design.theme.ToDoTheme
import com.example.todo.ui.design.theme.blue


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoItemDetailsToolBar(
    scrollState: ScrollState,
    behavior: TopAppBarScrollBehavior,
    onNavigateToItems: () -> Unit,
    onAddItem: () -> Unit,
){

    val shadowPadding by remember {
        derivedStateOf { if (scrollState.value == 0) 0.dp else 8.dp }
    }

    TopAppBar(
        modifier = Modifier
            .shadow(shadowPadding)
            .fillMaxWidth()
            .nestedScroll(behavior.nestedScrollConnection),
        navigationIcon = {
            IconButton(
                onClick = onNavigateToItems
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(id = R.string.icon_close),
                )
            }

        },
        title = {
            Row {
                Spacer(modifier = Modifier.weight(1f))
                TextButton(
                    colors = ButtonDefaults.buttonColors(
                        contentColor = MaterialTheme.colorScheme.blue,
                        containerColor = MaterialTheme.colorScheme.background,
                    ),
                    onClick = { onAddItem() }
                ) {
                    Text(
                        text = stringResource(id = R.string.save).uppercase(),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.blue,
                    )
                }

            }
        },
        colors = TopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.blue,
            scrolledContainerColor = MaterialTheme.colorScheme.background,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.blue
        ),
        scrollBehavior = behavior,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun TodoItemDetailsToolBarPreview(){
    ToDoTheme {
        TodoItemDetailsToolBar(
            behavior = TopAppBarDefaults.pinnedScrollBehavior(),
            scrollState = rememberScrollState(),
            onAddItem = {  },
            onNavigateToItems = {  },
        )
    }
}

