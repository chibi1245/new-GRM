package com.snsop.attendance.presentation.navigation.route

import androidx.compose.runtime.LaunchedEffect
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.snsop.attendance.presentation.MainViewModel
import com.snsop.attendance.presentation.navigation.Screens
import com.snsop.attendance.grm_presentation.screens.SplashScreen
import kotlinx.coroutines.delay

fun EntryProviderScope<NavKey>.splashRoute(
    mainViewModel: MainViewModel
) {
    entry<Screens.Splash> {

        SplashScreen(
            isUserLoggedIn = mainViewModel.isUserLoggedIn,
            onNavigateToLogin = {
                mainViewModel.backStack.add(Screens.Login)
                mainViewModel.backStack.remove(Screens.Splash)
            },

        )
    }
}
