package com.snsop.attendance.data

import androidx.room.Room
import androidx.room.RoomDatabase
import attendance.composeapp.generated.resources.Res
import com.snsop.attendance.data.local.AppDatabase
import com.snsop.attendance.data.local.DatabasePaths
import com.snsop.attendance.data.local.GeoDatabase
import io.github.aakira.napier.Napier
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask


fun getAppDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val dbFilePath = getDatabasePath(DatabasePaths.ATTENDANCE_DB)
    return Room.databaseBuilder<AppDatabase>(
        name = dbFilePath
    )
}
fun getGeoDatabaseBuilder(): RoomDatabase.Builder<GeoDatabase> {
    val dbFilePath = getDatabasePath(DatabasePaths.GEO_DB)

    // Manually replicate "createFromAsset" behavior
    if (!NSFileManager.defaultManager.fileExistsAtPath(dbFilePath))
        copyDatabaseFromResources(dbFilePath)

    return Room.databaseBuilder<GeoDatabase>(
        name = dbFilePath
    )
}

private fun getDatabasePath(databaseName: String): String {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )
    val dirPath = requireNotNull(documentDirectory?.path)
    return "$dirPath/$databaseName"
}

private fun copyDatabaseFromResources(destinationPath: String) {
    val uriString = Res.getUri(DatabasePaths.GEO_DB_FILE_PATH)

    val sourceUrl = NSURL.URLWithString(uriString)
    val sourcePath = sourceUrl?.path

    val fileManager = NSFileManager.defaultManager

    if (sourcePath != null && fileManager.fileExistsAtPath(sourcePath)) {
        try {
            fileManager.copyItemAtPath(sourcePath, destinationPath, null)
            Napier.d("iOS: Database successfully copied to $destinationPath")
        } catch (e: Exception) {
            Napier.d("iOS: Error copying database: ${e.message}")
        }
    } else {
        Napier.d("iOS: Critical Error - Source DB not found at: $sourcePath")
        Napier.d("iOS: Raw URI was: $uriString")
    }
}