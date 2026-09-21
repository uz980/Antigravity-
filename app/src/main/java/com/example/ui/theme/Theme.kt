package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme =
  darkColorScheme(
    primary = CyanAccent,
    onPrimary = ObsidianBackground,
    primaryContainer = ObsidianSurfaceVariant,
    onPrimaryContainer = CyanAccent,
    secondary = VioletAccent,
    onSecondary = TextPrimary,
    secondaryContainer = ObsidianSurfaceVariant,
    onSecondaryContainer = VioletAccentLight,
    tertiary = NeonGreen,
    onTertiary = ObsidianBackground,
    background = ObsidianBackground,
    onBackground = TextPrimary,
    surface = ObsidianSurface,
    onSurface = TextPrimary,
    surfaceVariant = ObsidianSurfaceVariant,
    onSurfaceVariant = TextSecondary,
    outline = ObsidianBorder
  )

private val LightColorScheme =
  darkColorScheme(
    primary = CyanAccent,
    onPrimary = ObsidianBackground,
    background = ObsidianBackground,
    surface = ObsidianSurface
  )

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = true, // Default to iconic Antigravity dark IDE mode
  dynamicColor: Boolean = false, // Preserve Antigravity cyber-IDE identity
  content: @Composable () -> Unit,
) {
  val colorScheme = DarkColorScheme
  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
