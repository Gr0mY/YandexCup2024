package ru.iblinov.yc2024.main.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import ru.iblinov.yc2024.common.theme.AppColors
import ru.iblinov.yc2024.main.mvi.MainAction
import ru.iblinov.yc2024.main.mvi.MainState
import kotlin.math.roundToInt

@Composable
fun ChooseSpeedDialog(
    speed: MainState.Speed,
    onAction: (MainAction) -> Unit
) {
    Dialog(
        onDismissRequest = { onAction(MainAction.ChooseSpeed.Dismissed) }
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(Color(0x55000000))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                // todo to res
                text = "Скорость (кадров в секунду)",
                color = AppColors.White
            )
            SpacerHeight16()
            Text(
                text = speed.playingFps.toString(),
                color = AppColors.White,
                fontSize = 32.sp,
            )
            SpacerHeight8()
            Slider(
                modifier = Modifier.padding(horizontal = 8.dp),
                value = speed.selectedStepIndex.toFloat(),
                onValueChange = {
                    val index = it.roundToInt()
                    onAction(MainAction.ChooseSpeed.StepIndexChosen(index))
                },
                valueRange = 0f..(speed.steps.size - 1).toFloat(),
                steps = speed.steps.lastIndex - 1,
            )
            SpacerHeight8()
            Button(
                onClick = { onAction(MainAction.ChooseSpeed.Dismissed) }) {
                Text(
                    // todo to res
                    text = "OK"
                )
            }
        }
    }
}