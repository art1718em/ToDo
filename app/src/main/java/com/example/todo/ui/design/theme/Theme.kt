package com.example.todo.ui.design.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    outline = md_theme_dark_outline,
    onPrimary = md_theme_dark_onPrimary,
    onSecondary = md_theme_dark_onSecondary,
    onTertiary = md_theme_dark_onTertiary,
    surface = md_theme_dark_surface,
    background = md_theme_dark_background,
)
val ColorScheme.elevated: Color
    @Composable
    get() = if (isSystemInDarkTheme()) md_theme_dark_elevated else md_theme_light_elevated

val ColorScheme.blue: Color
    @Composable
    get() = if (isSystemInDarkTheme()) md_theme_dark_blue else md_theme_light_blue

val ColorScheme.red: Color
    @Composable
    get() = if (isSystemInDarkTheme()) md_theme_dark_red else md_theme_light_red

val ColorScheme.green: Color
    @Composable
    get() = if (isSystemInDarkTheme()) md_theme_dark_green else md_theme_light_green

val ColorScheme.gray: Color
    @Composable
    get() = if (isSystemInDarkTheme()) md_theme_dark_gray else md_theme_light_gray

val ColorScheme.grayLight: Color
    @Composable
    get() = if (isSystemInDarkTheme()) md_theme_dark_gray_light else md_theme_light_gray_light

val ColorScheme.white: Color
    @Composable
    get() = if (isSystemInDarkTheme()) md_theme_dark_white else md_theme_light_white

val ColorScheme.overlay: Color
    @Composable
    get() = if (isSystemInDarkTheme()) md_theme_dark_overlay else md_theme_light_overlay

val ColorScheme.disable: Color
    @Composable
    get() = if (isSystemInDarkTheme()) md_theme_dark_onDisable else md_theme_light_onDisable

val ColorScheme.pink: Color
    @Composable
    get() = if (isSystemInDarkTheme()) md_theme_dark_pink else md_theme_light_pink

val ColorScheme.switchTrackChecked: Color
    @Composable
    get() = if (isSystemInDarkTheme()) md_theme_light_switch_track_checked else md_theme_dark_switch_track_checked

val ColorScheme.switchTrackUnchecked: Color
    @Composable
    get() = if (isSystemInDarkTheme()) md_theme_light_switch_track_unchecked else md_theme_dark_switch_track_unchecked

private val LightColorScheme = lightColorScheme(
    outline = md_theme_light_outline,
    onPrimary = md_theme_light_onPrimary,
    onSecondary = md_theme_light_onSecondary,
    onTertiary = md_theme_light_onTertiary,
    surface = md_theme_light_surface,
    background = md_theme_light_background,
)

@Composable
fun ToDoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}