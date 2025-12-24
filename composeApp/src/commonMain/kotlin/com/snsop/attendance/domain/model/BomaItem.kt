package com.snsop.attendance.domain.model

import kotlinx.serialization.SerialName

data class BomaItem(
    @SerialName("id")
    val id: Int = -1, // 0
    @SerialName("name")
    val name: String = "",
    @SerialName("totalBeneficiary")
    val totalBeneficiary: Int = 0,
    @SerialName("lastUpdated")
    val lastUpdated: Long = 0L
)