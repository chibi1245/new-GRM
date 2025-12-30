package com.snsop.attendance.presentation.navigation.route

import ThemeExtensions.themeGradient
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.snsop.attendance.grm_presentation.screens.auth.ForgotPasswordScreen
import com.snsop.attendance.presentation.MainViewModel
import com.snsop.attendance.presentation.SignInViewModel
import com.snsop.attendance.presentation.navigation.Screens
import com.snsop.attendance.presentation.navigation.components.entryWithVM
import com.snsop.attendance.grm_presentation.screens.auth.SignInScreen
import com.snsop.attendance.ui.theme.Dimen

fun EntryProviderScope<NavKey>.authRoute(
    mainViewModel: MainViewModel
) {
    entryWithVM<Screens.Login, SignInViewModel>(mainViewModel.backStack) {

        Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = snackBarState) { data ->
                    Snackbar(
                        snackbarData = data,
                        shape = MaterialTheme.shapes.large,
                        modifier = Modifier.padding(Dimen.Standard)
                    )
                }
            },
            modifier = Modifier
                .fillMaxSize()
                .themeGradient(),
            containerColor = Color.Transparent,
            contentWindowInsets = WindowInsets()
        ) { padding ->

            SignInScreen(
                viewModel = viewModel,
                backStack = mainViewModel.backStack,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            )
        }
    }
    entryWithVM<Screens.ForgotPassword, SignInViewModel>(mainViewModel.backStack) {

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .themeGradient(),
            containerColor = Color.Transparent,
            contentWindowInsets = WindowInsets()
        ) { padding ->

            ForgotPasswordScreen(
                viewModel = viewModel, // SAME VM
                backStack = mainViewModel.backStack,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            )
        }
    }
}

