package com.snsop.attendance.presentation.navigation

import ThemeExtensions.themeGradient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.scene.DialogSceneStrategy
import androidx.navigation3.ui.NavDisplay
import com.snsop.attendance.presentation.MainViewModel
import com.snsop.attendance.presentation.navigation.components.InitBaseVM
import com.snsop.attendance.presentation.navigation.route.*
import com.snsop.attendance.ui.theme.Dimen
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun SetupNavDisplay(modifier: Modifier = Modifier) {
    val viewModel = koinViewModel<MainViewModel>()
    val dialogStrategy = remember { DialogSceneStrategy<NavKey>() }

    InitBaseVM(
        backStack = viewModel.backStack,
        viewModel = viewModel,
    ) { snackBarState ->
        Scaffold(
            modifier = modifier.fillMaxSize()
                .themeGradient(),
            // Remove the background from Scaffold and let the Box handle it
            containerColor = Color.Transparent, // Important!
            contentColor = MaterialTheme.colorScheme.onBackground,
            snackbarHost = {
                SnackbarHost(hostState = snackBarState) { data ->
                    Snackbar(
                        snackbarData = data,
                        shape = MaterialTheme.shapes.large,
                        modifier = Modifier.padding(Dimen.Standard)
                    )
                }
            },
            content = {
                NavDisplay(
                    backStack = backStack,
                    sceneStrategy = dialogStrategy,
                    entryProvider = entryProvider {
                        splashRoute(  mainViewModel =  viewModel)
                       dialogRoute(
                            viewModel = viewModel
                       )
                        authRoute(
                            mainViewModel = viewModel
                        )
                        homeRoute(
                            viewModel = viewModel
                        )
                        attendanceRoute(
                            viewModel = viewModel
                        )
                    }
                )
            }
        )
    }
}

