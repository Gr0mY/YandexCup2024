package ru.iblinov.yc2024.common.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

private val LightColorScheme = darkColorScheme(
    background = lightAppColors.background,
    primary = lightAppColors.primary,
    onPrimary = lightAppColors.onPrimary,
    secondaryContainer = lightAppColors.secondaryContainer,
)
private val DarkColorScheme = darkColorScheme(
    background = darkAppColors.background,
    primary = darkAppColors.primary,
    onPrimary = darkAppColors.onPrimary,
    secondaryContainer = darkAppColors.secondaryContainer,
)

@Composable
fun LivePicturesTheme(
    isDarkMode: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalAppColors provides (if (isDarkMode) darkAppColors else lightAppColors)
    ) {
        MaterialTheme(
            colorScheme = if (isDarkMode) DarkColorScheme else LightColorScheme,
            typography = Typography,
            content = content
        )
    }
}