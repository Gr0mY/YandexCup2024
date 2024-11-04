package ru.iblinov.yc2024.common.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

data class AppColors(
    val primary: Color,
    val onPrimary: Color,
    val secondaryContainer: Color,
    val background: Color,
    val buttonActive: Color,
    val buttonInactive: Color,
    val buttonSelected: Color,
    val primaryTextColor: Color,
    val dialogBackground: Color,
    val dialogItemBackground: Color,
    val paletteGroupBackground: Color,
    val paletteGroupBorder: Color,
)

val lightAppColors = AppColors(
    primary = Color(0xFF00C9FB),
    onPrimary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFCCF3FF),
    background = Color(0xFFFFFFFF),
    buttonActive = Color(0xFF000000),
    buttonInactive = Color(0xFF8B8B8B),
    buttonSelected = Color(0xFFFF3D00),
    primaryTextColor = Color(0xFF000000),
    dialogBackground = Color(0xFFCCCCCC),
    dialogItemBackground = Color(0xFFBBBBBB),
    paletteGroupBackground = Color(0x99DDDDDD),
    paletteGroupBorder = Color(0x29555454),
)

val darkAppColors = AppColors(
    primary = Color(0xFFEDCAFF),
    onPrimary = Color(0xFF000000),
    secondaryContainer = Color(0xFF746888),
    background = Color(0xFF000000),
    buttonActive = Color(0xFFFFFFFF),
    buttonInactive = Color(0xFF8B8B8B),
    buttonSelected = Color(0xFFA8DB10),
    primaryTextColor = Color(0xFFFFFFFF),
    dialogBackground = Color(0xFF333333),
    dialogItemBackground = Color(0xFFBBBBBB),
    paletteGroupBackground = Color(0x99CCCCCC),
    paletteGroupBorder = Color(0x29555454),
)

val LocalAppColors = compositionLocalOf { lightAppColors }
