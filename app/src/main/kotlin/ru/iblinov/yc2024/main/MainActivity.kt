package ru.iblinov.yc2024.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.iblinov.yc2024.main.ui.MainScreenContent
import ru.iblinov.yc2024.main.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val state by viewModel.state.collectAsStateWithLifecycle()
            MainScreenContent(
                state = state,
                allFramesIterator = { viewModel.allFramesIterator },
                previousDrawnPaths = { viewModel.previousDrawnPaths },
                drawnPaths = { viewModel.drawnPaths },
                onAction = viewModel::onAction,
            )
        }
    }
}