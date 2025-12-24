package com.snsop.attendance.domain.model.ui

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class ActionItem(
    val title: String,
    val subtitle: String,
    val status: String,
    val statusColor: Color,
    val icon: ImageVector,
    val iconBackgroundColor: Color,
    val onClick: () -> Unit
)

data class DashboardStats(
    val totalBeneficiaries: Int = 0,
    val markedToday: Int = 0
)