package com.example.todo.ui.todoItemDetailsScreen.composables

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.todo.R
import com.example.todo.ui.design.theme.ToDoTheme
import com.example.todo.ui.design.theme.blue
import com.example.todo.ui.design.theme.elevated
import com.example.todo.ui.design.theme.white
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.ZoneId

@Composable
fun DateMaterialDialog(
    dateDialogState: MaterialDialogState,
    onUpdateDate: (Long?) -> Unit,
    onDismissDialog: () -> Unit,
) {
    MaterialDialog(
        backgroundColor = MaterialTheme.colorScheme.surface,
        dialogState = dateDialogState,
        buttons = {
            positiveButton(
                text = stringResource(id = R.string.done),
                textStyle = TextStyle(color = MaterialTheme.colorScheme.blue),
            )
            negativeButton(
                text = stringResource(id = R.string.cancel),
                textStyle = TextStyle(color = MaterialTheme.colorScheme.blue),
                onClick = onDismissDialog,
            )
        },
    ) {
        datepicker(
            title = stringResource(id = R.string.select_date),
            initialDate = LocalDate.now(),
            allowedDateValidator = {
                it >= LocalDate.now()
            },
            colors = DatePickerDefaults.colors(
                headerBackgroundColor = MaterialTheme.colorScheme.blue,
                headerTextColor = MaterialTheme.colorScheme.white,
                calendarHeaderTextColor = MaterialTheme.colorScheme.white,
                dateActiveBackgroundColor = MaterialTheme.colorScheme.blue,
                dateInactiveBackgroundColor = MaterialTheme.colorScheme.surface,
                dateActiveTextColor = MaterialTheme.colorScheme.white,
                dateInactiveTextColor = MaterialTheme.colorScheme.onPrimary,
            ),
        ) { localDate ->
            onUpdateDate(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DateMaterialDialogPreview(){
    val dateDialogState = rememberMaterialDialogState()
    dateDialogState.show()
    ToDoTheme {
        DateMaterialDialog(
            dateDialogState = dateDialogState,
            onUpdateDate = {  },
            onDismissDialog = {  },
        )
    }
}