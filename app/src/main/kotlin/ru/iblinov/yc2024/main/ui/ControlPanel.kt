package ru.iblinov.yc2024.main.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.unit.dp
import ru.iblinov.yc2024.R
import ru.iblinov.yc2024.common.theme.AppColors
import ru.iblinov.yc2024.main.mvi.MainAction

@Composable
fun ControlPanel(
    currentColor: Color,
    areNonPlayingButtonsActive: Boolean,
    getTopOfControlPanel: (Float) -> Unit,
    onAction: (MainAction) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 22.dp)
            .navigationBarsPadding()
            .onGloballyPositioned { getTopOfControlPanel(it.positionInParent().y) },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.CenterHorizontally
        ),
    ) {
        PencilButton(areNonPlayingButtonsActive)
        BrushButton(areNonPlayingButtonsActive)
        EraseButton(areNonPlayingButtonsActive)
        InstrumentsButton(areNonPlayingButtonsActive)
        ColorButton(
            color = currentColor,
            isActive = areNonPlayingButtonsActive,
            borderColor = AppColors.GreenSelected,
            onAction = onAction
        )
    }
}

@Composable
private fun PencilButton(isActive: Boolean) {
    AppIconButton(
        isActive = isActive,
        drawableRes = R.drawable.pencil__edit__create,
        // todo to res
        contentDescription = "Карандаш",
        // todo
        onClick = { },
    )
}

@Composable
private fun BrushButton(isActive: Boolean) {
    AppIconButton(
        isActive = isActive,
        drawableRes = R.drawable.brush__edit__create,
        // todo to res
        contentDescription = "Кисть",
        // todo
        onClick = { },
    )
}

@Composable
private fun EraseButton(isActive: Boolean) {
    AppIconButton(
        isActive = isActive,
        drawableRes = R.drawable.erase,
        // todo to res
        contentDescription = "Ластик",
        // todo
        onClick = { },
    )
}

@Composable
private fun InstrumentsButton(isActive: Boolean) {
    AppIconButton(
        isActive = isActive,
        drawableRes = R.drawable.instruments,
        // todo to res
        contentDescription = "Инструменты",
        // todo
        onClick = { },
    )
}

