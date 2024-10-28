package ru.iblinov.yc2024.main.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.iblinov.yc2024.common.model.DrawnPath
import ru.iblinov.yc2024.main.mvi.MainAction
import ru.iblinov.yc2024.main.mvi.MainState
import ru.iblinov.yc2024.main.viewmodel.MainReducer.binButtonClicked
import ru.iblinov.yc2024.main.viewmodel.MainReducer.cancelButtonClicked
import ru.iblinov.yc2024.main.viewmodel.MainReducer.onDragEnd
import ru.iblinov.yc2024.main.viewmodel.MainReducer.redoButtonClicked

class MainViewModel : ViewModel() {

    private val mutableState = MutableStateFlow(MainState())
    val state: StateFlow<MainState>
        get() = mutableState.asStateFlow()

    val drawnPaths = mutableListOf<DrawnPath>()
    private val deletedDrawnPaths = mutableListOf<DrawnPath>()

    fun onAction(action: MainAction): Any = when (action) {
        MainAction.CancelButtonClicked -> cancelButtonClicked()
        MainAction.RedoButtonClicked -> redoButtonClicked()
        MainAction.BinButtonClicked -> binButtonClicked()
        MainAction.FilePlusButtonClicked -> filePlusButtonClicked()
        MainAction.LayersButtonClicked -> {}
        MainAction.PauseButtonClicked -> {}
        MainAction.PlayButtonClicked -> {}
        MainAction.OnDragEnd -> onDragEnd()
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
                .binButtonClicked()
        }
    }

    private fun filePlusButtonClicked() {
        // todo добавить список drawnPaths и deletedDrawnPaths
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
}