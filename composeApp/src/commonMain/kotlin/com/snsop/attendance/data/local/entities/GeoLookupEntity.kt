package com.snsop.attendance.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "geo_lookup")
data class GeoLookupEntity(
    @ColumnInfo(name = "s_code") val stateCode: Int = -1,
    @ColumnInfo(name = "state") val state: String = "",
    @ColumnInfo(name = "c_code") val countyCode: Int = -1,
    @ColumnInfo(name = "county") val county: String = "",
    @ColumnInfo(name = "p_code") val payamCode: Int = -1,
    @ColumnInfo(name = "payam") val payam: String = "",
    @PrimaryKey
    @ColumnInfo(name = "b_code") val bomaCode: Int,
    @ColumnInfo(name = "boma") val boma: String = ""
)
