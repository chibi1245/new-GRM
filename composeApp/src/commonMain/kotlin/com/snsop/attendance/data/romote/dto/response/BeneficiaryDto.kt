package com.snsop.attendance.data.romote.dto.response


import com.snsop.attendance.domain.model.Beneficiary
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BeneficiaryDto(
    @SerialName("beneficiaries")
    val beneficiaries: List<Beneficiary?>? = null,
    @SerialName("errorCode")
    val errorCode: Int? = null, // 0
    @SerialName("errorMsg")
    val errorMsg: String? = null, // string
    @SerialName("operationResult")
    val operationResult: Boolean? = null, // true
    @SerialName("total")
    val total: Int? = null // 0
)