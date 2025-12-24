package com.snsop.attendance.data.romote.dto.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResultDto(
    @SerialName("code")
    val code: Int? = null, // 0
    @SerialName("message")
    val message: String? = null // string
)