package ru.iblinov.yc2024.common.model

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path

class DrawnPath(
    val startPointAsList: List<Offset>,
    val path: Path,
    val color: Color,
) {
    companion object {
        val EMPTY = DrawnPath(
            startPointAsList = listOf(),
            path = Path(),
            color = Color.Transparent
        )
    }
}