package com.example.todo.ui.todoItemDetailsScreen.composables

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todo.R
import com.example.todo.ui.design.BodyText
import com.example.todo.ui.design.theme.ToDoTheme
import com.example.todo.ui.todoItemDetailsScreen.state.TodoItemDetailsUiModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ElevatedTextField(
    paddingValues: PaddingValues,
    text: String,
    onUpdate: (String) -> Unit,
){
    ElevatedCard(
        modifier = Modifier
            .padding(top = 8.dp)
            .fillMaxWidth()
            .padding(paddingValues),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = text,
            textStyle = TextStyle(
                fontSize = 16.sp,
                lineHeight = 18.sp,
                color = MaterialTheme.colorScheme.onPrimary,
            ),
            onValueChange = {
                onUpdate(it)
            },
            minLines = 3,
            placeholder = {
                BodyText(
                    text = stringResource(id = R.string.what_needs_to_be_done),
                    color = MaterialTheme.colorScheme.onTertiary,
                    isLineThrough = false,
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.surface,
                focusedIndicatorColor = MaterialTheme.colorScheme.surface,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.surface,
            )
        )
    }
}

@Preview
@Composable
fun ElevatedTextFieldPreview(){
    ToDoTheme {
        ElevatedTextField(
            paddingValues = PaddingValues(0.dp),
            text = "",
            onUpdate = {  },
        )
    }
}