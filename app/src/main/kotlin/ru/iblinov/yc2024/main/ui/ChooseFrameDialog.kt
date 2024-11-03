package ru.iblinov.yc2024.main.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.copy
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toIntSize
import androidx.compose.ui.window.Dialog
import ru.iblinov.yc2024.common.model.DrawnPath
import ru.iblinov.yc2024.common.model.NonEmptyFramesCollection
import ru.iblinov.yc2024.common.theme.AppColors
import ru.iblinov.yc2024.main.mvi.MainAction

val containerModifier = Modifier
    .clip(RoundedCornerShape(20.dp))
    .background(Color(0xFF888888))
    .padding(16.dp)

val frameBoxModifier = Modifier
    .aspectRatio(1f)
    .padding(4.dp)
    .clip(RoundedCornerShape(4.dp))
    .background(Color(0x88FFFFFF))

val verticalGridModifier = Modifier.widthIn(max = 300.dp)

@Composable
fun ChooseFrameDialog(
    allFramesIterator: () -> NonEmptyFramesCollection.FramesIterator,
    onAction: (MainAction) -> Unit
) {
    Dialog(
        onDismissRequest = { onAction(MainAction.ChooseFrame.Dismissed) }
    ) {
        Column(
            modifier = containerModifier,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                // todo to res
                text = "Выбор кадра",
                color = AppColors.White
            )
            SpacerHeight16()

            LazyVerticalGrid(
                modifier = verticalGridModifier,
                columns = GridCells.Fixed(3)
            ) {
                items(
                    items = allFramesIterator().toList()
                ) { node ->
//                    var boxSize by remember { mutableStateOf<IntSize?>(null) }

                    Box(
                        modifier = frameBoxModifier
//                            .onSizeChanged { boxSize = it }
                            .drawBehind {
                                val drawnPaths = node.value.drawnPaths
                                    .map {
                                        it.scale(size.toIntSize())
                                    }
                                drawPaths(drawnPaths)
                            }
                            .clickable { onAction(MainAction.ChooseFrame.FrameChosen(node)) }
                    )
                }
            }
        }
    }
}

private fun DrawnPath.scale(
    canvasSize: IntSize
): DrawnPath {
    val widthRatio = canvasSize.width / this.canvasSize.width.toFloat()
    val heightRatio = canvasSize.height / this.canvasSize.height.toFloat()
    val matrix = Matrix()
    matrix.scale(x = widthRatio, y = heightRatio)

    val path = path.copy()
    path.transform(matrix)

    return DrawnPath(
        startPointAsList = startPointAsList
            .map { offset ->
                Offset(
                    x = offset.x / widthRatio,
                    y = offset.y / heightRatio,
                )
            },
        path = path,
        color = color,
        blendMode = blendMode,
        stroke = Stroke(width = stroke.width * widthRatio),
        canvasSize = canvasSize,
    )
}