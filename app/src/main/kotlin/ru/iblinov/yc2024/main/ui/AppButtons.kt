package ru.iblinov.yc2024.main.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.iblinov.yc2024.common.theme.AppColors
import ru.iblinov.yc2024.main.mvi.MainAction


@Composable
fun ColorButton(
    color: Color,
    borderColor: Color = Color.Transparent,
    isActive: Boolean = true,
    onAction: (MainAction) -> Unit
) {
    Box(
        modifier = Modifier
            .size(32.dp)
            .padding(2.dp)
            .clip(CircleShape)
            .background(color)
            .border(1.5.dp, borderColor, CircleShape)
            .clickable(isActive) { onAction(MainAction.Palette.ChooseColorClicked) }
    )
}

@Composable
fun AppIconButton(
    isActive: Boolean = true,
    isSelected: Boolean = false,
    @DrawableRes
    drawableRes: Int,
    contentDescription: String,
    tint: Color = toolbarButtonTint(isActive, isSelected),
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
        tint = tint,
        contentDescription = contentDescription,
    )
}

private fun toolbarButtonTint(
    isActive: Boolean,
    isSelected: Boolean,
): Color = when {
    !isActive -> AppColors.ButtonInactive
    isSelected -> AppColors.GreenSelected
    else -> AppColors.White
}