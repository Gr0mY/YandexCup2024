package ru.iblinov.yc2024.main.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableLongState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope.Companion.DefaultBlendMode
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.withSaveLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import ru.iblinov.yc2024.R
import ru.iblinov.yc2024.common.model.DrawnPath
import ru.iblinov.yc2024.common.model.DrawnPathType
import ru.iblinov.yc2024.main.mvi.MainAction

private const val DEFAULT_PENCIL_WIDTH = 10f
private const val DEFAULT_ERASE_WIDTH = 100f

@Composable
fun ColumnScope.DrawingCanvas(
    canDraw: Boolean,
    previousDrawnPaths: () -> MutableList<DrawnPath>?,
    drawnPaths: () -> MutableList<DrawnPath>,
    drawnPathType: DrawnPathType,
    color: Color,
    counterHack: MutableLongState,
    onAction: (MainAction) -> Unit,
) {
    var currentDrawnPath by remember { mutableStateOf(DrawnPath.EMPTY) }

    val paintForSave = remember { Paint() }

    val updatedCanDraw = rememberUpdatedState(canDraw)
    val updatedColor = rememberUpdatedState(color)
    val updatedDrawnPaths = rememberUpdatedState(drawnPaths)
    val updatedPreviousDrawnPathType = rememberUpdatedState(previousDrawnPaths)
    val updatedDrawnPathType = rememberUpdatedState(drawnPathType)

    Box(
        Modifier
            .padding(top = 32.dp)
            .fillMaxWidth()
            .weight(1f)
            .clip(RoundedCornerShape(20.dp))
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        if (!updatedCanDraw.value) return@detectTapGestures

                        val type = updatedDrawnPathType.value
                        currentDrawnPath = createDrawnPath(it, type, updatedColor, size)
                        updatedDrawnPaths.value() += currentDrawnPath

                        counterHack.longValue++
                    }
                )
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragEnd = {
                        if (!updatedCanDraw.value) return@detectDragGestures

                        onAction(MainAction.OnDragEnd)
                    },
                    onDrag = { change, _ ->
                        if (!updatedCanDraw.value) return@detectDragGestures

                        currentDrawnPath.path.lineTo(change.position.x, change.position.y)
                        counterHack.longValue++
                    }
                )
            }
            .drawWithContent {
                counterHack.longValue

                drawContent()

                drawContext.canvas.withSaveLayer(size.toRect(), paintForSave) {
                    drawPaths(updatedDrawnPaths.value())
                }
            }
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.background_rectangle),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        if (canDraw) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer { alpha = 0.3f }
                    .drawWithContent {
                        counterHack.longValue
                        drawContext.canvas.withSaveLayer(size.toRect(), paintForSave) {
                            updatedPreviousDrawnPathType
                                .value()
                                ?.let(::drawPaths)
                        }
                    }
            )
        }
    }
}

private fun createDrawnPath(
    offset: Offset,
    type: DrawnPathType,
    updatedColor: State<Color>,
    canvasSize: IntSize
): DrawnPath {
    val startPointAsList = listOf(offset)
    val path = Path().apply { moveTo(offset.x, offset.y) }
    return when (type) {
        DrawnPathType.PENCIL -> DrawnPath(
            startPointAsList = startPointAsList,
            path = path,
            color = updatedColor.value,
            blendMode = DefaultBlendMode,
            stroke = Stroke(width = DEFAULT_PENCIL_WIDTH),
            canvasSize = canvasSize,
        )

        DrawnPathType.ERASE -> DrawnPath(
            startPointAsList = startPointAsList,
            path = path,
            color = Color.Transparent,
            blendMode = BlendMode.Clear,
            stroke = Stroke(width = DEFAULT_ERASE_WIDTH),
            canvasSize = canvasSize,
        )
    }
}