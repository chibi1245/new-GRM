package com.snsop.attendance.di

import com.snsop.attendance.data.getAppDatabaseBuilder
import com.snsop.attendance.data.getGeoDatabaseBuilder
import com.snsop.attendance.data.local.AppDatabase
import com.snsop.attendance.data.local.GeoDatabase
import com.snsop.attendance.data.local.buildOnCommon
import org.koin.dsl.module

actual fun platformModule() = module {
    single<AppDatabase> {
        getAppDatabaseBuilder(get())
            .buildOnCommon()
    }
    single<GeoDatabase> {
        getGeoDatabaseBuilder(get())
            .buildOnCommon()
    }
}