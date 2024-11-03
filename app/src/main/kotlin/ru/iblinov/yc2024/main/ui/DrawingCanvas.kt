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
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope.Companion.DefaultBlendMode
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.withSaveLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.iblinov.yc2024.R
import ru.iblinov.yc2024.common.model.DrawnPath
import ru.iblinov.yc2024.common.model.DrawnPathType
import ru.iblinov.yc2024.main.mvi.MainAction

private const val DEFAULT_PENCIL_WIDTH = 10f
private const val DEFAULT_ERASE_WIDTH = 100f

@Composable
fun ColumnScope.DrawingCanvas(
    drawnPaths: () -> MutableList<DrawnPath>,
    drawnPathType: DrawnPathType,
    color: Color,
    counterHack: MutableLongState,
    onAction: (MainAction) -> Unit,
) {
    var currentDrawnPath by remember { mutableStateOf(DrawnPath.EMPTY) }

    val paintForSave = remember { Paint() }

    val updatedColor = rememberUpdatedState(color)
    val updatedDrawnPaths = rememberUpdatedState(drawnPaths)
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
                        val type = updatedDrawnPathType.value
                        currentDrawnPath = createDrawnPath(it, type, updatedColor)
                        updatedDrawnPaths.value() += currentDrawnPath

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

                drawContext.canvas.withSaveLayer(this.size.toRect(), paintForSave) {
                    updatedDrawnPaths
                        .value()
                        .forEach {
                            drawPoints(
                                points = it.startPointAsList,
                                pointMode = PointMode.Points,
                                cap = StrokeCap.Round,
                                color = it.color,
                                strokeWidth = it.stroke.width,
                                blendMode = it.blendMode
                            )

                            drawPath(
                                path = it.path,
                                color = it.color,
                                style = it.stroke,
                                blendMode = it.blendMode
                            )
                        }
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

private fun createDrawnPath(
    offset: Offset,
    type: DrawnPathType,
    updatedColor: State<Color>
): DrawnPath {
    val startPointAsList = listOf(offset)
    val path = Path().apply { moveTo(offset.x, offset.y) }
    return when (type) {
        DrawnPathType.PENCIL -> DrawnPath(
            startPointAsList = startPointAsList,
            path = path,
            color = updatedColor.value,
            type = type,
            blendMode = DefaultBlendMode,
            stroke = Stroke(width = DEFAULT_PENCIL_WIDTH)
        )

        DrawnPathType.ERASE -> DrawnPath(
            startPointAsList = startPointAsList,
            path = path,
            color = Color.Transparent,
            type = type,
            blendMode = BlendMode.Clear,
            stroke = Stroke(width = DEFAULT_ERASE_WIDTH)
        )
    }
}