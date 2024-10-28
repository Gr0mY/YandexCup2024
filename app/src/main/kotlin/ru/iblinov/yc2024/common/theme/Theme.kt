package ru.iblinov.yc2024.common.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import ru.iblinov.yc2024.common.theme.AppColors.Black
import ru.iblinov.yc2024.common.theme.AppColors.Pink80
import ru.iblinov.yc2024.common.theme.AppColors.Purple80
import ru.iblinov.yc2024.common.theme.AppColors.PurpleGrey80

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    background = Black,
)

@Composable
fun LivePicturesTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}