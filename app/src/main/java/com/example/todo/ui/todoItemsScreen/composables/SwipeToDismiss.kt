package com.example.todo.ui.todoItemsScreen.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo.R
import com.example.todo.ui.design.theme.ToDoTheme
import com.example.todo.ui.design.theme.green
import com.example.todo.ui.design.theme.red
import com.example.todo.ui.design.theme.white
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeBackground(
    swipeDismissState: SwipeToDismissBoxState,
    isCompleted: Boolean,
) {
    val color = when (swipeDismissState.dismissDirection) {
        SwipeToDismissBoxValue.EndToStart -> MaterialTheme.colorScheme.red
        SwipeToDismissBoxValue.StartToEnd -> if (!isCompleted) {
            MaterialTheme.colorScheme.green
        } else {
            Color.Transparent
        }
        else -> Color.Transparent
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(color),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            if (swipeDismissState.dismissDirection == SwipeToDismissBoxValue.StartToEnd && !isCompleted) {
                Icon(
                    modifier = Modifier
                        .padding(start = 24.dp)
                        .size(24.dp),
                    imageVector = Icons.Default.Check,
                    contentDescription = stringResource(id = R.string.icon_complete),
                    tint = MaterialTheme.colorScheme.white,
                )
            }

            Spacer(
                modifier = Modifier
                    .weight(1f),
            )

            if (swipeDismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart) {
                Icon(
                    modifier = Modifier
                        .padding(end = 24.dp)
                        .size(24.dp),
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(id = R.string.icon_delete),
                    tint = MaterialTheme.colorScheme.white,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeContainer(
    id: String,
    isCompleted: Boolean,
    onDelete: (String) -> Unit,
    onCheckedChange: (String, Boolean) -> Unit,
    animationDuration: Int = 500,
    content: @Composable () -> Unit,
){
    var isRemoved by remember{
        mutableStateOf(false)
    }

    val state = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            when (it) {
                SwipeToDismissBoxValue.EndToStart -> {
                    isRemoved = true
                    true
                }
                SwipeToDismissBoxValue.StartToEnd -> {
                    onCheckedChange(id, true)
                    false
                }
                SwipeToDismissBoxValue.Settled -> false
            }
        }
    )

    LaunchedEffect(key1 = isRemoved) {
        if(isRemoved) {
            delay(animationDuration.toLong())
            onDelete(id)
        }
    }

    AnimatedVisibility(
        visible = !isRemoved,
        exit = shrinkVertically(
            animationSpec = tween(durationMillis = animationDuration),
            shrinkTowards = Alignment.Top,
        ) + fadeOut(),
    ) {
        SwipeToDismissBox(
            state = state,
            backgroundContent = {
                 SwipeBackground(swipeDismissState = state, isCompleted)
            },
            enableDismissFromStartToEnd = !isCompleted
        ){
            content()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun SwipeBackgroundPreview(){
    ToDoTheme {
        SwipeBackground(
            swipeDismissState = rememberSwipeToDismissBoxState(),
            isCompleted = false
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SwipeContainerPreview(){
    SwipeContainer(
        id = "123",
        isCompleted = false,
        onCheckedChange = { _, _ -> },
        onDelete = {  },
        content = {  }
    )
}