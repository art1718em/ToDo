package com.example.todo.ui.todoItemDetailsScreen.composables

import android.app.DatePickerDialog
import android.os.DropBoxManager
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
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
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todo.R
import com.example.todo.domain.model.Importance
import com.example.todo.ui.design.theme.blue
import com.example.todo.ui.design.theme.disable
import com.example.todo.ui.design.theme.elevated
import com.example.todo.ui.design.theme.red
import com.example.todo.ui.design.theme.switchTrackChecked
import com.example.todo.ui.design.theme.switchTrackUnchecked
import com.example.todo.ui.design.theme.white
import com.example.todo.ui.todoItemDetailsScreen.TodoItemDetailsPresenter
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoItemDetailsScreen(
    presenter: TodoItemDetailsPresenter,
) {

    val todoItemDetailsUiModel by presenter.todoItemDetailsUiModel.collectAsState()

    val dateDialogState = remember { mutableStateOf(false) }

    val switchState = remember { mutableStateOf(todoItemDetailsUiModel.deadline != null) }
    Log.d("mytag", todoItemDetailsUiModel.deadline.toString())
    Log.d("mytag", switchState.value.toString())

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
                            Text(
                                text = stringResource(id = R.string.save),
                                fontFamily = FontFamily(Font(R.font.roboto_medium)),
                                fontSize = 14.sp,
                                lineHeight = 24.sp,
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
                    containerColor = MaterialTheme.colorScheme.white
                )
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = todoItemDetailsUiModel.text,
                    textStyle = TextStyle(
                        fontFamily = FontFamily(Font(R.font.roboto_regular)),
                        fontSize = 16.sp,
                        lineHeight = 18.sp,
                        color = MaterialTheme.colorScheme.onPrimary,
                    ),
                    onValueChange = {
                        presenter.updateText(it)
                    },
                    minLines = 3,
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.what_needs_to_be_done),
                            fontFamily = FontFamily(Font(R.font.roboto_regular)),
                            fontSize = 16.sp,
                            lineHeight = 18.sp,
                            color = MaterialTheme.colorScheme.onTertiary,
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = MaterialTheme.colorScheme.white,
                        focusedIndicatorColor = MaterialTheme.colorScheme.white,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.white,
                    )
                )
            }
            Spacer(
                modifier = Modifier
                    .height(12.dp),
            )
            Box(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    Box {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                        ) {
                            Column(
                                modifier = Modifier
                                    .clickable { expanded = true }
                            ) {
                                Text(
                                    text = stringResource(id = R.string.importance),
                                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                                    fontSize = 16.sp,
                                    lineHeight = 18.sp,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                )
                                Text(
                                    text = stringResource(
                                        id = when (todoItemDetailsUiModel.importance) {
                                            is Importance.Usual -> R.string.no
                                            is Importance.Low -> R.string.low
                                            is Importance.High -> R.string.high
                                        }
                                    ),
                                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                                    fontSize = 14.sp,
                                    lineHeight = 16.sp,
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
                            onDismissRequest = { expanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text(text = stringResource(id = R.string.no)) },
                                onClick = {
                                    expanded = false
                                    presenter.updateImportance(Importance.Usual)
                                },
                                modifier = Modifier.padding(2.dp),
                            )
                            DropdownMenuItem(
                                text = { Text(text = stringResource(id = R.string.low)) },
                                onClick = {
                                    expanded = false
                                    presenter.updateImportance(Importance.Low)
                                },
                                modifier = Modifier.padding(2.dp)
                            )
                            DropdownMenuItem(
                                text = { Text(text = stringResource(id = R.string.high)) },
                                onClick = {
                                    expanded = false
                                    presenter.updateImportance(Importance.High)
                                },
                                modifier = Modifier.padding(2.dp)
                            )
                        }
                    }
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth(),
                        color = MaterialTheme.colorScheme.outline,
                        thickness = 0.5.dp,
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column {
                            Text(
                                text = stringResource(id = R.string.make_it_to),
                                fontFamily = FontFamily(Font(R.font.roboto_regular)),
                                fontSize = 16.sp,
                                lineHeight = 18.sp,
                                color = MaterialTheme.colorScheme.onPrimary,
                            )
                            Text(
                                text = todoItemDetailsUiModel.deadline ?: "",
                                fontFamily = FontFamily(Font(R.font.roboto_regular)),
                                fontSize = 14.sp,
                                lineHeight = 16.sp,
                                color = MaterialTheme.colorScheme.blue,
                            )
                        }
                        Switch(
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = MaterialTheme.colorScheme.blue,
                                checkedTrackColor = MaterialTheme.colorScheme.switchTrackChecked,
                                uncheckedThumbColor = MaterialTheme.colorScheme.white,
                                uncheckedTrackColor = MaterialTheme.colorScheme.switchTrackUnchecked,
                            ),
                            checked = (todoItemDetailsUiModel.deadline != null || switchState.value),
                            onCheckedChange = {
                                if (switchState.value) {
                                    switchState.value = false
                                    presenter.updateDeadline(null)
                                } else {
                                    switchState.value = true
                                    dateDialogState.value = true
                                }
                            }
                        )

                    }
                    Spacer(
                        modifier = Modifier
                            .height(24.dp),
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
                            .clickable {
                                if (todoItemDetailsUiModel.id.isNotEmpty()) {
                                    presenter.deleteTodoItem()
                                }
                            },
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            tint =  if (todoItemDetailsUiModel.id.isNotEmpty()){
                                MaterialTheme.colorScheme.red
                            }else{
                                MaterialTheme.colorScheme.disable
                            },
                            imageVector = Icons.Default.Delete,
                            contentDescription = stringResource(id = R.string.icon_delete),
                        )
                        Text(
                            modifier = Modifier
                                .padding(start = 12.dp),
                            text = stringResource(id = R.string.delete),
                            fontFamily = FontFamily(Font(R.font.roboto_regular)),
                            fontSize = 16.sp,
                            lineHeight = 20.sp,
                            color = if (todoItemDetailsUiModel.id.isNotEmpty()){
                                MaterialTheme.colorScheme.red
                            } else{
                                MaterialTheme.colorScheme.disable
                            },
                        )
                    }
                }
            }
        }
    }




    if (dateDialogState.value) {
        DatePickerDialog(
            onDateSelected = { date ->
                presenter.updateDeadline(date)
                dateDialogState.value = false
            },
            onDismissRequest = {
                dateDialogState.value = false
                if (todoItemDetailsUiModel.deadline == null)
                    switchState.value = false
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

