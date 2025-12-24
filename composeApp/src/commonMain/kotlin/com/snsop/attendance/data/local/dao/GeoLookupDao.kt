package com.snsop.attendance.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.snsop.attendance.data.local.entities.GeoLookupEntity

@Dao
interface GeoLookupDao {
    @Query("SELECT * FROM geo_lookup")
    suspend fun getAll(): List<GeoLookupEntity>
}

