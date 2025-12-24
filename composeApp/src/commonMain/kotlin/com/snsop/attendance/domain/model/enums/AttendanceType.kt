package com.snsop.attendance.domain.model.enums

enum class AttendanceType {
    General,
    Training;

    val isGeneral
        get() = this == General
    val isTraining
        get() = this == Training
}