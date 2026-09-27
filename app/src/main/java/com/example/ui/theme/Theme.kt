package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val VtxColorScheme = darkColorScheme(
    primary = VtxPrimary,
    onPrimary = Color.White,
    primaryContainer = VtxSurfaceElevated,
    onPrimaryContainer = VtxTextPurple,
    secondary = VtxSecondary,
    onSecondary = Color.White,
    secondaryContainer = VtxSurfaceVariant,
    onSecondaryContainer = VtxTextPurple,
    background = VtxBackground,
    onBackground = VtxTextPrimary,
    surface = VtxSurface,
    onSurface = VtxTextPrimary,
    surfaceVariant = VtxSurfaceVariant,
    onSurfaceVariant = VtxTextSecondary,
    outline = VtxBorder,
    outlineVariant = VtxBorderGlowing,
    error = VtxDangerRed,
    onError = Color.White
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true, // Force modern dark purple theme as requested
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = VtxColorScheme,
        typography = Typography,
        content = content
    )
}
