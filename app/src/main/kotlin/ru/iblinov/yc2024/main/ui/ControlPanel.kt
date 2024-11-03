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
import ru.iblinov.yc2024.common.model.DrawnPathType
import ru.iblinov.yc2024.common.theme.AppColors
import ru.iblinov.yc2024.main.mvi.MainAction

@Composable
fun ControlPanel(
    currentColor: Color,
    areNonPlayingButtonsActive: Boolean,
    drawnPathType: DrawnPathType,
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
        PencilButton(
            isActive = areNonPlayingButtonsActive,
            isSelected = drawnPathType == DrawnPathType.PENCIL,
            onAction = onAction,
        )
        BrushButton()
        EraseButton(
            isActive = areNonPlayingButtonsActive,
            isSelected = drawnPathType == DrawnPathType.ERASE,
            onAction = onAction,
        )
        InstrumentsButton()
        ColorButton(
            color = currentColor,
            isActive = areNonPlayingButtonsActive,
            borderColor = AppColors.GreenSelected,
            onAction = onAction
        )
    }
}

@Composable
private fun PencilButton(
    isActive: Boolean,
    isSelected: Boolean,
    onAction: (MainAction) -> Unit,
) {
    AppIconButton(
        isActive = isActive,
        isSelected = isSelected,
        drawableRes = R.drawable.pencil__edit__create,
        // todo to res
        contentDescription = "Карандаш",
        onClick = { onAction(MainAction.ControlPanel.PencilClicked) },
    )
}

@Composable
private fun BrushButton() {
    AppIconButton(
        isActive = false,
        drawableRes = R.drawable.brush__edit__create,
        // todo to res
        contentDescription = "Кисть",
        // todo
        onClick = { },
    )
}

@Composable
private fun EraseButton(
    isActive: Boolean,
    isSelected: Boolean,
    onAction: (MainAction) -> Unit,
) {
    AppIconButton(
        isActive = isActive,
        isSelected = isSelected,
        drawableRes = R.drawable.erase,
        // todo to res
        contentDescription = "Ластик",
        onClick = { onAction(MainAction.ControlPanel.EraseClicked) },
    )
}

@Composable
private fun InstrumentsButton() {
    AppIconButton(
        isActive = false,
        drawableRes = R.drawable.instruments,
        // todo to res
        contentDescription = "Инструменты",
        // todo
        onClick = { },
    )
}

