package com.snsop.attendance.data.repo

import com.snsop.attendance.data.local.GeoDatabase
import com.snsop.attendance.domain.model.Area
import com.snsop.attendance.domain.model.GeoData
import com.snsop.attendance.domain.repo.GeoRepo
import io.github.aakira.napier.Napier

class GeoRepoImpl(
    geoDatabase: GeoDatabase
): GeoRepo {
    private val geoLookupDao = geoDatabase.getGeoLookupDao()

    override suspend fun getGeoDataFromLocal(): GeoData {
        val allStates = mutableListOf<Area.State>()
        val allCounties = mutableListOf<Area.County>()
        val allPayams = mutableListOf<Area.Payam>()
        val allBomas = mutableListOf<Area.Boma>()

        val stateToCounties = mutableMapOf<Int, MutableList<Area.County>>()
        val countyToPayams = mutableMapOf<Int, MutableList<Area.Payam>>()
        val payamToBomas = mutableMapOf<Int, MutableList<Area.Boma>>()

        val stateSet = mutableSetOf<Area.State>()
        val countySet = mutableSetOf<Area.County>()
        val payamSet = mutableSetOf<Area.Payam>()
        val bomaSet = mutableSetOf<Area.Boma>()

        // Fetch all geo data from the database
        val geoList = geoLookupDao.getAll()

        if (geoList.isEmpty())
            error("No data found in local database.")

        // Process each geo data entity
        geoList.forEach { entity ->
            // State
            val stateArea = Area.State(entity.stateCode, -1, entity.state)
            if (stateSet.add(stateArea)) allStates.add(stateArea)

            // County
            val countyArea = Area.County(entity.countyCode, entity.stateCode, entity.county)
            if (countySet.add(countyArea)) {
                stateToCounties.getOrPut(entity.stateCode) { mutableListOf() }.add(countyArea)
                allCounties.add(countyArea)
            }

            // Payam
            val payamArea = Area.Payam(entity.payamCode, entity.countyCode, entity.payam)
            if (payamSet.add(payamArea)) {
                countyToPayams.getOrPut(entity.countyCode) { mutableListOf() }.add(payamArea)
                allPayams.add(payamArea)
            }

            // Boma
            val bomaArea = Area.Boma(entity.bomaCode, entity.payamCode, entity.boma)
            if (bomaSet.add(bomaArea)) {
                payamToBomas.getOrPut(entity.payamCode) { mutableListOf() }.add(bomaArea)
                allBomas.add(bomaArea)
            }
        }

        // Log the map sizes
        Napier.d("Posting stateToCounties: ${stateToCounties.size}", tag = TAG)
        Napier.d("Posting countyToPayams: ${countyToPayams.size}", tag = TAG)
        Napier.d("Posting payamToBomas: ${payamToBomas.size}", tag = TAG)

        return GeoData(
            states = allStates,
            counties = allCounties,
            payams = allPayams,
            bomas = allBomas,
            stateToCounties = stateToCounties,
            countyToPayams = countyToPayams,
            payamToBomas = payamToBomas
        )
    }

    companion object {
        private const val TAG = "GeoRepository"
    }
}