package ru.iblinov.yc2024.main.mvi

import androidx.compose.ui.graphics.Color
import ru.iblinov.yc2024.common.model.DrawnPathType
import ru.iblinov.yc2024.common.theme.AppColors

data class MainState(
    val drawingToolbarButtons: DrawingToolbarButtons = DrawingToolbarButtons(),
    val updateCanvasSignal: Long = Long.MIN_VALUE,
    val drawnPathType: DrawnPathType = DrawnPathType.PENCIL,
    val palette: Palette = Palette(),
    val speed: Speed = Speed(),
    val areNonPlayingButtonsActive: Boolean = true,
) {
    data class DrawingToolbarButtons(
        val isCancelButtonActive: Boolean = false,
        val isRedoButtonActive: Boolean = false,
        val isPauseButtonActive: Boolean = false,
        val isPlayButtonActive: Boolean = false,
    )

    data class Palette(
        val selectedColor: Color = AppColors.CommonColors.last(),
        val commonColors: List<Color> = AppColors.CommonColors,
        val additionalColors: List<List<Color>> = AppColors.AdditionalColors,
        val areCommonColorsVisible: Boolean = false,
        val areAdditionalColorsVisible: Boolean = false,
    )

    data class Speed(
        val isChooseSpeedVisible: Boolean = false,
        val steps: List<Int> = availableFpsList,
        val selectedStepIndex: Int = steps.lastIndex - 1,
    ) {
        val playingFps: Int = steps[selectedStepIndex]
    }

    companion object {
        private val availableFpsList = listOf(1, 2, 4, 8, 10, 20, 30, 60)
    }
}

sealed interface MainAction {

    data object OnDragEnd : MainAction

    sealed interface DrawingToolbar : MainAction {

        data object CancelButtonClicked : DrawingToolbar

        data object RedoButtonClicked : DrawingToolbar

        data object BinButtonClicked : DrawingToolbar

        data object FilePlusButtonClicked : DrawingToolbar

        data object LayersButtonClicked : DrawingToolbar

        data object SpeedClicked : DrawingToolbar

        data object PlayPauseButtonClicked : DrawingToolbar
    }

    sealed interface ControlPanel : MainAction {

        data object PencilClicked : ControlPanel

        data object EraseClicked : ControlPanel
    }

    sealed interface Palette : MainAction {

        data object ChooseColorClicked : Palette

        data object AdditionalColorsClicked : Palette

        data object OutlineClicked : Palette

        data class ColorChosen(
            val color: Color
        ) : Palette
    }

    sealed interface ChooseSpeed : MainAction {

        data object Dismissed : ChooseSpeed

        data class StepIndexChosen(
            val index: Int,
        ) : ChooseSpeed
    }
}
