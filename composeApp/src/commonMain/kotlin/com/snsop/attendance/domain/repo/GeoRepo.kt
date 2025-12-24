package com.snsop.attendance.domain.repo

import com.snsop.attendance.domain.model.GeoData

interface GeoRepo {
    suspend fun getGeoDataFromLocal(): GeoData
}