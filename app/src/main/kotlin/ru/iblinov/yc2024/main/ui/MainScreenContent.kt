package ru.iblinov.yc2024.main.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import ru.iblinov.yc2024.common.model.DrawnPath
import ru.iblinov.yc2024.common.theme.AppColors
import ru.iblinov.yc2024.common.theme.LivePicturesTheme
import ru.iblinov.yc2024.main.mvi.MainAction
import ru.iblinov.yc2024.main.mvi.MainState
import kotlin.math.roundToInt

@Composable
fun MainScreenContent(
    state: MainState,
    drawnPaths: () -> MutableList<DrawnPath>,
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

                DrawingToolbar(
                    buttons = state.drawingToolbarButtons,
                    areNonPlayingButtonsActive = state.areNonPlayingButtonsActive,
                    onAction = onAction
                )
                DrawingCanvas(
                    drawnPaths = drawnPaths,
                    color = state.palette.selectedColor,
                    counterHack = counterHack,
                    onAction = onAction,
                )
                ControlPanel(
                    currentColor = state.palette.selectedColor,
                    areNonPlayingButtonsActive = state.areNonPlayingButtonsActive,
                    getTopOfControlPanel = { topOfControlPanel.floatValue = it },
                    onAction = onAction
                )

                LaunchedEffect(state.updateCanvasSignal) {
                    counterHack.longValue++
                }
            }

            Palette(state.palette, topOfControlPanel, onAction)
        }

        val speed = state.speed
        if (speed.isChooseSpeedVisible) {
            Dialog(
                onDismissRequest = { onAction(MainAction.ChooseSpeed.Dismissed) }
            ) {
                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(0x55000000))
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        // todo to res
                        text = "Скорость (кадров в секунду)",
                        color = AppColors.White
                    )
                    SpacerHeight16()
                    Text(
                        text = speed.playingFps.toString(),
                        color = AppColors.White,
                        fontSize = 32.sp,
                    )
                    SpacerHeight8()
                    Slider(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        value = speed.selectedStepIndex.toFloat(),
                        onValueChange = {
                            val index = it.roundToInt()
                            onAction(MainAction.ChooseSpeed.StepIndexChosen(index))
                        },
                        valueRange = 0f..(speed.steps.size - 1).toFloat(),
                        steps = speed.steps.lastIndex - 1,
                    )
                    SpacerHeight8()
                    Button(
                        onClick = { onAction(MainAction.ChooseSpeed.Dismissed) }) {
                        Text(
                            // todo to res
                            text = "OK"
                        )
                    }
                }
            }
        }
    }
}
