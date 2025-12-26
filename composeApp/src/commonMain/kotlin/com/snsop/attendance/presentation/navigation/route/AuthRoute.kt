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
import com.snsop.attendance.presentation.AuthViewModel
import com.snsop.attendance.presentation.MainViewModel
import com.snsop.attendance.presentation.navigation.Screens
import com.snsop.attendance.presentation.navigation.components.entryWithVM
import com.snsop.attendance.presentation.screens.auth.LoginScreen
import com.snsop.attendance.ui.theme.Dimen
import com.snsop.attendance.utils.log

fun EntryProviderScope<NavKey>.authRoute(
    mainViewModel: MainViewModel
) {
    entryWithVM<Screens.Login, AuthViewModel>(mainViewModel.backStack) {
        val userInfo = mainViewModel.settings.userInfo
        userInfo.log("userInfo")
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
            modifier = Modifier.fillMaxSize()
                .themeGradient(),
            containerColor = Color.Transparent,
            contentWindowInsets = WindowInsets()
        ) {

            LoginScreen(
                oldUserName = userInfo.userName,
                oldPassword = userInfo.password,
                onSignIn = { userName, pass, rememberMe ->
                    viewModel.login(
                        userName = userName,
                        password = pass,
                        rememberMe = rememberMe,
                        backStack = mainViewModel.backStack
                    )
                },
                modifier = Modifier.fillMaxSize().padding(it)
            )
        }
    }
}