package ru.iblinov.yc2024.main.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.iblinov.yc2024.R
import ru.iblinov.yc2024.main.mvi.MainAction

@Composable
fun ControlPanel(
    currentColor: MutableState<Color>,
    onAction: (MainAction) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 22.dp)
            .navigationBarsPadding(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.CenterHorizontally
        ),
    ) {
        PencilButton()
        BrushButton()
        EraseButton()
        InstrumentsButton()
        ColorButton(currentColor)
    }
}

@Composable
private fun PencilButton() {
    AppIconButton(
        drawableRes = R.drawable.pencil__edit__create,
        // todo to res
        contentDescription = "Карандаш",
        // todo
        onClick = { },
    )
}

@Composable
private fun BrushButton() {
    AppIconButton(
        drawableRes = R.drawable.brush__edit__create,
        // todo to res
        contentDescription = "Кисть",
        // todo
        onClick = { },
    )
}

@Composable
private fun EraseButton() {
    AppIconButton(
        drawableRes = R.drawable.erase,
        // todo to res
        contentDescription = "Ластик",
        // todo
        onClick = { },
    )
}

@Composable
private fun InstrumentsButton() {
    AppIconButton(
        drawableRes = R.drawable.instruments,
        // todo to res
        contentDescription = "Инструменты",
        // todo
        onClick = { },
    )
}

@Composable
private fun ColorButton(currentColor: MutableState<Color>) {
    Box(
        modifier = Modifier
            .size(32.dp)
            .padding(2.dp)
            .clip(CircleShape)
            .background(currentColor.value)
    )
}