package com.snsop.attendance.domain.repo

import com.snsop.attendance.domain.model.Area
import com.snsop.attendance.domain.model.Attendance
import com.snsop.attendance.domain.model.Beneficiary
import com.snsop.attendance.domain.model.BomaItem
import com.snsop.attendance.domain.model.enums.AttendanceType
import com.snsop.attendance.domain.model.enums.Criteria
import com.snsop.attendance.presentation.base.Info

interface MainRepo {
    suspend fun downloadBeneficiaries(
        boma: Area.Boma,
        criteria: Criteria,
        page: Int = 0,
        pageSize: Int = 10000
    )

    suspend fun getBomaList(): List<BomaItem>
    suspend fun getBeneficiaries(bomaId: Int): List<Beneficiary>
    suspend fun getBeneficiary(householdNumber: String): Beneficiary?
    suspend fun getBeneficiaryCount(): Int
    suspend fun getAttendanceList(boma: BomaItem, attendanceDate: Long, type: AttendanceType): List<Pair<Beneficiary, Attendance>>
    suspend fun saveAttendance(attendance: Attendance): Info
    suspend fun syncAttendance(type: AttendanceType, boma: BomaItem? = null, attendanceDate: Long? = null)
    suspend fun getAttendanceCount(type: AttendanceType, attendanceDate: Long? = null): Int
    suspend fun getSyncedAttendanceCount(type: AttendanceType, attendanceDate: Long? = null): Int
    suspend fun getUnSyncedAttendanceCount(type: AttendanceType, attendanceDate: Long? = null): Int

}