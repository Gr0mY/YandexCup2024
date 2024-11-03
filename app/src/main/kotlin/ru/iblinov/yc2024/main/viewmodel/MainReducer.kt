package ru.iblinov.yc2024.main.viewmodel

import androidx.compose.ui.graphics.Color
import ru.iblinov.yc2024.common.model.DrawnPath
import ru.iblinov.yc2024.common.model.DrawnPathType
import ru.iblinov.yc2024.main.mvi.MainState

object MainReducer {

    fun MainState.updateSignal() = copy(
        updateCanvasSignal = updateCanvasSignal.inc()
    )

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

    fun MainState.clearCancelRedoButtons() = copy(
        drawingToolbarButtons = drawingToolbarButtons.copy(
            isCancelButtonActive = false,
            isRedoButtonActive = false,
        )
    )

    fun MainState.updatePlayButton(isActive: Boolean) = copy(
        drawingToolbarButtons = drawingToolbarButtons.copy(
            isPlayButtonActive = isActive
        )
    )

    fun MainState.updateCancelRedoButtons(
        isCancelButtonActive: Boolean,
        isRedoButtonActive: Boolean,
    ) = copy(
        drawingToolbarButtons = drawingToolbarButtons.copy(
            isCancelButtonActive = isCancelButtonActive,
            isRedoButtonActive = isRedoButtonActive,
        )
    )

    fun MainState.onDragEnd() = copy(
        drawingToolbarButtons = drawingToolbarButtons.copy(
            isCancelButtonActive = true,
            isRedoButtonActive = false,
        )
    )

    fun MainState.chooseColorClicked() = copy(
        palette = palette.copy(
            areCommonColorsVisible = !palette.areCommonColorsVisible,
            areAdditionalColorsVisible = false,
        )
    )

    fun MainState.additionalColorsClicked() = copy(
        palette = palette.copy(
            areAdditionalColorsVisible = !palette.areAdditionalColorsVisible
        )
    )

    fun MainState.colorChosen(color: Color) = copy(
        palette = palette.copy(
            selectedColor = color,
        )
    )

    fun MainState.closePalette() = copy(
        palette = palette.copy(
            areCommonColorsVisible = false,
            areAdditionalColorsVisible = false,
        )
    )

    fun MainState.activePlaying() = copy(
        drawingToolbarButtons = drawingToolbarButtons.copy(
            isPlayButtonActive = false,
            isPauseButtonActive = true,
        ),
        canDraw = false,
    )

    fun MainState.stoppedPlaying() = copy(
        drawingToolbarButtons = drawingToolbarButtons.copy(
            isPlayButtonActive = true,
            isPauseButtonActive = false,
        ),
        canDraw = true,
    )

    fun MainState.chooseSpeedShown() = copy(
        speed = speed.copy(
            isChooseSpeedVisible = true
        )
    )

    fun MainState.chooseSpeedHidden() = copy(
        speed = speed.copy(
            isChooseSpeedVisible = false
        )
    )

    fun MainState.speedIndex(index: Int) = copy(
        speed = speed.copy(
            selectedStepIndex = index
        )
    )

    fun MainState.selectedDrawnPathType(
        type: DrawnPathType
    ) = copy(
        drawnPathType = type
    )

    fun MainState.chooseFrameShown() = copy(
        isChooseFrameVisible = true,
    )

    fun MainState.chooseFrameHidden() = copy(
        isChooseFrameVisible = false,
    )
}