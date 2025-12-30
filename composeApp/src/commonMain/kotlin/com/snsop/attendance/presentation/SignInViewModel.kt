package com.snsop.attendance.presentation

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.snsop.attendance.domain.repo.AuthRepo2
import com.snsop.attendance.presentation.base.BaseViewModel
import com.snsop.attendance.presentation.navigation.Screens
import kotlinx.coroutines.delay

class SignInViewModel(
    private val authRepo: AuthRepo2
) : BaseViewModel() {

    /* ---------------- SIGN IN ---------------- */

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

    /* ---------------- FORGOT PASSWORD ---------------- */

    val forgotMobileState = TextFieldState()

    var forgotLoading by mutableStateOf(false)
        private set

    fun sendForgotPasswordCode() {
        val mobile = forgotMobileState.text.toString()

        if (mobile.isBlank()) {
            showError("Please enter your mobile number")
            return
        }

        launch {
            forgotLoading = true
            delay(2000) // call real API later
            forgotLoading = false
            showSuccess("Reset code sent successfully")
        }
    }

    /* ---------------- UI HELPERS ---------------- */

    fun togglePasswordVisibility() {
        obscureText = !obscureText
    }

    fun onComplainantRememberMeChange(value: Boolean) {
        complainantRememberMe = value
    }

    fun onAdminRememberMeChange(value: Boolean) {
        adminRememberMe = value
    }

    /* ---------------- LOGIN ---------------- */

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

            authRepo.login(
                userName = identifier,
                password = password,
                rememberMe = rememberMe
            )

            loader = false

            val oldStack = backStack.toList()
            backStack.add(Screens.Home)
            backStack.removeAll(oldStack)
        }
    }
}
