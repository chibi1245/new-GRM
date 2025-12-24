package com.snsop.attendance.domain.model


fun GeoData.asAreaLists() =
    listOf(states, counties, payams, bomas)

fun Area.getParents(geoData: GeoData): Quadruple<Area.Boma?, Area.Payam?, Area.County?, Area.State?> {
    when (this) {
        is Area.Boma -> {
            val (payam, county, state) = getParents(geoData)
            return Quadruple(this, payam, county, state)
        }
        is Area.Payam -> {
            val (county, state) = getParents(geoData)
            return Quadruple(null, this, county, state)
        }
        is Area.County -> {
            val state = getParent(geoData)
            return Quadruple(null, null, this, state)
        }
        is Area.State -> {
            return Quadruple(null, null, null, this)
        }
    }
}

fun Area.Boma.getParents(geoData: GeoData): Triple<Area.Payam?, Area.County?, Area.State?> {
    val payam = geoData.payams.find { it.id == parentId }
    val county = geoData.counties.find { it.id == payam?.parentId }
    val state = geoData.states.find { it.id == county?.parentId }
    return Triple(payam, county, state)
}

fun Area.Payam.getParents(geoData: GeoData): Pair<Area.County?, Area.State?> {
    val county = geoData.counties.find { it.id == parentId }
    val state = geoData.states.find { it.id == county?.parentId }
    return county to state
}

fun Area.County.getParent(geoData: GeoData): Area.State? =
    geoData.states.find { it.id == parentId }