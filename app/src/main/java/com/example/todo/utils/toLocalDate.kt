package com.example.todo.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun String.toLocalDate(): LocalDate {
    return LocalDate.parse(this, DateTimeFormatter.ofPattern("dd MMM yyyy"))
}