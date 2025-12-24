package com.snsop.attendance.data.local.entities


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.snsop.attendance.domain.model.enums.Criteria

@Entity(tableName = "beneficiary")
data class BeneficiaryEntity(
    @PrimaryKey
    @ColumnInfo("id")
    val id: Int? = null, // 0
    @ColumnInfo("criteria")
    val criteria: Criteria = Criteria.ALL,
    @ColumnInfo("bomaId")
    val bomaId: Int = -1, // 0
    @ColumnInfo("householdNumber")
    val householdNumber: String? = null, // string
    @ColumnInfo("beneficiaryName")
    val beneficiaryName: String? = null, // string
    @ColumnInfo("age")
    val age: Int? = null, // 150
    @ColumnInfo("gender")
    val gender: String? = null, // MALE
    @ColumnInfo("phoneNo")
    val phoneNo: String? = null, // string
    @ColumnInfo("photoData")
    val photoData: String? = null // string
)