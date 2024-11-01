package ru.iblinov.yc2024.main.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.iblinov.yc2024.common.model.DrawnPath
import ru.iblinov.yc2024.common.model.DrawnPathsPair
import ru.iblinov.yc2024.main.mvi.MainAction
import ru.iblinov.yc2024.main.mvi.MainState
import ru.iblinov.yc2024.main.viewmodel.MainReducer.additionalColorsClicked
import ru.iblinov.yc2024.main.viewmodel.MainReducer.cancelButtonClicked
import ru.iblinov.yc2024.main.viewmodel.MainReducer.chooseColorClicked
import ru.iblinov.yc2024.main.viewmodel.MainReducer.clearCancelRedoButtons
import ru.iblinov.yc2024.main.viewmodel.MainReducer.closePalette
import ru.iblinov.yc2024.main.viewmodel.MainReducer.colorChosen
import ru.iblinov.yc2024.main.viewmodel.MainReducer.onDragEnd
import ru.iblinov.yc2024.main.viewmodel.MainReducer.redoButtonClicked

class MainViewModel : ViewModel() {

    private val mutableState = MutableStateFlow(MainState())
    val state: StateFlow<MainState>
        get() = mutableState.asStateFlow()

    private val allDrawnPathsPairs: MutableList<DrawnPathsPair> =
        mutableListOf(createDrawnPathsPair())

    private var currentPage = 0

    val drawnPaths: MutableList<DrawnPath>
        get() = allDrawnPathsPairs[currentPage].drawnPaths

    private val deletedDrawnPaths: MutableList<DrawnPath>
        get() = allDrawnPathsPairs[currentPage].deletedDrawnPaths

    fun onAction(action: MainAction) = when (action) {
        MainAction.CancelButtonClicked -> cancelButtonClicked()
        MainAction.RedoButtonClicked -> redoButtonClicked()
        MainAction.BinButtonClicked -> binButtonClicked()
        MainAction.FilePlusButtonClicked -> filePlusButtonClicked()
        MainAction.LayersButtonClicked -> {}
        MainAction.PauseButtonClicked -> {}
        MainAction.PlayButtonClicked -> {}
        MainAction.OnDragEnd -> onDragEnd()
        is MainAction.Palette -> onAction(action)
    }

    private fun onAction(action: MainAction.Palette) = when (action) {
        MainAction.Palette.ChooseColorClicked -> updateState { chooseColorClicked() }
        MainAction.Palette.AdditionalColorsClicked -> updateState { additionalColorsClicked() }
        is MainAction.Palette.ColorChosen -> updateState { closePalette().colorChosen(action.color) }
        MainAction.Palette.OutlineClicked -> updateState { closePalette() }
    }

    private fun cancelButtonClicked() {
        deletedDrawnPaths += drawnPaths.removeLast()

        updateState {
            updateSignal()
                .cancelButtonClicked(drawnPaths)
        }
    }

    private fun redoButtonClicked() {
        val deleted = deletedDrawnPaths.removeLastOrNull()
        if (deleted != null) {
            drawnPaths += deleted

            updateState {
                updateSignal()
                    .redoButtonClicked(deletedDrawnPaths)
            }
        }
    }

    private fun binButtonClicked() {
        deletedDrawnPaths.clear()
        drawnPaths.clear()

        updateState {
            updateSignal()
                .clearCancelRedoButtons()
        }
    }

    private fun filePlusButtonClicked() {
        allDrawnPathsPairs += createDrawnPathsPair()
        currentPage++

        updateState {
            updateSignal()
                .clearCancelRedoButtons()
        }
    }

    private fun onDragEnd() {
        deletedDrawnPaths.clear()

        updateState {
            updateSignal()
                .onDragEnd()
        }
    }

    private fun updateState(body: MainState.() -> MainState) = mutableState.update(body)

    private fun MainState.updateSignal() = copy(updateCanvasSignal = updateCanvasSignal.inc())

    private fun createDrawnPathsPair() = DrawnPathsPair(
        drawnPaths = mutableListOf(),
        deletedDrawnPaths = mutableListOf()
    )
}