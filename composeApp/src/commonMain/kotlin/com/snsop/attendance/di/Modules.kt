package com.snsop.attendance.di

import com.snsop.attendance.data.local.AppSettings
import com.snsop.attendance.data.repo.AuthRepoImpl
import com.snsop.attendance.data.repo.AuthRepoImpl2
import com.snsop.attendance.data.repo.GeoRepoImpl
import com.snsop.attendance.data.repo.MainRepoImpl
import com.snsop.attendance.domain.network.ConnectionListener
import com.snsop.attendance.domain.repo.AuthRepo
import com.snsop.attendance.domain.repo.AuthRepo2
import com.snsop.attendance.domain.repo.GeoRepo
import com.snsop.attendance.domain.repo.MainRepo
import com.snsop.attendance.presentation.AuthViewModel
import com.snsop.attendance.presentation.MainViewModel
import com.snsop.attendance.presentation.SignInViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect fun platformModule(): Module

val viewModelModule = module {
    viewModelOf(::AuthViewModel)
    viewModelOf(::SignInViewModel)
    viewModelOf(::MainViewModel)
}

val dataModule = module {
    singleOf(::AuthRepoImpl) bind AuthRepo::class
    singleOf(::AuthRepoImpl2 ) bind AuthRepo2::class
    singleOf(::MainRepoImpl) bind MainRepo::class
    singleOf(::GeoRepoImpl) bind GeoRepo::class
}

val instantModule = module(createdAtStart = true) {
    singleOf(::AppSettings)
    singleOf(::ConnectionListener)
}

val appModules = listOf(
    platformModule(),
    viewModelModule,
    dataModule,
    instantModule,
    networkModule,
)

