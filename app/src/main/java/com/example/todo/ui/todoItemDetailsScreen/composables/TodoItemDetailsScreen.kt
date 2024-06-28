package com.example.todo.ui.todoItemDetailsScreen.composables

import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem

import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todo.R
import com.example.todo.domain.model.Importance
import com.example.todo.ui.design.BodyText
import com.example.todo.ui.design.ButtonText
import com.example.todo.ui.design.SubheadText
import com.example.todo.ui.design.theme.blue
import com.example.todo.ui.design.theme.disable
import com.example.todo.ui.design.theme.elevated
import com.example.todo.ui.design.theme.red
import com.example.todo.ui.design.theme.switchThumbUnchecked
import com.example.todo.ui.design.theme.switchTrackChecked
import com.example.todo.ui.design.theme.switchTrackUnchecked
import com.example.todo.ui.todoItemDetailsScreen.TodoItemDetailsPresenter
import com.example.todo.ui.todoItemDetailsScreen.state.TodoItemDetailsUiModel
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoItemDetailsScreen(
    presenter: TodoItemDetailsPresenter,
) {

    val todoItemDetailsUiModel by presenter.todoItemDetailsUiModel.collectAsState()

    val dateDialogState = remember { mutableStateOf(false) }

    var switchState by remember { mutableStateOf(todoItemDetailsUiModel.deadline != null) }

    var expanded by remember { mutableStateOf(false) }

    val behavior = TopAppBarDefaults.pinnedScrollBehavior()
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .shadow(
                        if (scrollState.value == 0) 0.dp else 8.dp
                    )
                    .fillMaxWidth()
                    .nestedScroll(behavior.nestedScrollConnection),
                navigationIcon = {
                    IconButton(onClick = {
                        presenter.navigateToItems()
                    }) {
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
                            onClick = { presenter.addItem() }
                        ) {
                            ButtonText(
                                text = stringResource(id = R.string.save),
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
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(scrollState),
        ) {
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
                    value = todoItemDetailsUiModel.text,
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 18.sp,
                        color = MaterialTheme.colorScheme.onPrimary,
                    ),
                    onValueChange = {
                        presenter.updateText(it)
                    },
                    minLines = 3,
                    placeholder = {
                        BodyText(
                            text = stringResource(id = R.string.what_needs_to_be_done),
                            color = MaterialTheme.colorScheme.onTertiary,
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        focusedIndicatorColor = MaterialTheme.colorScheme.surface,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.surface,
                    )
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
            ) {

                ImportanceRow(
                    expanded = expanded,
                    todoItemDetailsUiModel = todoItemDetailsUiModel,
                    onOpenDateDialog = { expanded = true },
                    onCloseDateDialog = { expanded = false },
                    onUpdateImportance = presenter::updateImportance,
                )

                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = MaterialTheme.colorScheme.outline,
                    thickness = 0.5.dp,
                )

                DeadlineRow(
                    todoItemDetailsUiModel = todoItemDetailsUiModel,
                    switchState = switchState,
                    onUpdateDeadline = presenter::updateDeadline,
                    onSwitchToFalse = {
                        switchState = false
                    },
                    onSwitchToTrue = {
                        switchState = true
                        dateDialogState.value = true
                    },
                    onOpenDateDialog = {
                        dateDialogState.value = true
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
                            presenter.deleteTodoItem()
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
                    )
                }
            }

        }
    }




    if (dateDialogState.value) {
        DatePickerDialog(
            onDateSelected = { date ->
                presenter.updateDeadline(date.time)
                dateDialogState.value = false
            },
            onDismissRequest = {
                dateDialogState.value = false
                if (todoItemDetailsUiModel.deadline == null)
                    switchState = false
            }
        )
    }
}

@Composable
fun DatePickerDialog(onDateSelected: (Date) -> Unit, onDismissRequest: () -> Unit) {

    val context = LocalContext.current
    val calendar = Calendar.getInstance()


    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            onDateSelected(calendar.time)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    datePickerDialog.setOnDismissListener {
        onDismissRequest()
    }

    datePickerDialog.show()
}

@Composable
fun ImportanceRow(
    expanded: Boolean,
    todoItemDetailsUiModel: TodoItemDetailsUiModel,
    onOpenDateDialog: () -> Unit,
    onCloseDateDialog: () -> Unit,
    onUpdateImportance: (Importance) -> Unit,
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
    ) {
        Column(
            modifier = Modifier
                .clickable { onOpenDateDialog() }
        ) {
            BodyText(
                text = stringResource(id = R.string.importance),
            )

            SubheadText(
                text = stringResource(
                    id = when (todoItemDetailsUiModel.importance) {
                        is Importance.Usual -> R.string.no
                        is Importance.Low -> R.string.low
                        is Importance.High -> R.string.high
                    }
                ),
                color = when (todoItemDetailsUiModel.importance) {
                    is Importance.High -> MaterialTheme.colorScheme.red
                    else -> MaterialTheme.colorScheme.onTertiary
                },
            )
        }
    }
    DropdownMenu(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.elevated),
        offset = DpOffset(x = 0.dp, y = 4.dp),
        expanded = expanded,
        onDismissRequest = { onCloseDateDialog() }
    ) {
        DropdownMenuItem(
            text = { Text(text = stringResource(id = R.string.no)) },
            onClick = {
                onCloseDateDialog()
                onUpdateImportance(Importance.Usual)
            },
            modifier = Modifier.padding(2.dp),
        )
        DropdownMenuItem(
            text = { Text(text = stringResource(id = R.string.low)) },
            onClick = {
                onCloseDateDialog()
                onUpdateImportance(Importance.Low)
            },
            modifier = Modifier.padding(2.dp)
        )
        DropdownMenuItem(
            text = {
                Text(
                    text = stringResource(id = R.string.high),
                    color = MaterialTheme.colorScheme.red,
                )
            },
            onClick = {
                onCloseDateDialog()
                onUpdateImportance(Importance.High)
            },
            modifier = Modifier.padding(2.dp)
        )
    }
}

@Composable
fun DeadlineRow(
    todoItemDetailsUiModel: TodoItemDetailsUiModel,
    switchState: Boolean,
    onUpdateDeadline: (Long?) -> Unit,
    onSwitchToFalse: () -> Unit,
    onSwitchToTrue: () -> Unit,
    onOpenDateDialog: () -> Unit,
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 16.dp,
                bottom = 40.dp,
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = if (switchState){
                Modifier
                    .clickable {
                        onOpenDateDialog()
                    }
            } else {
                Modifier
            },
        ) {
            BodyText(
                text = stringResource(id = R.string.make_it_to),
            )

            SubheadText(
                text = todoItemDetailsUiModel.deadline ?: "",
                color = MaterialTheme.colorScheme.blue,
            )
        }
        Switch(
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colorScheme.blue,
                checkedTrackColor = MaterialTheme.colorScheme.switchTrackChecked,
                uncheckedThumbColor = MaterialTheme.colorScheme.switchThumbUnchecked,
                uncheckedTrackColor = MaterialTheme.colorScheme.switchTrackUnchecked,

                ),
            checked = (todoItemDetailsUiModel.deadline != null || switchState),
            onCheckedChange = {
                if (switchState) {
                    onSwitchToFalse()
                    onUpdateDeadline(null)
                } else {
                    onSwitchToTrue()
                }
            }
        )

    }
}

