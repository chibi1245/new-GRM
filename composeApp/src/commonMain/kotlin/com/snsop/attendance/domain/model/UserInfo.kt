package com.snsop.attendance.domain.model

import com.snsop.attendance.domain.model.enums.UserStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserInfo(
    @SerialName("username")
    val userName: String = "",
    @SerialName("email")
    val email: String = "",
    @SerialName("fullName")
    val fullName: String = "",
    @SerialName("id")
    val id: Int = -1,
    @SerialName("role")
    val role: String = "",
    @SerialName("status")
    val status: UserStatus = UserStatus.INACTIVE,
    @SerialName("password")
    val password: String = ""
)