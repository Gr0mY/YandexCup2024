package ru.iblinov.yc2024.common.model

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.DefaultTintBlendMode

class DrawnPath(
    val startPointAsList: List<Offset>,
    val path: Path,
    val color: Color,
    val type: DrawnPathType,
    val blendMode: BlendMode,
    val stroke: Stroke,
) {
    companion object {
        val EMPTY = DrawnPath(
            startPointAsList = listOf(),
            path = Path(),
            color = Color.Transparent,
            type = DrawnPathType.PENCIL,
            blendMode = DefaultTintBlendMode,
            stroke = Stroke(width = 10f)
        )
    }
}

enum class DrawnPathType {
    PENCIL,
    ERASE,
}
