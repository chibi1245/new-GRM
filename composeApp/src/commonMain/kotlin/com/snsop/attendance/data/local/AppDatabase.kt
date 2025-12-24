package com.snsop.attendance.data.local

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.snsop.attendance.data.local.dao.AttendanceDao
import com.snsop.attendance.data.local.dao.BeneficiaryDao
import com.snsop.attendance.data.local.entities.AttendanceEntity
import com.snsop.attendance.data.local.entities.BeneficiaryEntity
import com.snsop.attendance.data.local.entities.BomaEntity

@Database(entities = [
    BeneficiaryEntity::class,
    AttendanceEntity::class,
    BomaEntity::class],
    version = 1)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun beneficiaryDao(): BeneficiaryDao
    abstract fun attendanceDao(): AttendanceDao
}

// The Room compiler generates the `actual` implementations.
@Suppress("KotlinNoActualForExpect")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}