package com.snsop.attendance.data.romote.dto.response


import com.snsop.attendance.domain.model.Attendance
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AttendanceDto(
    @SerialName("code")
    val code: Int? = null, // 0
    @SerialName("content")
    val attendances: List<Attendance?>? = null,
    @SerialName("message")
    val message: String? = null, // string
    @SerialName("pageNumber")
    val pageNumber: Int? = null, // 0
    @SerialName("pageSize")
    val pageSize: Int? = null, // 0
    @SerialName("totalElements")
    val totalElements: Int? = null // 0
)