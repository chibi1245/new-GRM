package com.snsop.attendance.data.local.entities.relational

import androidx.room.Embedded
import androidx.room.Relation
import com.snsop.attendance.data.local.entities.AttendanceEntity
import com.snsop.attendance.data.local.entities.BeneficiaryEntity

data class AttendanceWithBeneficiary(
    @Embedded
    val attendance: AttendanceEntity,
    @Relation(
        parentColumn = "beneficiaryId",
        entityColumn = "id"
    )
    val beneficiary: BeneficiaryEntity
)
