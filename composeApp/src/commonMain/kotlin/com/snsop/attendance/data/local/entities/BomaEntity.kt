package com.snsop.attendance.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "boma")
data class BomaEntity(
    @ColumnInfo("id")
    @PrimaryKey
    val id: Int = -1, // 0
    @ColumnInfo("name")
    val name: String = "",
    @ColumnInfo("totalBeneficiary")
    val totalBeneficiary: Int = 0,
    @ColumnInfo("lastUpdated")
    val lastUpdated: Long = 0L
)