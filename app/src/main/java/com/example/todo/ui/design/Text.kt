package com.example.todo.ui.design

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.todo.R
import com.example.todo.ui.design.theme.blue

@Composable
fun LargeTitleText(
    text: String,
    color: Color = MaterialTheme.colorScheme.onPrimary
){
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        color = color,
    )
}

@Composable
fun ButtonText(
    text: String,
    color: Color = MaterialTheme.colorScheme.blue,
){
    Text(
        text = text.uppercase(),
        style = MaterialTheme.typography.bodyLarge,
        color = color,
    )
}

@Composable
fun BodyText(
    text: String,
    color: Color = MaterialTheme.colorScheme.onPrimary,
    isLineThrough: Boolean,
    maxLines: Int = 1,
    textOverflow: TextOverflow = TextOverflow.Ellipsis,
){
    Text(
        text = text,
        style = if (isLineThrough) MaterialTheme.typography.bodySmall else MaterialTheme.typography.bodyMedium,
        color = color,
        maxLines =  maxLines,
        overflow = textOverflow,
    )
}

@Composable
fun SubheadText(
    text: String,
    color: Color = MaterialTheme.colorScheme.onTertiary,
){
    Text(
        text = text,
        color = color,
        style = MaterialTheme.typography.headlineMedium,
    )
}