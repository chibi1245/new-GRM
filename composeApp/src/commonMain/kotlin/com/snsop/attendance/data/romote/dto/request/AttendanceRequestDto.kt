package com.snsop.attendance.data.romote.dto.request

import com.snsop.attendance.domain.model.Attendance
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AttendanceRequestDto(
    @SerialName("list")
    val attendances: List<Attendance> = listOf(),
)