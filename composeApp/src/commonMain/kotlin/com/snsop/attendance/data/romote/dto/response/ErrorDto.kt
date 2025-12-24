package com.snsop.attendance.data.romote.dto.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorDto(
    @SerialName("error")
    var error: String? = null, // Unauthorized
    @SerialName("errorMsg")
    var errorMsg: String? = null, // Unauthorized
    @SerialName("message")
    val message: String? = null // Full authentication is required to access this resource
)
