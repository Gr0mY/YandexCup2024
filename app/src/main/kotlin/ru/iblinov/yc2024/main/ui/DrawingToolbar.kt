package ru.iblinov.yc2024.main.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.iblinov.yc2024.R
import ru.iblinov.yc2024.main.mvi.MainAction
import ru.iblinov.yc2024.main.mvi.MainState

val drawingToolbarModifier = Modifier
    .fillMaxWidth()
    .padding(top = 16.dp)

@Composable
fun DrawingToolbar(
    drawingToolbarButtons: MainState.DrawingToolbarButtons,
    onAction: (MainAction) -> Unit,
) {
    Row(
        modifier = drawingToolbarModifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CancelButton(drawingToolbarButtons.isCancelButtonActive, onAction)
        SpacerWidth8()
        RedoButton(drawingToolbarButtons.isRedoButtonActive, onAction)

        SpacerWeight1()
        DrawingToolbarCenterButtons(onAction)
        SpacerWeight1()

        PauseButton(drawingToolbarButtons.isPauseButtonActive, onAction)
        SpacerWidth16()
        PlayButton(drawingToolbarButtons.isPlayButtonActive, onAction)
    }
}

@Composable
private fun CancelButton(
    isCancelButtonActive: Boolean,
    onAction: (MainAction) -> Unit,
) {
    AppIconButton(
        isActive = isCancelButtonActive,
        drawableRes = R.drawable.property_1_right_active,
        // todo to res
        contentDescription = "Отменить",
        onClick = { onAction(MainAction.CancelButtonClicked) },
    )
}

@Composable
private fun RedoButton(
    isRedoButtonActive: Boolean,
    onAction: (MainAction) -> Unit,
) {
    AppIconButton(
        isActive = isRedoButtonActive,
        drawableRes = R.drawable.property_1_left_active,
        // todo to res
        contentDescription = "Повторить",
        onClick = { onAction(MainAction.RedoButtonClicked) },
    )
}

@Composable
private fun DrawingToolbarCenterButtons(
    onAction: (MainAction) -> Unit,
) {
    AppIconButton(
        drawableRes = R.drawable.bin,
        // todo to res
        contentDescription = "Удалить кадр",
        onClick = { onAction(MainAction.BinButtonClicked) },
    )

    SpacerWidth16()

    AppIconButton(
        drawableRes = R.drawable.file_plus,
        // todo to res
        contentDescription = "Создать кадр",
        onClick = { onAction(MainAction.FilePlusButtonClicked) },
    )

    SpacerWidth16()

    AppIconButton(
        drawableRes = R.drawable.layers,
        // todo to res
        contentDescription = "Слои",
        onClick = { onAction(MainAction.LayersButtonClicked) },
    )
}

@Composable
private fun PauseButton(
    isPauseButtonActive: Boolean,
    onAction: (MainAction) -> Unit,
) {
    AppIconButton(
        isActive = isPauseButtonActive,
        drawableRes = R.drawable.property_1_active_1,
        // todo to res
        contentDescription = "Остановить",
        onClick = { onAction(MainAction.PauseButtonClicked) },
    )
}

@Composable
private fun PlayButton(
    isPlayButtonActive: Boolean,
    onAction: (MainAction) -> Unit,
) {
    AppIconButton(
        isActive = isPlayButtonActive,
        drawableRes = R.drawable.property_1_active,
        // todo to res
        contentDescription = "Запустить",
        onClick = { onAction(MainAction.PlayButtonClicked) },
    )
}

