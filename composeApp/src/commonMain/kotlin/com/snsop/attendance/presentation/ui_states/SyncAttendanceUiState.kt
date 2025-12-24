package com.snsop.attendance.presentation.ui_states

import com.snsop.attendance.domain.model.BomaItem
import com.snsop.attendance.domain.model.enums.AttendanceType

data class SyncAttendanceUiState(
    val bomas: List<BomaItem> = emptyList(),
    val unSyncedAttendanceCount: Int = 0,
    val type: AttendanceType = AttendanceType.General,
    val syncedAttendanceCount: Int = 0,
    val isRefreshing: Boolean = false
)