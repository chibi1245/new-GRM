package com.snsop.attendance.domain.mapper

import com.snsop.attendance.data.local.entities.AttendanceEntity
import com.snsop.attendance.data.local.entities.BeneficiaryEntity
import com.snsop.attendance.data.local.entities.BomaEntity
import com.snsop.attendance.data.romote.dto.response.UserDto
import com.snsop.attendance.domain.model.Attendance
import com.snsop.attendance.domain.model.Beneficiary
import com.snsop.attendance.domain.model.BomaItem
import com.snsop.attendance.domain.model.UserInfo
import com.snsop.attendance.domain.model.enums.UserStatus
import com.snsop.attendance.utils.orMinusOne

fun UserDto.Info.toDomain() = UserInfo(
    userName = userName.orEmpty(),
    email = email.orEmpty(),
    fullName = fullName.orEmpty(),
    id = id.orMinusOne(),
    role = role.orEmpty(),
    status = status ?: UserStatus.INACTIVE
)

fun BomaEntity.toDomain() = BomaItem(
    id = id,
    name = name,
    totalBeneficiary = totalBeneficiary,
    lastUpdated = lastUpdated
)

fun BeneficiaryEntity.toDomain() = Beneficiary(
    id = id,
    name = beneficiaryName,
    criteria = criteria,
    age = age,
    gender = gender,
    phoneNo = phoneNo,
    householdNumber = householdNumber,
    photoData = photoData
)


fun AttendanceEntity.toDomain() = Attendance(
    date = date,
    beneficiaryId = beneficiaryId,
    ecdAttended = ecdAttended,
    finLitAttended = finLitAttended,
    gbvAttended = gbvAttended,
    washAttended = washAttended,
    present = present,
    status = status,
    id = id,
    message = message
)