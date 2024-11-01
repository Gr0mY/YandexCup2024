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
import ru.iblinov.yc2024.common.theme.LivePicturesTheme
import ru.iblinov.yc2024.main.mvi.MainAction
import ru.iblinov.yc2024.main.mvi.MainState

@Composable
fun MainScreenContent(
    state: MainState,
    drawnPaths: MutableList<DrawnPath>,
    onAction: (MainAction) -> Unit,
) {
    LivePicturesTheme {
        Scaffold { scaffoldPadding ->
            val topOfControlPanel = remember { mutableFloatStateOf(0f) }

            Column(
                modifier = Modifier
                    .padding(scaffoldPadding)
                    .padding(horizontal = 16.dp)
            ) {
                val counterHack = remember { mutableLongStateOf(Long.MIN_VALUE) }

                DrawingToolbar(state.drawingToolbarButtons, onAction)
                DrawingCanvas(
                    drawnPaths = drawnPaths,
                    color = state.palette.selectedColor,
                    counterHack = counterHack,
                    onAction = onAction,
                )
                ControlPanel(
                    currentColor = state.palette.selectedColor,
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
}
