package com.snsop.attendance.domain.model

import com.snsop.attendance.domain.model.enums.AttendanceType
import com.snsop.attendance.domain.model.enums.SyncStatus
import com.snsop.attendance.presentation.navigation.Screens
import com.snsop.attendance.utils.toDateServer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class Attendance(
    @SerialName("beneficiaryId")
    val beneficiaryId: Int, // string
    @Transient
    @SerialName("ecdAttended")
    val ecdAttended: Boolean = false, // true
    @Transient
    @SerialName("finlitAttended")
    val finLitAttended: Boolean = false, // true
    @Transient
    @SerialName("gbvAttended")
    val gbvAttended: Boolean = false, // true
    @Transient
    @SerialName("washAttended")
    val washAttended: Boolean = false, // true
    @SerialName("present")
    val present: Boolean = false, // true

    @SerialName("attendanceDate")
    val date: String = "", // 2025-11-21

    @Transient
    @SerialName("status")
    val status: SyncStatus = SyncStatus.Saved,
    @Transient
    @SerialName("message")
    val message: String? = null, // error
    @Transient
    @SerialName("type")
    val type: AttendanceType = AttendanceType.General,
    @Transient
    @SerialName("id")
    val id: Long = 0L
)

sealed class AttendanceState(open val value: Boolean, val name: String, val description: String) {
    data class Present(override val value: Boolean = false) : AttendanceState(value, "Present", "Physically Present")
    data class ECDAttended(override val value: Boolean = false) : AttendanceState(value, "ECD Attended", "Early Childhood Development")
    data class FinLitAttended(override val value: Boolean = false) : AttendanceState(value, "FL Attended", "Financial Literacy")
    data class GBVAttended(override val value: Boolean = false) : AttendanceState(value, "GBV Attended", "Gender Based Violence")
    data class WashAttended(override val value: Boolean = false) : AttendanceState(value, "Wash Attended", "Water, Sanitation, and Hygiene")

    override fun toString() = name
}

data class AttendanceStates(
    val present: AttendanceState.Present = AttendanceState.Present(),
    val ecdAttended: AttendanceState.ECDAttended = AttendanceState.ECDAttended(),
    val finLitAttended: AttendanceState.FinLitAttended = AttendanceState.FinLitAttended(),
    val gbvAttended: AttendanceState.GBVAttended = AttendanceState.GBVAttended(),
    val washAttended: AttendanceState.WashAttended = AttendanceState.WashAttended()
) {
    constructor(screen: Screens.MarkAttendance) : this(
        present = AttendanceState.Present(screen.present),
        ecdAttended = AttendanceState.ECDAttended(screen.ecdAttended),
        finLitAttended = AttendanceState.FinLitAttended(screen.finLitAttended),
        gbvAttended = AttendanceState.GBVAttended(screen.gbvAttended),
        washAttended = AttendanceState.WashAttended(screen.washAttended)
    )

    fun toList() = listOf(
        present,
        ecdAttended,
        finLitAttended,
        gbvAttended,
        washAttended
    )

    fun toAttendance(
        beneficiaryId: Int,
        date: Long
    ) = Attendance(
        beneficiaryId = beneficiaryId,
        ecdAttended = ecdAttended.value,
        finLitAttended = finLitAttended.value,
        gbvAttended = gbvAttended.value,
        washAttended = washAttended.value,
        present = present.value,
        date = date.toDateServer()
    )

    override fun hashCode(): Int {
        var result = present.hashCode()
        result = 31 * result + ecdAttended.hashCode()
        result = 31 * result + finLitAttended.hashCode()
        result = 31 * result + gbvAttended.hashCode()
        result = 31 * result + washAttended.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as AttendanceStates

        if (present != other.present) return false
        if (ecdAttended != other.ecdAttended) return false
        if (finLitAttended != other.finLitAttended) return false
        if (gbvAttended != other.gbvAttended) return false
        if (washAttended != other.washAttended) return false

        return true
    }
}