package com.snsop.attendance.data.romote.dto.response


import com.snsop.attendance.domain.model.enums.UserStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    @SerialName("data")
    val data: Info? = null,
    @SerialName("message")
    val message: String? = null, // string
    @SerialName("status")
    val status: String? = null // 100 CONTINUE
){
    @Serializable
    data class Info(
        @SerialName("email")
        val email: String? = null, // string
        @SerialName("emailReceiver")
        val emailReceiver: Boolean? = null, // true
        @SerialName("fullName")
        val fullName: String? = null, // string
        @SerialName("id")
        val id: Int? = null, // 0
        @SerialName("isMobile")
        val isMobile: Boolean? = null, // true
        @SerialName("role")
        val role: String? = null, // string
        @SerialName("status")
        val status: UserStatus? = null, // string
        @SerialName("username")
        val userName: String? = null // string
    )

}