package ru.iblinov.yc2024.main.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableLongState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.iblinov.yc2024.R
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
            Column(
                modifier = Modifier
                    .padding(scaffoldPadding)
                    .padding(horizontal = 16.dp)
            ) {
                val currentColor = remember { mutableStateOf(Color.Blue) }
                val counterHack = remember { mutableLongStateOf(Long.MIN_VALUE) }

                DrawingToolbar(state.drawingToolbarButtons, onAction)
                DrawingCanvas(
                    drawnPaths = drawnPaths,
                    color = currentColor,
                    counterHack = counterHack,
                    onAction = onAction,
                )
                ControlPanel(currentColor, onAction)

                LaunchedEffect(state.updateCanvasSignal) {
                    counterHack.longValue++
                }
            }
        }
    }
}

@Composable
fun ColumnScope.DrawingCanvas(
    drawnPaths: MutableList<DrawnPath>,
    color: State<Color>,
    counterHack: MutableLongState,
    onAction: (MainAction) -> Unit,
) {
    var currentDrawnPath by remember { mutableStateOf(DrawnPath.EMPTY) }

    val pathStroke = remember { Stroke(width = 10f) }

    Box(
        Modifier
            .padding(top = 32.dp)
            .fillMaxWidth()
            .weight(1f)
            .clip(RoundedCornerShape(20.dp))
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        currentDrawnPath = DrawnPath(
                            startPointAsList = listOf(it),
                            path = Path().apply { moveTo(it.x, it.y) },
                            color = color.value
                        )
                        drawnPaths += currentDrawnPath

                        counterHack.longValue++
                    }
                )
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragEnd = {
                        onAction(MainAction.OnDragEnd)
                    },
                    onDrag = { change, _ ->
                        currentDrawnPath.path.lineTo(change.position.x, change.position.y)
                        counterHack.longValue++
                    }
                )
            }
            .drawWithContent {
                counterHack.longValue

                drawContent()

                drawnPaths.forEach {
                    drawPoints(
                        points = it.startPointAsList,
                        pointMode = PointMode.Points,
                        cap = StrokeCap.Round,
                        color = it.color,
                        strokeWidth = 10f,
                    )

                    drawPath(
                        path = it.path,
                        color = it.color,
                        style = pathStroke
                    )
                }
            }
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.background_rectangle),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}

@Preview
@Composable
private fun MainScreenContentPreview() {
    MainScreenContent(
        state = MainState(),
        onAction = {},
        drawnPaths = remember { mutableListOf<DrawnPath>() }
    )
}