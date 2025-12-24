package com.snsop.attendance.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import androidx.room.Transaction
import androidx.room.Update
import com.snsop.attendance.data.local.entities.AttendanceEntity
import com.snsop.attendance.data.local.entities.relational.AttendanceWithBeneficiary
import com.snsop.attendance.domain.model.enums.AttendanceType
import com.snsop.attendance.domain.model.enums.SyncStatus
import com.snsop.attendance.presentation.base.Info

@Dao
interface AttendanceDao {

    @Transaction
    suspend fun upsert(attendance: AttendanceEntity): Info {
        val existingAttendance = getAttendance(
            beneficiaryId = attendance.beneficiaryId,
            date = attendance.date,
            type = attendance.type
        )
        val info = if (existingAttendance != null) {
            updateAttendance(attendance.copy(id = existingAttendance.id)).toLong() to Info("Attendance updated")
        }
        else {
            insertAttendance(attendance) to Info("Attendance saved")
        }
        if (info.first == -1L) error("Couldn't save attendance")
        return info.second
    }

    @Query("SELECT * FROM attendance WHERE beneficiaryId = :beneficiaryId AND date = :date AND type = :type LIMIT 1")
    suspend fun getAttendance(
        beneficiaryId: Int,
        date: String,
        type: AttendanceType
    ): AttendanceEntity?

    @Transaction
    @Query("""
        SELECT attendance.* FROM attendance 
        INNER JOIN beneficiary 
        ON attendance.beneficiaryId = beneficiary.id 
        WHERE attendance.date = :date 
        AND attendance.type = :type
        AND beneficiary.bomaId = :bomaId
    """)
    @RewriteQueriesToDropUnusedColumns
    suspend fun getAllAttendance(bomaId: Int, date: String, type: AttendanceType): List<AttendanceWithBeneficiary>

    @Query("SELECT * FROM attendance WHERE status != 'Synced' AND type = :type")
    suspend fun getAllUnSyncedAttendance(type: AttendanceType): List<AttendanceEntity>

    @Query("SELECT * FROM attendance WHERE status != 'Synced' AND date = :date AND type = :type")
    suspend fun getAllUnSyncedAttendance(date: String, type: AttendanceType): List<AttendanceEntity>

    @Query("""
        SELECT attendance.* FROM attendance 
        INNER JOIN beneficiary 
        ON attendance.beneficiaryId = beneficiary.id 
        WHERE attendance.status != 'Synced' 
        AND attendance.date = :date 
        AND attendance.type = :type
        AND beneficiary.bomaId = :bomaId
        """)
    @RewriteQueriesToDropUnusedColumns
    suspend fun getAllUnSyncedAttendance(bomaId: Int, date: String, type: AttendanceType): List<AttendanceEntity>

    @Query("UPDATE attendance SET status = :status, message = :message WHERE id IN (:attendanceIds)")
    suspend fun updateStatusForAttendanceIds(attendanceIds: List<Long>, status: SyncStatus, message: String? = null)

    @Update
    suspend fun updateAttendance(attendance: AttendanceEntity): Int

    @Insert
    suspend fun insertAttendance(attendance: AttendanceEntity): Long

    @Query("SELECT COUNT(*) FROM attendance WHERE type = :type")
    suspend fun getAttendanceCount(type: AttendanceType): Int

    @Query("SELECT COUNT(*) FROM attendance WHERE date = :date AND type = :type")
    suspend fun getAttendanceCount(date: String, type: AttendanceType): Int

    @Query("SELECT COUNT(*) FROM attendance WHERE status == 'Synced' AND type = :type")
    suspend fun getSyncedAttendanceCount(type: AttendanceType): Int

    @Query("SELECT COUNT(*) FROM attendance WHERE status == 'Synced' AND date = :date AND type = :type")
    suspend fun getSyncedAttendanceCount(date: String, type: AttendanceType): Int

    @Query("SELECT COUNT(*) FROM attendance WHERE status != 'Synced' AND type = :type")
    suspend fun getUnSyncedAttendanceCount(type: AttendanceType): Int

    @Query("SELECT COUNT(*) FROM attendance WHERE date = :date AND status != 'Synced' AND type = :type")
    suspend fun getUnSyncedAttendanceCount(date: String, type: AttendanceType): Int
}