package ru.iblinov.yc2024.main.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.FloatState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import ru.iblinov.yc2024.R
import ru.iblinov.yc2024.common.theme.AppColors
import ru.iblinov.yc2024.main.mvi.MainAction
import ru.iblinov.yc2024.main.mvi.MainState

private val paletteGroupModifier = Modifier
    .clip(RoundedCornerShape(4.dp))
    .border(1.dp, Color(0x29555454))
    .blur(20.dp)
    .background(Color(0x24000000))
    .clickable { }

@Composable
fun Palette(
    palette: MainState.Palette,
    topOfControlPanel: FloatState,
    onAction: (MainAction) -> Unit
) {
    if (palette.areCommonColorsVisible) {
        Box(Modifier.clickable { onAction(MainAction.Palette.OutlineClicked) }) {
            val height = with(LocalDensity.current) { topOfControlPanel.floatValue.toDp() + 16.dp }
            Column(
                modifier = Modifier
                    .height(height)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (palette.areAdditionalColorsVisible) {
                    AdditionalColors(onAction, palette)
                    SpacerHeight8()
                }

                CommonButtons(palette, onAction)
            }
        }
    }
}

@Composable
private fun AdditionalColors(
    onAction: (MainAction) -> Unit,
    palette: MainState.Palette
) {
    Box {
        Box(
            modifier = Modifier
                .matchParentSize()
                .then(paletteGroupModifier),
        )
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(
                space = 16.dp,
                alignment = Alignment.CenterVertically
            )
        ) {
            palette.additionalColors.forEach { colorsRow ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(
                        space = 16.dp,
                        alignment = Alignment.CenterHorizontally
                    ),
                ) {
                    colorsRow.forEach { color ->
                        ColorButton(
                            color = color,
                            onAction = { onAction(MainAction.Palette.ColorChosen(color)) }
                        )
                    }
                }

            }
        }
    }
}

@Composable
private fun CommonButtons(
    palette: MainState.Palette,
    onAction: (MainAction) -> Unit
) {
    Box {
        Box(
            modifier = Modifier
                .matchParentSize()
                .then(paletteGroupModifier),
        )
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(
                space = 16.dp,
                alignment = Alignment.CenterHorizontally
            ),
        ) {
            AdditionalColorsButton(palette.areAdditionalColorsVisible, onAction)
            palette.commonColors.forEach { color ->
                ColorButton(color) { onAction(MainAction.Palette.ColorChosen(color)) }
            }
        }
    }
}

@Composable
private fun AdditionalColorsButton(
    isChosen: Boolean,
    onAction: (MainAction) -> Unit
) {
    AppIconButton(
        isActive = true,
        drawableRes = R.drawable.color__palette,
        contentDescription = "Другие цвета",
        tint = if (isChosen) AppColors.GreenSelected else AppColors.White,
        onClick = { onAction(MainAction.Palette.AdditionalColorsClicked) }
    )
}