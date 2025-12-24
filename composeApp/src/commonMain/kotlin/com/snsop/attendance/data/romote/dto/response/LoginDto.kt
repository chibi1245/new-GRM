package com.snsop.attendance.data.romote.dto.response


import com.snsop.attendance.domain.model.enums.UserStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginDto(
    @SerialName("errorCode")
    val errorCode: Int?, // 0
    @SerialName("errorMsg")
    val errorMsg: String?, // string
    @SerialName("expiredIn")
    val expiredIn: String?, // string
    @SerialName("id")
    val id: Int?, // 0
    @SerialName("operationResult")
    val operationResult: Boolean?, // true
    @SerialName("status")
    val status: UserStatus?, // string
    @SerialName("token")
    val token: String?, // string
    @SerialName("userName")
    val userName: String? // string
)