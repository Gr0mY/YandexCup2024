package ru.iblinov.yc2024.main.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import ru.iblinov.yc2024.common.theme.AppColors.ButtonInactive
import ru.iblinov.yc2024.common.theme.AppColors.White

@Composable
fun AppIconButton(
    isActive: Boolean = true,
    @DrawableRes
    drawableRes: Int,
    contentDescription: String,
    onClick: () -> Unit,
) {
    Icon(
        modifier = Modifier
            .clip(CircleShape)
            .clickable(
                enabled = isActive,
                onClick = onClick
            ),
        painter = painterResource(drawableRes),
        tint = toolbarButtonTint(isActive),
        contentDescription = contentDescription,
    )
}

private fun toolbarButtonTint(
    isPauseButtonActive: Boolean,
) = if (isPauseButtonActive) White else ButtonInactive