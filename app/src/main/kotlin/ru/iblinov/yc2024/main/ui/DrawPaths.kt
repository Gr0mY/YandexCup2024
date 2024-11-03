package ru.iblinov.yc2024.main.ui

import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import ru.iblinov.yc2024.common.model.DrawnPath

fun DrawScope.drawPaths(
    drawnPaths: List<DrawnPath>,
) {
    drawnPaths
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
