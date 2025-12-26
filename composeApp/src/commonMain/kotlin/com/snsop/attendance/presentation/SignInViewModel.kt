package com.snsop.attendance.presentation
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

import com.snsop.attendance.presentation.base.BaseViewModel
import com.snsop.attendance.presentation.navigation.Screens
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.snsop.attendance.domain.repo.AuthRepo2

class SignInViewModel(
    private val authRepo: AuthRepo2
) : BaseViewModel() {

    var complainantRememberMe by mutableStateOf(false)
        private set

    var adminRememberMe by mutableStateOf(false)
        private set

    var obscureText by mutableStateOf(true)
        private set

    var loader by mutableStateOf(false)
        private set

    var mobileComplainant by mutableStateOf("")
    var pinComplainant by mutableStateOf("")

    var userAdmin by mutableStateOf("")
    var pinAdmin by mutableStateOf("")

    fun onComplainantRememberMeChange(value: Boolean) {
        complainantRememberMe = value
    }

    fun onAdminRememberMeChange(value: Boolean) {
        adminRememberMe = value
    }

    fun togglePasswordVisibility() {
        obscureText = !obscureText
    }

    fun signInComplainant(backStack: NavBackStack<NavKey>) {
        signIn(
            identifier = mobileComplainant,
            password = pinComplainant,
            rememberMe = complainantRememberMe,
            backStack = backStack
        )
    }

    fun signInAdmin(backStack: NavBackStack<NavKey>) {
        signIn(
            identifier = userAdmin,
            password = pinAdmin,
            rememberMe = adminRememberMe,
            backStack = backStack
        )
    }

    private fun signIn(
        identifier: String,
        password: String,
        rememberMe: Boolean,
        backStack: NavBackStack<NavKey>
    ) {
        launch {
            loader = true
            authRepo.login(identifier, password, rememberMe)
            loader = false

            backStack.add(Screens.Home)
            backStack.remove(Screens.Login)
        }
    }
}
