package ru.iblinov.yc2024.main

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.iblinov.yc2024.main.ui.MainScreenContent
import ru.iblinov.yc2024.main.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        window?.also { window ->
            window.statusBarColor = Color.TRANSPARENT

            val controller = WindowInsetsControllerCompat(window, window.decorView)
            controller.isAppearanceLightStatusBars = false
            controller.isAppearanceLightNavigationBars = false
        }

        setContent {
            val state by viewModel.state.collectAsStateWithLifecycle()
            MainScreenContent(
                state = state,
                allFramesIterator = { viewModel.allFramesIterator },
                drawnPaths = { viewModel.drawnPaths },
                onAction = viewModel::onAction,
            )
        }
    }
}