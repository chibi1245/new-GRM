package com.snsop.attendance.data.local

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.snsop.attendance.Platform
import com.snsop.attendance.getPlatform
import com.snsop.attendance.utils.applyIf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

object DatabasePaths {
    const val ATTENDANCE_DB = "attendance_room.db"
    const val GEO_DB = "geo_room.db"
    const val GEO_DB_FILE_PATH = "files/odbv7.db"  // added two new boma in odbv7
}

fun <T : RoomDatabase> RoomDatabase.Builder<T>.buildOnCommon(): T =
    setQueryCoroutineContext(Dispatchers.IO)
        .applyIf(getPlatform() == Platform.iOS) {
            setDriver(BundledSQLiteDriver())
        }.build()