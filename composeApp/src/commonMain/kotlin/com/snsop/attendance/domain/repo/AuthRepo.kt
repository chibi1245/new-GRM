package com.snsop.attendance.domain.repo

import com.snsop.attendance.domain.model.UserInfo

interface AuthRepo {
    suspend fun login(userName: String, password: String, rememberMe: Boolean): UserInfo

    suspend fun getUserInfo(): UserInfo
}