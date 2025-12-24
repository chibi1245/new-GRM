package com.snsop.attendance.domain.model.enums

import kotlinx.serialization.Serializable

@Serializable
enum class UserStatus {
    ACTIVE,
    INACTIVE,
    PENDING,
    REJECTED;
    fun isActive() = this == ACTIVE
    fun isInactive() = this == INACTIVE
}