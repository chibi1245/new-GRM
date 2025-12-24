package com.snsop.attendance.presentation.screens.attendance.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Backup
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.snsop.attendance.Features
import com.snsop.attendance.domain.model.Attendance
import com.snsop.attendance.domain.model.Beneficiary
import com.snsop.attendance.domain.model.enums.SyncStatus
import com.snsop.attendance.presentation.components.CoilImage
import com.snsop.attendance.presentation.components.PrimaryOutlinedCard
import com.snsop.attendance.ui.theme.AttendanceTheme
import com.snsop.attendance.ui.theme.Dimen
import com.snsop.attendance.utils.bounceOnClick


@Composable
fun AttendanceItem(
    attendance: Attendance,
    beneficiary: Beneficiary,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    PrimaryOutlinedCard(
        modifier = modifier
            .bounceOnClick(),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimen.Standard)
        ) {
            // Header with beneficiary info and status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    // Avatar
                    CoilImage(name = beneficiary.name, photo = beneficiary.photoData)

                    Spacer(modifier = Modifier.width(Dimen.MediumPlus))

                    Column {
                        Text(
                            text = beneficiary.name ?: "Unknown",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(Dimen.Small))
                        beneficiary.householdNumber?.let {
                            Text(
                                text = "ID: $it",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Spacer(modifier = Modifier.height(Dimen.Small))
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(Dimen.MediumPlus)
                        ) {
                            beneficiary.age?.let {
                                Text(
                                    text = "Age: $it",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            beneficiary.gender?.let {
                                Text(
                                    text = it,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }

                // Status indicator
                SyncStatusIcon(status = attendance.status)
            }

            Spacer(modifier = Modifier.height(Dimen.Standard))

            // Attendance info
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Date: ${attendance.date}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(Dimen.Small))
                Text(
                    text = if (attendance.present) "Present" else "Absent",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = if (attendance.present)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.error
                )

                // Session summary
                if(!Features.showOnlyPresent) {
                    Column(
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = "Sessions",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(Dimen.Small))
                        Text(
                            text = "${getAttendedSessionsCount(attendance)}/4",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            // Session badges
            if(!Features.showOnlyPresent) {
                Spacer(modifier = Modifier.height(Dimen.MediumPlus))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(Dimen.Medium)
                ) {
                    SessionBadge(
                        label = "ECD",
                        attended = attendance.ecdAttended
                    )
                    SessionBadge(
                        label = "FinLit",
                        attended = attendance.finLitAttended
                    )
                    SessionBadge(
                        label = "GBV",
                        attended = attendance.gbvAttended
                    )
                    SessionBadge(
                        label = "WASH",
                        attended = attendance.washAttended
                    )
                }
            }

            // Error message if exists
            AnimatedVisibility(attendance.message != null && attendance.status == SyncStatus.Failed)  {
                Text(
                    text = attendance.message.orEmpty(),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .padding(top = Dimen.MediumPlus)
                        .fillMaxWidth()
                        .background(
                            color = MaterialTheme.colorScheme.errorContainer,
                            shape = RoundedCornerShape(Dimen.Medium)
                        )
                        .padding(Dimen.Medium)
                )
            }
        }
    }
}

@Composable
private fun SessionBadge(
    label: String,
    attended: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                color = if (attended)
                    MaterialTheme.colorScheme.primaryContainer
                else
                    MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(Dimen.Medium)
            )
            .padding(horizontal = Dimen.MediumPlus, vertical = Dimen.Small),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Medium,
            color = if (attended)
                MaterialTheme.colorScheme.onPrimaryContainer
            else
                MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun SyncStatusIcon(
    status: SyncStatus,
    modifier: Modifier = Modifier
) {
    when (status) {
        SyncStatus.Saved -> Icon(
            imageVector = Icons.Rounded.CheckCircle,
            contentDescription = "Saved",
            tint = MaterialTheme.colorScheme.primary,
            modifier = modifier.size(Dimen.Large)
        )
        SyncStatus.Synced -> Icon(
            imageVector = Icons.Rounded.Backup,
            contentDescription = "Synced",
            tint = MaterialTheme.colorScheme.tertiary,
            modifier = modifier.size(Dimen.Large)
        )
        SyncStatus.Failed -> Icon(
            imageVector = Icons.Rounded.Error,
            contentDescription = "Error",
            tint = MaterialTheme.colorScheme.error,
            modifier = modifier.size(Dimen.Large)
        )
    }
}

private fun getAttendedSessionsCount(attendance: Attendance): Int {
    var count = 0
    if (attendance.ecdAttended) count++
    if (attendance.finLitAttended) count++
    if (attendance.gbvAttended) count++
    if (attendance.washAttended) count++
    return count
}

@Preview(showBackground = true)
@Composable
private fun AttendanceItemPrev() {
    AttendanceTheme {
        Column(
            modifier = Modifier.padding(Dimen.Standard),
            verticalArrangement = Arrangement.spacedBy(Dimen.MediumPlus)
        ) {
            AttendanceItem(
                attendance = Attendance(
                    beneficiaryId = 1,
                    ecdAttended = true,
                    finLitAttended = true,
                    gbvAttended = true,
                    washAttended = false,
                    present = true,
                    date = "2025-11-21",
                    status = SyncStatus.Saved
                ),
                beneficiary = Beneficiary(
                    id = 1,
                    name = "John Doe",
                    householdNumber = "HH001",
                    age = 35,
                    gender = "Male"
                ),
                onClick = {}
            )

            AttendanceItem(
                attendance = Attendance(
                    beneficiaryId = 2,
                    ecdAttended = false,
                    finLitAttended = false,
                    gbvAttended = false,
                    washAttended = false,
                    present = false,
                    date = "2025-11-21",
                    status = SyncStatus.Failed,
                    message = "Network error occurred"
                ),
                beneficiary = Beneficiary(
                    id = 2,
                    name = "Jane Smith",
                    householdNumber = "HH002",
                    age = 28,
                    gender = "Female"
                ),
                onClick = {}
            )
        }
    }
}