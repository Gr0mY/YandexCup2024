package ru.iblinov.yc2024.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.iblinov.yc2024.common.model.DrawnPath
import ru.iblinov.yc2024.common.model.FrameData
import ru.iblinov.yc2024.main.mvi.MainAction
import ru.iblinov.yc2024.main.mvi.MainState
import ru.iblinov.yc2024.main.viewmodel.MainReducer.activePlaying
import ru.iblinov.yc2024.main.viewmodel.MainReducer.additionalColorsClicked
import ru.iblinov.yc2024.main.viewmodel.MainReducer.cancelButtonClicked
import ru.iblinov.yc2024.main.viewmodel.MainReducer.chooseColorClicked
import ru.iblinov.yc2024.main.viewmodel.MainReducer.chooseSpeedHidden
import ru.iblinov.yc2024.main.viewmodel.MainReducer.chooseSpeedShown
import ru.iblinov.yc2024.main.viewmodel.MainReducer.clearCancelRedoButtons
import ru.iblinov.yc2024.main.viewmodel.MainReducer.closePalette
import ru.iblinov.yc2024.main.viewmodel.MainReducer.colorChosen
import ru.iblinov.yc2024.main.viewmodel.MainReducer.onDragEnd
import ru.iblinov.yc2024.main.viewmodel.MainReducer.redoButtonClicked
import ru.iblinov.yc2024.main.viewmodel.MainReducer.speedIndex
import ru.iblinov.yc2024.main.viewmodel.MainReducer.stoppedPlaying
import ru.iblinov.yc2024.main.viewmodel.MainReducer.updateCancelRedoButtons
import ru.iblinov.yc2024.main.viewmodel.MainReducer.updatePlayButton
import java.util.concurrent.TimeUnit

class MainViewModel : ViewModel() {

    private val mutableState = MutableStateFlow(MainState())
    val state: StateFlow<MainState>
        get() = mutableState.asStateFlow()

    private val allFramesData: MutableList<FrameData> =
        mutableListOf(createFrameData())

    private var currentPage = 0

    val drawnPaths: MutableList<DrawnPath>
        get() = allFramesData[currentPage].drawnPaths

    private val deletedDrawnPaths: MutableList<DrawnPath>
        get() = allFramesData[currentPage].deletedDrawnPaths

    private var playingJob: Job? = null

    fun onAction(action: MainAction) = when (action) {
        is MainAction.DrawingToolbar -> onAction(action)
        MainAction.OnDragEnd -> onDragEnd()
        is MainAction.Palette -> onAction(action)
        is MainAction.ChooseSpeed -> onAction(action)
    }

    private fun onAction(action: MainAction.DrawingToolbar) = when (action) {
        MainAction.DrawingToolbar.CancelButtonClicked -> cancelButtonClicked()
        MainAction.DrawingToolbar.RedoButtonClicked -> redoButtonClicked()
        MainAction.DrawingToolbar.BinButtonClicked -> binButtonClicked()
        MainAction.DrawingToolbar.FilePlusButtonClicked -> filePlusButtonClicked()
        MainAction.DrawingToolbar.LayersButtonClicked -> {}
        MainAction.DrawingToolbar.PlayPauseButtonClicked -> startOrStopPlaying()
        MainAction.DrawingToolbar.SpeedClicked -> updateState { chooseSpeedShown() }
    }

    private fun onAction(action: MainAction.Palette) = when (action) {
        MainAction.Palette.ChooseColorClicked -> updateState { chooseColorClicked() }
        MainAction.Palette.AdditionalColorsClicked -> updateState { additionalColorsClicked() }
        is MainAction.Palette.ColorChosen -> updateState { closePalette().colorChosen(action.color) }
        MainAction.Palette.OutlineClicked -> updateState { closePalette() }
    }

    private fun onAction(action: MainAction.ChooseSpeed) = when (action) {
        is MainAction.ChooseSpeed.StepIndexChosen -> updateState { speedIndex(action.index) }
        MainAction.ChooseSpeed.Dismissed -> updateState { chooseSpeedHidden() }
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
        allFramesData.removeAt(currentPage)
        if (allFramesData.isEmpty()) {
            allFramesData += createFrameData()
            currentPage = 0
        } else {
            currentPage--
        }

        updateState {
            updateSignal()
                .updateCancelRedoButtons(
                    isCancelButtonActive = drawnPaths.isNotEmpty(),
                    isRedoButtonActive = deletedDrawnPaths.isNotEmpty()
                )
                .updatePlayButton(isActive = isPlayButtonActive())
        }
    }

    private fun filePlusButtonClicked() {
        allFramesData.add(++currentPage, createFrameData())

        updateState {
            updateSignal()
                .clearCancelRedoButtons()
                .updatePlayButton(isActive = isPlayButtonActive())
        }
    }


    private fun onDragEnd() {
        deletedDrawnPaths.clear()

        updateState {
            updateSignal()
                .onDragEnd()
        }
    }

    private fun startOrStopPlaying() {
        val job = playingJob
        if (job != null) {
            job.cancel()
            playingJob = null
            updateState { stoppedPlaying() }
            return
        }

        playingJob = viewModelScope.launch {
            val savedPage = currentPage
            updateState { activePlaying() }
            try {
                val timeForOneFrame = millisInSecond / state.value.speed.playingFps
                while (true) {
                    delay(timeForOneFrame)

                    val newPage = currentPage + 1
                    currentPage = if (newPage > allFramesData.lastIndex) 0 else newPage

                    updateState { updateSignal() }
                }
            } catch (t: CancellationException) {
                currentPage = savedPage
                updateState { updateSignal() }
            }
        }
    }

    private fun isPlayButtonActive() = allFramesData.size > 1

    private fun updateState(body: MainState.() -> MainState) = mutableState.update(body)

    private fun MainState.updateSignal() = copy(updateCanvasSignal = updateCanvasSignal.inc())

    private fun createFrameData() = FrameData(
        drawnPaths = mutableListOf(),
        deletedDrawnPaths = mutableListOf()
    )

    companion object {
        private val millisInSecond = TimeUnit.SECONDS.toMillis(1)
    }
}