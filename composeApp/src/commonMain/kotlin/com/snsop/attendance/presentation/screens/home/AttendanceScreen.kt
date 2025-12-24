package com.snsop.attendance.presentation.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Upload
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.snsop.attendance.domain.model.enums.AttendanceType
import com.snsop.attendance.domain.model.ui.ActionItem
import com.snsop.attendance.presentation.components.ActionCard
import com.snsop.attendance.ui.theme.Dimen

@Composable
fun AttendanceScreen(
    modifier: Modifier = Modifier,
    isConnected: Boolean,
    onMarkAttendance: (AttendanceType) -> Unit,
    onSyncAttendance: (AttendanceType) -> Unit,
    bottomPadding: Dp
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(Dimen.Standard)
    ) {
        Text(
            text = "General Attendance",
            style = MaterialTheme.typography.titleSmall,
            color = Color(0xFF4CAF50),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = Dimen.Medium)
                .padding(Dimen.responsiveHorizontalPadding())
        )
        ActionCard(
            actionItem = ActionItem(
                title = "Mark Attendance",
                subtitle = "Mark as present\nor absent",
                status = "Ready to mark",
                statusColor = Color(0xFF4CAF50),
                icon = Icons.Rounded.Person,
                iconBackgroundColor = Color(0xFF4CAF50),
                onClick = {
                    onMarkAttendance(AttendanceType.General)
                }
            )
        )
        Spacer(modifier = Modifier.height(Dimen.Standard))
        ActionCard(
            actionItem = ActionItem(
                title = "Sync Attendance",
                subtitle = "Mark as present\nor absent",
                status = if (isConnected) "Ready to sync" else "Server offline",
                statusColor = Color(0xFF4CAF50),
                icon = Icons.Rounded.Upload,
                iconBackgroundColor = if (isConnected) Color(0xFF4CAF50) else Color.Gray,
                onClick = {
                    onSyncAttendance(AttendanceType.General)
                }
            )
        )

        HorizontalDivider(
            modifier = Modifier
                .padding(Dimen.StandardPlus)
                .padding(Dimen.responsiveHorizontalPadding())
        )

        Text(
            text = "Training Attendance",
            style = MaterialTheme.typography.titleSmall,
            color = Color(0xFF2196F3),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = Dimen.Medium)
                .padding(Dimen.responsiveHorizontalPadding())
        )

        ActionCard(
            actionItem = ActionItem(
                title = "Mark Attendance",
                subtitle = "Mark as present\nor absent",
                status = "Ready to mark",
                statusColor = Color(0xFF2196F3),
                icon = Icons.Rounded.Person,
                iconBackgroundColor = Color(0xFF2196F3),
                onClick = {
                    onMarkAttendance(AttendanceType.Training)
                }
            )
        )
        Spacer(modifier = Modifier.height(Dimen.Standard))
        ActionCard(
            actionItem = ActionItem(
                title = "Sync Attendance",
                subtitle = "Mark as present\nor absent",
                status = if (isConnected) "Ready to sync" else "Server offline",
                statusColor = Color(0xFF2196F3),
                icon = Icons.Rounded.Upload,
                iconBackgroundColor = if (isConnected) Color(0xFF2196F3) else Color.Gray,
                onClick = {
                    onSyncAttendance(AttendanceType.Training)
                }
            )
        )

        Spacer(modifier = Modifier.height(bottomPadding))
    }
}