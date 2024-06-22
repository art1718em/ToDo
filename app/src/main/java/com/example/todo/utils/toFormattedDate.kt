package com.example.todo.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.toFormattedDate(): String {
    val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    return formatter.format(this)
}
