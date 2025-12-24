package com.snsop.attendance.domain.model

data class GeoData(
    val states: List<Area.State> = emptyList(),
    val counties: List<Area.County> = emptyList(),
    val payams: List<Area.Payam> = emptyList(),
    val bomas: List<Area.Boma> = emptyList(),
    val stateToCounties: Map<Int, List<Area.County>> = emptyMap(),
    val countyToPayams: Map<Int, List<Area.Payam>> = emptyMap(),
    val payamToBomas: Map<Int, List<Area.Boma>> = emptyMap()
)


