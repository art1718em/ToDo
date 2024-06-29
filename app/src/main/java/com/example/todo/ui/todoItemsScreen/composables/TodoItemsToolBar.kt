package com.example.todo.ui.todoItemsScreen.composables

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo.R
import com.example.todo.ui.design.theme.ToDoTheme
import com.example.todo.ui.design.theme.blue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoItemsToolBar(
    countOfCompletedItems: Int,
    isHiddenCompletedItems: Boolean,
    scrollState: LazyListState,
    scrollBehavior: TopAppBarScrollBehavior,
    onChangeHiddenCompletedItems: (Boolean) -> Unit,
) {
    LargeTopAppBar(
        modifier = Modifier
            .shadow(
                if (remember { derivedStateOf { scrollState.firstVisibleItemScrollOffset } }.value == 0) 0.dp else 8.dp
            ),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            scrolledContainerColor = MaterialTheme.colorScheme.background
        ),
        title = {
            Text(
                text = stringResource(id = R.string.my_things),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary,
            )
        },
        scrollBehavior = scrollBehavior,
        actions = {
            Text(
                text = stringResource(id = R.string.completed) + " $countOfCompletedItems",
                style = MaterialTheme.typography.bodyMedium,
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
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun TodoItemsToolBarPreview(){
    ToDoTheme {
        TodoItemsToolBar(
            countOfCompletedItems = 3,
            isHiddenCompletedItems = false,
            scrollState = rememberLazyListState(),
            scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
            onChangeHiddenCompletedItems = {  },
        )
    }
}