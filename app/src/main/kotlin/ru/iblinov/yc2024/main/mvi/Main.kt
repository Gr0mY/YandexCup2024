package ru.iblinov.yc2024.main.mvi

data class MainState(
    val drawingToolbarButtons: DrawingToolbarButtons = DrawingToolbarButtons(),
    val updateCanvasSignal: Long = Long.MIN_VALUE,
) {
    data class DrawingToolbarButtons(
        val isCancelButtonActive: Boolean = false,
        val isRedoButtonActive: Boolean = false,
        val isPauseButtonActive: Boolean = false,
        val isPlayButtonActive: Boolean = false,
    )
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
}
