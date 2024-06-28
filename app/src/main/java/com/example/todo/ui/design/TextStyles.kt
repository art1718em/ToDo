package com.example.todo.ui.design

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.example.todo.ui.design.theme.blue

@Composable
fun LargeTitleText(
    text: String,
    color: Color = MaterialTheme.colorScheme.onPrimary
){
    Text(
        text = text,
        fontWeight = FontWeight(500),
        color = color,
        fontSize = 32.sp,
        lineHeight = 38.sp,
    )
}

@Composable
fun ButtonText(
    text: String,
    color: Color = MaterialTheme.colorScheme.blue,
){
    Text(
        text = text.uppercase(),
        fontWeight = FontWeight(500),
        color = color,
        fontSize = 14.sp,
        lineHeight = 24.sp,
    )
}

@Composable
fun BodyText(
    text: String,
    color: Color = MaterialTheme.colorScheme.onPrimary,
    textStyle: TextStyle = TextStyle(),
    maxLines: Int = 1,
    textOverflow: TextOverflow = TextOverflow.Ellipsis,
){
    Text(
        text = text,
        fontWeight = FontWeight(400),
        style = textStyle,
        color = color,
        fontSize = 16.sp,
        lineHeight = 20.sp,
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
        fontWeight = FontWeight(400),
        color = color,
        fontSize = 14.sp,
        lineHeight = 20.sp,
    )
}