package ru.iblinov.yc2024.main.viewmodel

import ru.iblinov.yc2024.common.model.DrawnPath
import ru.iblinov.yc2024.main.mvi.MainState

object MainReducer {

    fun MainState.cancelButtonClicked(drawnPaths: MutableList<DrawnPath>) = copy(
        drawingToolbarButtons = drawingToolbarButtons.copy(
            isCancelButtonActive = drawnPaths.isNotEmpty(),
            isRedoButtonActive = true,
        )
    )

    fun MainState.redoButtonClicked(deletedDrawnPaths: MutableList<DrawnPath>) = copy(
        drawingToolbarButtons = drawingToolbarButtons.copy(
            isCancelButtonActive = true,
            isRedoButtonActive = deletedDrawnPaths.isNotEmpty()
        )
    )

    fun MainState.binButtonClicked() = copy(
        drawingToolbarButtons = drawingToolbarButtons.copy(
            isCancelButtonActive = false,
            isRedoButtonActive = false,
        )
    )

    fun MainState.onDragEnd() = copy(
        drawingToolbarButtons = drawingToolbarButtons.copy(
            isCancelButtonActive = true,
            isRedoButtonActive = false,
        )
    )
}