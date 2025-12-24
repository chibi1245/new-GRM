package com.snsop.attendance.data.romote.dto.request


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestDto(
    @SerialName("userName")
    val userName: String = "", // string
    @SerialName("password")
    val password: String = "" // string
)