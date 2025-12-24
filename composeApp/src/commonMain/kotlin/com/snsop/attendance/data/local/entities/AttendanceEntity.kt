package com.snsop.attendance.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.snsop.attendance.domain.model.enums.AttendanceType
import com.snsop.attendance.domain.model.enums.SyncStatus

@Entity(tableName = "attendance", foreignKeys = [
    ForeignKey(
        entity = BeneficiaryEntity::class,
        parentColumns = ["id"],
        childColumns = ["beneficiaryId"]
    )
], indices = [Index("beneficiaryId")])
data class AttendanceEntity(
    @ColumnInfo("beneficiaryId")
    val beneficiaryId: Int = 0, // string
    @ColumnInfo("date")
    val date: String, // 2025-11-21
    @ColumnInfo("ecdAttended")
    val ecdAttended: Boolean = false, // true
    @ColumnInfo("finlitAttended")
    val finLitAttended: Boolean = false, // true
    @ColumnInfo("gbvAttended")
    val gbvAttended: Boolean = false, // true
    @ColumnInfo("washAttended")
    val washAttended: Boolean = false, // true
    @ColumnInfo("present")
    val present: Boolean = false, // true

    @ColumnInfo("status")
    val status: SyncStatus = SyncStatus.Saved,
    @ColumnInfo("type")
    val type: AttendanceType = AttendanceType.General,
    @ColumnInfo("message")
    val message: String? = null, // error

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    val id: Long = 0L
)