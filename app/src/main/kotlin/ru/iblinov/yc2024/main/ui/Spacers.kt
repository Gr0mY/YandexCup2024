package ru.iblinov.yc2024.main.ui

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SpacerWidth8() {
    Spacer(Modifier.width(8.dp))
}

@Composable
fun SpacerWidth16() {
    Spacer(Modifier.width(16.dp))
}

@Composable
fun SpacerHeight8() {
    Spacer(Modifier.height(8.dp))
}

@Composable
fun SpacerHeight16() {
    Spacer(Modifier.height(16.dp))
}

@Composable
fun RowScope.SpacerWeight1() {
    Spacer(Modifier.weight(1f))
}