package com.snsop.attendance.domain.repo

import com.snsop.attendance.domain.model.UserInfo

interface AuthRepo2 {
    suspend fun login(
        userName: String,
        password: String,
        rememberMe: Boolean
    ): UserInfo

    /**
     * Get current logged-in user
     */
    suspend fun getUserInfo(): UserInfo
}