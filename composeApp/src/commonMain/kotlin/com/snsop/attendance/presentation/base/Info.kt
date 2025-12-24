package com.snsop.attendance.presentation.base

data class Info(val message: String="", val type: Type = Type.Default) {
    enum class Type() {
        SuccessSignIn,
        SuccessSignUp,
        InvalidUserName,
        InvalidPassword,
        Network,
        ServerError,
        Default
    }
}

