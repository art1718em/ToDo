package com.example.todo.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateFormatting {

    private const val DATE_PATTERN = "dd MMM yyyy"
    @SuppressLint("ConstantLocale")
    private val formatter = SimpleDateFormat(DATE_PATTERN, Locale.getDefault())

    fun toFormattedDate(dateLong: Long?): String? =
        dateLong?.let { formatter.format(Date(it)) }

    fun toDateLong(dateString: String?): Long? =
        dateString?.let { formatter.parse(it)?.time ?: 0L }

}