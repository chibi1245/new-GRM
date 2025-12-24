package com.snsop.attendance.data.repo

import com.snsop.attendance.data.local.AppDatabase
import com.snsop.attendance.data.romote.dto.request.AttendanceRequestDto
import com.snsop.attendance.data.romote.dto.response.BeneficiaryDto
import com.snsop.attendance.data.romote.dto.response.ResultDto
import com.snsop.attendance.domain.mapper.toDomain
import com.snsop.attendance.domain.mapper.toEntity
import com.snsop.attendance.domain.model.Area
import com.snsop.attendance.domain.model.Attendance
import com.snsop.attendance.domain.model.Beneficiary
import com.snsop.attendance.domain.model.BomaItem
import com.snsop.attendance.domain.model.enums.AttendanceType
import com.snsop.attendance.domain.model.enums.Criteria
import com.snsop.attendance.domain.model.enums.SyncStatus
import com.snsop.attendance.domain.network.append
import com.snsop.attendance.domain.network.processGetRequest
import com.snsop.attendance.domain.network.processPostRequest
import com.snsop.attendance.domain.repo.MainRepo
import com.snsop.attendance.presentation.base.Info
import com.snsop.attendance.utils.mapAsync
import com.snsop.attendance.utils.toDateServer
import io.ktor.client.HttpClient
import io.ktor.http.parameters

class MainRepoImpl(
    private val ktorClient: HttpClient,
    database: AppDatabase
) : MainRepo {
    private val beneficiaryDao = database.beneficiaryDao()
    private val attendanceDao = database.attendanceDao()

    override suspend fun downloadBeneficiaries(
        boma: Area.Boma,
        criteria: Criteria,
        page: Int,
        pageSize: Int
    ) {
        val response =
            ktorClient.processGetRequest<BeneficiaryDto>(
                "mis_server/api/beneficiary/getBeneficiary/selectionCriteria/$criteria"
            ) {
                parameters {
                    append("bomaId", boma.id)
                    append("page", page)
                    append("size", pageSize)
                }
            }
        val beneficiaries = response.beneficiaries
            .orEmpty().mapNotNull { it?.toEntity(boma.id, criteria) }
        beneficiaries.ifEmpty { error("No beneficiaries found") }
        beneficiaryDao.upsertBeneficiaries(beneficiaries = beneficiaries)
        beneficiaryDao.upsertBoma(bomaEntity = boma.toEntity().copy(totalBeneficiary = beneficiaries.size))
    }

    override suspend fun getBomaList(): List<BomaItem> {
        return beneficiaryDao.getBomaList().map { it.toDomain() }
    }

    override suspend fun getBeneficiaries(bomaId: Int): List<Beneficiary> {
        return beneficiaryDao.getBeneficiaries(bomaId).map { it.toDomain() }
    }

    override suspend fun getBeneficiary(householdNumber: String): Beneficiary? {
        return beneficiaryDao.getBeneficiary(householdNumber)?.toDomain()
    }

    override suspend fun getAttendanceList(
        boma: BomaItem,
        attendanceDate: Long,
        type: AttendanceType
    ): List<Pair<Beneficiary, Attendance>> {
        val attendance = attendanceDao.getAllAttendance(
            bomaId = boma.id,
            date = attendanceDate.toDateServer(),
            type = type
        )
        return attendance.map { it.beneficiary.toDomain() to it.attendance.toDomain() }
    }

    override suspend fun saveAttendance(attendance: Attendance): Info {
        return attendanceDao.upsert(attendance.toEntity())
    }

    override suspend fun syncAttendance(
        type: AttendanceType,
        boma: BomaItem?,
        attendanceDate: Long?
    ) {
        if (type.isTraining)
            error("Under development")
        val attendanceList =
            if (boma != null && attendanceDate != null) {
                attendanceDao.getAllUnSyncedAttendance(
                    bomaId = boma.id,
                    date = attendanceDate.toDateServer(),
                    type = type
                ).mapAsync { it.toDomain() }
            } else {
                attendanceDao.getAllUnSyncedAttendance(type)
                    .mapAsync { it.toDomain() }
            }
        if (attendanceList.isEmpty()) error("No attendance to sync")
        try {
            saveAttendanceToServer(attendances = attendanceList)
            attendanceDao.updateStatusForAttendanceIds(
                attendanceIds = attendanceList.map { it.id },
                status = SyncStatus.Synced
            )
        } catch (e: Exception) {
            attendanceDao.updateStatusForAttendanceIds(
                attendanceIds = attendanceList.map { it.id },
                status = SyncStatus.Failed,
                message = e.message ?: "Unable to sync attendance"
            )
            throw e
        }
    }

    override suspend fun getBeneficiaryCount(): Int {
        return beneficiaryDao.getBeneficiaryCount()
    }

    override suspend fun getAttendanceCount(type: AttendanceType, attendanceDate: Long?): Int {
        return if (attendanceDate != null) attendanceDao.getAttendanceCount(
            date = attendanceDate.toDateServer(),
            type = type
        ) else attendanceDao.getAttendanceCount(type)
    }

    override suspend fun getSyncedAttendanceCount(
        type: AttendanceType,
        attendanceDate: Long?
    ): Int {
        return if (attendanceDate != null) attendanceDao.getSyncedAttendanceCount(
            date = attendanceDate.toDateServer(),
            type = type
        ) else attendanceDao.getSyncedAttendanceCount(type)
    }

    override suspend fun getUnSyncedAttendanceCount(
        type: AttendanceType,
        attendanceDate: Long?
    ): Int {
        return if (attendanceDate != null) attendanceDao.getUnSyncedAttendanceCount(
            date = attendanceDate.toDateServer(),
            type = type
        ) else attendanceDao.getUnSyncedAttendanceCount(type)
    }

    private suspend fun saveAttendanceToServer(
        attendances: List<Attendance>
    ) {
        ktorClient.processPostRequest<ResultDto>(
            url = "mis_server/api/attendance/save/async",
            body = AttendanceRequestDto(attendances = attendances)
        )
    }
}