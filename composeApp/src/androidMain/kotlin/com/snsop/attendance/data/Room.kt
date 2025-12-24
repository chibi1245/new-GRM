package com.snsop.attendance.data

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import attendance.composeapp.generated.resources.Res
import com.snsop.attendance.data.local.AppDatabase
import com.snsop.attendance.data.local.DatabasePaths
import com.snsop.attendance.data.local.GeoDatabase

fun getAppDatabaseBuilder(context: Context): RoomDatabase.Builder<AppDatabase> {
    val appContext = context.applicationContext
    val dbFile = appContext.getDatabasePath(DatabasePaths.ATTENDANCE_DB)
    return Room.databaseBuilder<AppDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}

fun getGeoDatabaseBuilder(context: Context): RoomDatabase.Builder<GeoDatabase> {
    val appContext = context.applicationContext
    val dbFile = appContext.getDatabasePath(DatabasePaths.GEO_DB)
    val resourcePath = Res.getUri(DatabasePaths.GEO_DB_FILE_PATH).substringAfter("android_asset/")
    return Room.databaseBuilder<GeoDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    ).createFromAsset(resourcePath)
}