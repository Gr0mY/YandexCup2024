package ru.iblinov.yc2024.main.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.iblinov.yc2024.R
import ru.iblinov.yc2024.main.mvi.MainAction
import ru.iblinov.yc2024.main.mvi.MainState

val drawingToolbarModifier = Modifier
    .fillMaxWidth()
    .padding(top = 16.dp)

@Composable
fun DrawingToolbar(
    buttons: MainState.DrawingToolbarButtons,
    areNonPlayingButtonsActive: Boolean,
    onAction: (MainAction) -> Unit,
) {
    Row(
        modifier = drawingToolbarModifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CancelButton(
            isCancelButtonActive = buttons.isCancelButtonActive && areNonPlayingButtonsActive,
            onAction = onAction
        )
        SpacerWidth8()
        RedoButton(
            isRedoButtonActive = buttons.isRedoButtonActive && areNonPlayingButtonsActive,
            onAction = onAction
        )

        SpacerWeight1()
        DrawingToolbarCenterButtons(
            areNonPlayingButtonsActive = areNonPlayingButtonsActive,
            onAction = onAction
        )
        SpacerWeight1()

        SpeedButton(
            isActive = areNonPlayingButtonsActive,
            onAction = onAction
        )
        SpacerWidth16()
        PlayPauseButton(
            isPlayButtonActive = buttons.isPlayButtonActive,
            isPauseButtonActive = buttons.isPauseButtonActive,
            onAction = onAction
        )
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
        contentDescription = stringResource(R.string.cancel),
        onClick = { onAction(MainAction.DrawingToolbar.CancelButtonClicked) },
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
        contentDescription = stringResource(R.string.repeat),
        onClick = { onAction(MainAction.DrawingToolbar.RedoButtonClicked) },
    )
}

@Composable
private fun DrawingToolbarCenterButtons(
    areNonPlayingButtonsActive: Boolean,
    onAction: (MainAction) -> Unit,
) {
    AppIconButton(
        isActive = areNonPlayingButtonsActive,
        drawableRes = R.drawable.bin,
        contentDescription = stringResource(R.string.delete_frame),
        onClick = { onAction(MainAction.DrawingToolbar.BinButtonClicked) },
    )

    SpacerWidth16()

    AppIconButton(
        isActive = areNonPlayingButtonsActive,
        drawableRes = R.drawable.file_plus,
        contentDescription = stringResource(R.string.add_frame),
        onClick = { onAction(MainAction.DrawingToolbar.FilePlusButtonClicked) },
    )

    SpacerWidth16()

    AppIconButton(
        isActive = areNonPlayingButtonsActive,
        drawableRes = R.drawable.layers,
        contentDescription = stringResource(R.string.layers),
        onClick = { onAction(MainAction.DrawingToolbar.LayersButtonClicked) },
    )
}

@Composable
private fun SpeedButton(
    isActive: Boolean,
    onAction: (MainAction) -> Unit,
) {
    AppIconButton(
        isActive = isActive,
        drawableRes = R.drawable.baseline_speed_32,
        contentDescription = stringResource(R.string.playing_speed),
        onClick = { onAction(MainAction.DrawingToolbar.SpeedClicked) },
    )
}

@Composable
private fun PlayPauseButton(
    isPlayButtonActive: Boolean,
    isPauseButtonActive: Boolean,
    onAction: (MainAction) -> Unit,
) {
    AppIconButton(
        isActive = isPlayButtonActive || isPauseButtonActive,
        drawableRes = when {
            isPauseButtonActive -> R.drawable.property_1_active_1
            else -> R.drawable.property_1_active
        },
        contentDescription = stringResource(R.string.play_stop),
        onClick = { onAction(MainAction.DrawingToolbar.PlayPauseButtonClicked) },
    )
}

