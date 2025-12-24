package com.snsop.attendance.domain.model.enums

enum class Criteria {
    ALL,
    LIPW,
    DIS;

    fun isLIPW() = this == LIPW
}