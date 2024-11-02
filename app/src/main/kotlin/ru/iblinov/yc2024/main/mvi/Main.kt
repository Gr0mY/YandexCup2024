package ru.iblinov.yc2024.main.mvi

import androidx.compose.ui.graphics.Color
import ru.iblinov.yc2024.common.theme.AppColors

data class MainState(
    val drawingToolbarButtons: DrawingToolbarButtons = DrawingToolbarButtons(),
    val updateCanvasSignal: Long = Long.MIN_VALUE,
    val palette: Palette = Palette(),
    val playingFps: Int = INITIAL_FPS,
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

    companion object {
        private const val INITIAL_FPS = 30
    }
}

sealed interface MainAction {

    data object CancelButtonClicked : MainAction

    data object RedoButtonClicked : MainAction

    data object BinButtonClicked : MainAction

    data object FilePlusButtonClicked : MainAction

    data object LayersButtonClicked : MainAction

    data object PauseButtonClicked : MainAction

    data object PlayButtonClicked : MainAction

    data object OnDragEnd : MainAction

    sealed interface Palette : MainAction {

        data object ChooseColorClicked : Palette

        data object AdditionalColorsClicked : Palette

        data object OutlineClicked : Palette

        data class ColorChosen(
            val color: Color
        ) : Palette
    }
}
