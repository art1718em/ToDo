package com.example.todo.ui.design.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DarkColorBlock(color: Color, text: String, textColor: Color = Color.White) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(color)
            .padding(16.dp)
            .height(36.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Column {
            Text(
                text = text,
                color = textColor,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "#${Integer.toHexString(color.toArgb()).uppercase()}",
                color = textColor,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun DarkColorPalette() {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD3D3D3))
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        DarkColorBlock(md_theme_dark_outline, "Support [Dark] / Separator", Color.Black)
        DarkColorBlock(md_theme_dark_overlay, "Support [Dark] / Overlay")
        DarkColorBlock(md_theme_dark_onPrimary, "Label [Dark] / Primary", Color.Black)
        DarkColorBlock(md_theme_dark_onSecondary, "Label [Dark] / Secondary", Color.Black)
        DarkColorBlock(md_theme_dark_onTertiary, "Label [Dark] / Tertiary", Color.Black)
        DarkColorBlock(md_theme_dark_onDisable, "Label [Dark] / Disable", Color.Black)
        DarkColorBlock(md_theme_dark_red, "Color [Dark] / Red", Color.Black)
        DarkColorBlock(md_theme_dark_green, "Color [Dark] / Green")
        DarkColorBlock(md_theme_dark_blue, "Color [Dark] / Blue")
        DarkColorBlock(md_theme_dark_gray, "Color [Dark] / Gray")
        DarkColorBlock(md_theme_dark_gray_light, "Color [Dark] / Gray Light")
        DarkColorBlock(md_theme_dark_white, "Color [Dark] / White", Color.Black)
        DarkColorBlock(md_theme_dark_background, "Back [Dark] / Primary")
        DarkColorBlock(md_theme_dark_surface, "Back [Dark] / Secondary")
        DarkColorBlock(md_theme_dark_elevated, "Back [Dark] / Elevated")
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDarkColorPalette() {
    DarkColorPalette()
}