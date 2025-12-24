package com.snsop.attendance.data.repo

import com.snsop.attendance.data.local.AppSettings
import com.snsop.attendance.data.romote.dto.request.LoginRequestDto
import com.snsop.attendance.data.romote.dto.response.LoginDto
import com.snsop.attendance.data.romote.dto.response.UserDto
import com.snsop.attendance.domain.mapper.toDomain
import com.snsop.attendance.domain.model.enums.UserStatus
import com.snsop.attendance.domain.model.UserInfo
import com.snsop.attendance.domain.network.processGetRequest
import com.snsop.attendance.domain.network.processPostRequest
import com.snsop.attendance.domain.repo.AuthRepo
import com.snsop.attendance.utils.isFalse
import com.snsop.attendance.utils.isMinusOne
import com.snsop.attendance.utils.orMinusOne
import io.ktor.client.HttpClient

class AuthRepoImpl(
    private val ktorClient: HttpClient,
    private val settings: AppSettings
) : AuthRepo {
    override suspend fun login(
        userName: String,
        password: String,
        rememberMe: Boolean
    ): UserInfo {
        val response = ktorClient.processPostRequest<LoginDto>(
            url = "mis_auth/auth/mobile/login",
            body = LoginRequestDto(
                userName = userName,
                password = password
            )
        )
        if (response.status != UserStatus.ACTIVE)
            error("User is not active")
        if (response.operationResult.isFalse())
            error(response.errorMsg ?: "Login failed")
        settings.token = response.token
        return getUserInfo().also { user ->
            settings.userInfo = user.copy(
                userName = user.userName.ifBlank { userName },
                id = response.id.orMinusOne().takeIf { user.id.isMinusOne() } ?: user.id,
                password = password.takeIf { rememberMe }.orEmpty(),
                status = response.status.takeIf { user.status.isInactive() } ?: user.status
            )
        }
    }

    override suspend fun getUserInfo(): UserInfo {
        val response = ktorClient.processGetRequest<UserDto>("mis_auth/auth/extract")
        response.data ?: error("User not found")
//        if (response.data.status != StatusEnum.ACTIVE)
//            error("User is not active")
        return response.data.toDomain()
    }
}