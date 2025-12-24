package com.snsop.attendance.presentation.ui_states

import com.snsop.attendance.domain.model.Area
import com.snsop.attendance.domain.model.Areas
import com.snsop.attendance.domain.model.GeoData

data class DownloadScreenUiState(
    val state: Area.State? = null,
    val county: Area.County? = null,
    val payam: Area.Payam? = null,
    val boma: Area.Boma? = null,
    val geoData: GeoData = GeoData()
) {
    fun toAreas(): Areas? {
        return Areas(
            state = state ?: return null,
            county = county ?: return null,
            payam = payam ?: return null,
            boma = boma ?: return null
        )
    }
}
