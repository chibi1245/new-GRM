package com.snsop.attendance.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Logout
import androidx.compose.material.icons.rounded.ArrowDownward
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.FileUpload
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.snsop.attendance.domain.model.UserInfo
import com.snsop.attendance.domain.model.ui.ActionItem
import com.snsop.attendance.domain.model.ui.DashboardStats
import com.snsop.attendance.presentation.components.ActionCard
import com.snsop.attendance.presentation.components.PrimaryOutlinedCard
import com.snsop.attendance.ui.theme.AttendanceTheme
import com.snsop.attendance.ui.theme.Dimen
import com.snsop.attendance.utils.bounceClick
import com.snsop.attendance.utils.toInitial
import com.snsop.attendance.utils.toTitleName

@Composable
fun DashboardScreen(
    userInfo: UserInfo,
    isConnected: Boolean,
    dashboardStats: DashboardStats,
    onLogoutClick: () -> Unit,
    onDownloadBeneficiaries: () -> Unit,
    onMarkAttendance: () -> Unit,
    onExportData: () -> Unit,
    modifier: Modifier = Modifier,
    bottomPadding: Dp
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(Dimen.Standard)
    ) {
        // User Profile Card
        UserProfileCard(
            userInfo = userInfo,
            onLogoutClick = onLogoutClick,
            dashboardStats = dashboardStats,
            isConnected = isConnected
        )

        Spacer(modifier = Modifier.height(Dimen.Standard))

        // Action Items
        ActionCard(
            actionItem = ActionItem(
                title = "Get Beneficiaries",
                subtitle = "Download list\nfrom server",
                status = if (isConnected) "Server ready" else "Server offline",
                statusColor = if (isConnected) Color(0xFF2196F3) else Color.Gray,
                icon = Icons.Rounded.ArrowDownward,
                iconBackgroundColor = if (isConnected) Color(0xFF2196F3) else Color.Gray,
                onClick = onDownloadBeneficiaries
            ),
            modifier = Modifier.alpha(if (isConnected) 1f else .5f)
        )

        Spacer(modifier = Modifier.height(Dimen.Standard))

        ActionCard(
            actionItem = ActionItem(
                title = "Mark Attendance",
                subtitle = "Mark as present\nor absent",
                status = "Ready to mark",
                statusColor = Color(0xFF4CAF50),
                icon = Icons.Rounded.Person,
                iconBackgroundColor = Color(0xFF4CAF50),
                onClick = onMarkAttendance
            )
        )

        Spacer(modifier = Modifier.height(Dimen.Standard))

        ActionCard(
            actionItem = ActionItem(
                title = "Export Data",
                subtitle = "Export attendance to file",
                status = "Data ready",
                statusColor = Color(0xFFFF9800),
                icon = Icons.Rounded.FileUpload,
                iconBackgroundColor = Color(0xFFFF9800),
                onClick = onExportData
            )
        )

        Spacer(modifier = Modifier.height(bottomPadding))
    }
}

@Composable
fun UserProfileCard(
    userInfo: UserInfo,
    onLogoutClick: () -> Unit,
    dashboardStats: DashboardStats,
    isConnected: Boolean,
    modifier: Modifier = Modifier
) {
    PrimaryOutlinedCard(modifier = modifier) {
        Column(
            modifier = Modifier.padding(Dimen.Standard)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Avatar with verification badge
                    Box {
                        Box(
                            modifier = Modifier
                                .size(Dimen.HugePlus)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary.copy(.6f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = userInfo.fullName.toInitial(),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                        if (userInfo.status.isActive()) {
                            Box(
                                modifier = Modifier
                                    .size(Dimen.Large)
                                    .align(Alignment.BottomEnd)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primary)
                                    .border(2.dp, MaterialTheme.colorScheme.surface, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Check,
                                    contentDescription = "Verified",
                                    tint = MaterialTheme.colorScheme.surface,
                                    modifier = Modifier.size(Dimen.IconSizeSmall)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(Dimen.Standard))

                    Column {
                        Text(
                            text = userInfo.fullName.toTitleName(),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(if (isConnected) MaterialTheme.colorScheme.primary else Color.Gray)
                            )
                            Spacer(modifier = Modifier.width(Dimen.Medium))
                            Text(
                                text = if (isConnected) "Online" else "Offline",
                                fontSize = 14.sp,
                                color = if (isConnected) MaterialTheme.colorScheme.primary else Color.Gray
                            )
                        }
                    }
                }

                // Logout button
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.Logout,
                    contentDescription = "Logout",
                    tint = Color.White,
                    modifier = Modifier
                        .bounceClick(keepRipple = false) {
                            onLogoutClick()
                        }
                        .clip(RoundedCornerShape(Dimen.Medium))
                        .background(MaterialTheme.colorScheme.error.copy(.8f))
                        .padding(Dimen.MediumPlus)
                )
            }

            Spacer(modifier = Modifier.height(Dimen.Standard))

            // Stats Card
            StatsCard(dashboardStats = dashboardStats)
        }
    }
}

@Composable
fun StatsCard(dashboardStats: DashboardStats) {
    Row(
        modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Max),
        horizontalArrangement = Arrangement.spacedBy(Dimen.Standard)
    ) {
        StatItem(
            value = dashboardStats.totalBeneficiaries.toString(),
            label = "Total\nBeneficiaries",
            modifier = Modifier.weight(1f).fillMaxHeight()
        )

        StatItem(
            value = dashboardStats.markedToday.toString(),
            label = "Marked Today",
            valueColor = Color(0xFF4CAF50),
            modifier = Modifier.weight(1f).fillMaxHeight()
        )
    }
}

@Composable
fun StatItem(
    value: String,
    label: String,
    valueColor: Color = MaterialTheme.colorScheme.onBackground,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .clip(RoundedCornerShape(Dimen.Standard))
            .background(MaterialTheme.colorScheme.primary.copy(.3f))
            .padding(Dimen.Standard)
    ) {
        Text(
            text = value,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = valueColor
        )
        Spacer(modifier = Modifier.height(Dimen.Small))
        Text(
            text = label,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onBackground,
            lineHeight = 16.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardPreview() {
    AttendanceTheme {
        DashboardScreen(
            userInfo = UserInfo(
                fullName = "Yamin Mahdi"
            ),
            dashboardStats = DashboardStats(
                totalBeneficiaries = 45,
                markedToday = 32
            ),
            onLogoutClick = {},
            onDownloadBeneficiaries = {},
            onMarkAttendance = {},
            onExportData = {},
            bottomPadding = 0.dp,
            isConnected = true
        )
    }
}