package com.snsop.attendance.data.local

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.snsop.attendance.data.local.dao.GeoLookupDao
import com.snsop.attendance.data.local.entities.GeoLookupEntity

@Database(entities = [GeoLookupEntity::class], version = 1)
@ConstructedBy(GeoDatabaseConstructor::class)
abstract class GeoDatabase : RoomDatabase() {
    abstract fun getGeoLookupDao(): GeoLookupDao
}

// The Room compiler generates the `actual` implementations.
@Suppress("KotlinNoActualForExpect")
expect object GeoDatabaseConstructor : RoomDatabaseConstructor<GeoDatabase> {
    override fun initialize(): GeoDatabase
}