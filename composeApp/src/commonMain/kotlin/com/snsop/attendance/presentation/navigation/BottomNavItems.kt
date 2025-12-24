package com.snsop.attendance.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Download
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.ui.graphics.vector.ImageVector

enum class BottomNavItems(val icon: ImageVector) {
    Dashboard(Icons.Rounded.Home),
    Attendance(Icons.Rounded.Person),
    Download(Icons.Rounded.Download),
}