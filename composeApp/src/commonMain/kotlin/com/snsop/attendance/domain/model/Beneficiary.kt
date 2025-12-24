package com.snsop.attendance.domain.model

import com.snsop.attendance.domain.model.enums.Criteria
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient


@Serializable
data class Beneficiary(
    @SerialName("id")
    val id: Int? = null, // 0
    @SerialName("fullName")
    val name: String? = null, // 0
    @SerialName("householdNumber")
    val householdNumber: String? = null, // 0
    @SerialName("age")
    val age: Int? = null, // 150
    @SerialName("gender")
    val gender: String? = null, // MALE
    @SerialName("phoneNumber")
    val phoneNo: String? = null, // string
    @SerialName("photoData")
    val photoData: String? = null, // string

    @Transient
    @SerialName("criteria")
    val criteria: Criteria = Criteria.ALL // string
)