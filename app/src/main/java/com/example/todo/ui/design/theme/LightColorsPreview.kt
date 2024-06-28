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
fun LightColorPalette() {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD3D3D3))
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        LightColorBlock(md_theme_light_outline, "Support [Light] / Separator")
        LightColorBlock(md_theme_light_overlay, "Support [Light] / Overlay")
        LightColorBlock(md_theme_light_onPrimary, "Label [Light] / Primary")
        LightColorBlock(md_theme_light_onSecondary, "Label [Light] / Secondary")
        LightColorBlock(md_theme_light_onTertiary, "Label [Light] / Tertiary")
        LightColorBlock(md_theme_light_onDisable, "Label [Light] / Disable")
        LightColorBlock(md_theme_light_red, "Color [Light] / Red")
        LightColorBlock(md_theme_light_green, "Color [Light] / Green")
        LightColorBlock(md_theme_light_blue, "Color [Light] / Blue")
        LightColorBlock(md_theme_light_gray, "Color [Light] / Gray")
        LightColorBlock(md_theme_light_gray_light, "Color [Light] / Gray Light")
        LightColorBlock(md_theme_light_white, "Color [Light] / White")
        LightColorBlock(md_theme_light_background, "Back [Light] / Primary")
        LightColorBlock(md_theme_light_surface, "Back [Light] / Secondary")
        LightColorBlock(md_theme_light_elevated, "Back [Light] / Elevated")
    }
}

@Composable
fun LightColorBlock(color: Color, text: String) {
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
                color = if (text == "Label [Light] / Primary") Color.White else Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "#${Integer.toHexString(color.toArgb()).uppercase()}",
                color = if (text == "Label [Light] / Primary") Color.White else Color.Black,
                fontSize = 12.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLightColorPalette() {
    LightColorPalette()
}

