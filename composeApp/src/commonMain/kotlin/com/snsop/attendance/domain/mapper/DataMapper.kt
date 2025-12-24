package com.snsop.attendance.domain.mapper

import com.snsop.attendance.data.local.entities.AttendanceEntity
import com.snsop.attendance.data.local.entities.BeneficiaryEntity
import com.snsop.attendance.data.local.entities.BomaEntity
import com.snsop.attendance.domain.model.Area
import com.snsop.attendance.domain.model.Attendance
import com.snsop.attendance.domain.model.Beneficiary
import com.snsop.attendance.domain.model.enums.Criteria
import com.snsop.attendance.utils.currentTimestamp


fun Attendance.toEntity() = AttendanceEntity(
    beneficiaryId = beneficiaryId,
    date = date,
    ecdAttended = ecdAttended,
    finLitAttended = finLitAttended,
    gbvAttended = gbvAttended,
    washAttended = washAttended,
    present = present,
    status = status,
    message = message,
    type = type
)

fun Beneficiary.toEntity(bomaId: Int, criteria: Criteria) = BeneficiaryEntity(
    id = id,
    age = age,
    gender = gender,
    phoneNo = phoneNo,
    bomaId = bomaId,
    criteria = criteria,
    householdNumber = householdNumber,
    beneficiaryName = name,
    photoData = photoData
)

fun Area.Boma.toEntity() = BomaEntity(
    id = id,
    name = name,
    totalBeneficiary = 0,
    lastUpdated = currentTimestamp
)