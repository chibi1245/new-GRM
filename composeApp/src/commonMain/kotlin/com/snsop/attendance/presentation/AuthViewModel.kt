package com.snsop.attendance.presentation

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.snsop.attendance.domain.repo.AuthRepo
import com.snsop.attendance.presentation.base.BaseViewModel
import com.snsop.attendance.presentation.navigation.Screens

class AuthViewModel(
    private val repo: AuthRepo
): BaseViewModel(){

    fun login(
        userName: String,
        password: String,
        rememberMe: Boolean,
        backStack: NavBackStack<NavKey>
    ){
        launch {
            repo.login(userName, password, rememberMe)
            backStack.add(Screens.Home)
            backStack.remove(Screens.Login)
        }
    }

}