package ru.iblinov.yc2024.main.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.iblinov.yc2024.common.model.DrawnPath
import ru.iblinov.yc2024.common.model.NonEmptyFramesCollection
import ru.iblinov.yc2024.common.theme.LivePicturesTheme
import ru.iblinov.yc2024.main.mvi.MainAction
import ru.iblinov.yc2024.main.mvi.MainState

@Composable
fun MainScreenContent(
    state: MainState,
    allFramesIterator: () -> NonEmptyFramesCollection.FramesIterator,
    previousDrawnPaths: () -> MutableList<DrawnPath>?,
    drawnPaths: () -> MutableList<DrawnPath>,
    onAction: (MainAction) -> Unit,
) {
    LivePicturesTheme {
        CommonScaffold(
            state = state,
            previousDrawnPaths = previousDrawnPaths,
            drawnPaths = drawnPaths,
            onAction = onAction
        )

        val speed = state.speed
        if (speed.isChooseSpeedVisible) {
            ChooseSpeedDialog(speed, onAction)
        }

        if (state.isChooseFrameVisible) {
            ChooseFrameDialog(
                allFramesIterator = allFramesIterator,
                onAction = onAction
            )
        }
    }
}

@Composable
private fun CommonScaffold(
    state: MainState,
    previousDrawnPaths: () -> MutableList<DrawnPath>?,
    drawnPaths: () -> MutableList<DrawnPath>,
    onAction: (MainAction) -> Unit
) {
    Scaffold { scaffoldPadding ->
        val topOfControlPanel = remember { mutableFloatStateOf(0f) }

        Column(
            modifier = Modifier
                .padding(scaffoldPadding)
                .padding(horizontal = 16.dp)
        ) {
            val counterHack = remember { mutableLongStateOf(Long.MIN_VALUE) }

            DrawingToolbar(
                buttons = state.drawingToolbarButtons,
                areNonPlayingButtonsActive = state.canDraw,
                onAction = onAction
            )
            DrawingCanvas(
                canDraw = state.canDraw,
                previousDrawnPaths = previousDrawnPaths,
                drawnPaths = drawnPaths,
                color = state.palette.selectedColor,
                counterHack = counterHack,
                drawnPathType = state.drawnPathType,
                onAction = onAction,
            )
            ControlPanel(
                currentColor = state.palette.selectedColor,
                areNonPlayingButtonsActive = state.canDraw,
                drawnPathType = state.drawnPathType,
                getTopOfControlPanel = { topOfControlPanel.floatValue = it },
                onAction = onAction
            )

            LaunchedEffect(state.updateCanvasSignal) {
                counterHack.longValue++
            }
        }

        Palette(state.palette, topOfControlPanel, onAction)
    }
}
