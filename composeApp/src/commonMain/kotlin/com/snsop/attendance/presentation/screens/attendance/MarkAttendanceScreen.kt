package com.snsop.attendance.presentation.screens.attendance

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.snsop.attendance.domain.model.AttendanceState
import com.snsop.attendance.domain.model.AttendanceStates
import com.snsop.attendance.domain.model.Beneficiary
import com.snsop.attendance.domain.model.enums.AttendanceType
import com.snsop.attendance.presentation.components.AnimatedSelector
import com.snsop.attendance.presentation.components.CoilImage
import com.snsop.attendance.presentation.components.DatePickerField
import com.snsop.attendance.presentation.components.PrimaryButton
import com.snsop.attendance.presentation.components.PrimaryOutlinedCard
import com.snsop.attendance.presentation.components.PrimaryScaffold
import com.snsop.attendance.presentation.components.SecondaryButton
import com.snsop.attendance.presentation.navigation.Screens
import com.snsop.attendance.ui.theme.AttendanceTheme
import com.snsop.attendance.ui.theme.Dimen
import com.snsop.attendance.utils.currentTimestamp
import com.snsop.attendance.utils.toTitleName

@Composable
fun MarkAttendanceScreen(
    modifier: Modifier = Modifier,
    attendanceType: AttendanceType,
    beneficiary: Beneficiary,
    screen: Screens.MarkAttendance,
    selectedDate: Long,
    onChangeDate: (Long) -> Unit,
    onSave: (AttendanceStates) -> Unit,
    onShowPicker: () -> Unit,
    onBack: () -> Unit
) {
    var attendanceStates by remember { mutableStateOf(AttendanceStates(screen)) }

    PrimaryScaffold(
        modifier = modifier,
        title = "Mark ${attendanceType.name} Attendance",
        onBack = onBack
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            // Beneficiary Info Section (Avatar, Name, Gender, ID)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(Dimen.responsiveHorizontalPadding())
            ) {
                // Avatar with initials
                CoilImage(name = beneficiary.name, photo = beneficiary.photoData)

                Spacer(modifier = Modifier.width(Dimen.Standard))

                // Name, Gender, and ID
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = beneficiary.name?.toTitleName() ?: "---",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(Dimen.Small))

                    // Gender chip
                    Surface(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = RoundedCornerShape(Dimen.Medium)
                    ) {
                        Text(
                            text = beneficiary.gender ?: "Unknown",
                            modifier = Modifier.padding(
                                horizontal = Dimen.Medium,
                                vertical = Dimen.Tiny
                            ),
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Spacer(modifier = Modifier.height(Dimen.Small))

                    // Beneficiary ID
                    Text(
                        text = "ID: ${beneficiary.householdNumber}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(Dimen.Large))

            AttendanceCard(
                attendanceStates = attendanceStates,
                type = screen.type,
                attendanceType = attendanceType,
                selectedDate = selectedDate,
                onStateChange = { state ->
                    attendanceStates = state
                },
                onSave = {
                    onSave(attendanceStates)
                },
                onShowPicker = onShowPicker,
                onReset = {
                    attendanceStates = AttendanceStates(screen)
                    onChangeDate(currentTimestamp)
                }
            )
            Spacer(modifier = Modifier.height(bottomPadding + Dimen.Standard))
        }
    }
}


// Custom Attendance Card with Present/Absent Chips
@Composable
fun AttendanceCard(
    attendanceStates: AttendanceStates,
    type: String,
    attendanceType: AttendanceType,
    selectedDate: Long,
    onStateChange: (AttendanceStates) -> Unit,
    onShowPicker: () -> Unit,
    onSave: () -> Unit,
    onReset: () -> Unit
) {
    PrimaryOutlinedCard(
        modifier = Modifier.padding(Dimen.Standard)
    ) {
        Column(modifier = Modifier.padding(Dimen.Standard)) {
            DatePickerField(
                value = selectedDate,
                onShowPicker = onShowPicker,  // Update the selected date via callback
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(Dimen.MediumPlus))

            if (attendanceType.isGeneral)
                Spacer(modifier = Modifier.height(Dimen.Medium))

            // Iterate through the courses and display each one with present/absent options
            val stateList = if (attendanceType.isGeneral)
                listOf(attendanceStates.toList().first())
            else
                attendanceStates.toList()
            stateList.forEach { state ->
                AnimatedSelector(
                    name = state.name,
                    description = state.description,
                    isPositive = state.value,
                    onToggle = { selected ->
                        when (state) {
                            is AttendanceState.Present -> onStateChange(
                                attendanceStates.copy(
                                    present = AttendanceState.Present(selected)
                                )
                            )

                            is AttendanceState.ECDAttended -> onStateChange(
                                attendanceStates.copy(
                                    ecdAttended = AttendanceState.ECDAttended(selected)
                                )
                            )

                            is AttendanceState.FinLitAttended -> onStateChange(
                                attendanceStates.copy(
                                    finLitAttended = AttendanceState.FinLitAttended(selected)
                                )
                            )

                            is AttendanceState.GBVAttended -> onStateChange(
                                attendanceStates.copy(
                                    gbvAttended = AttendanceState.GBVAttended(selected)
                                )
                            )

                            is AttendanceState.WashAttended -> onStateChange(
                                attendanceStates.copy(
                                    washAttended = AttendanceState.WashAttended(selected)
                                )
                            )
                        }
                    }
                )
            }

            if (attendanceType.isGeneral)
                Spacer(modifier = Modifier.height(Dimen.ExtraLargePlus))

            Spacer(modifier = Modifier.height(Dimen.Medium))

            // Save and Clear Buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = Dimen.Standard),
                horizontalArrangement = Arrangement.spacedBy(Dimen.Standard)
            ) {
                SecondaryButton(
                    text = "Reset",
                    onClick = onReset,
                    compact = true,
                    modifier = Modifier.weight(1f)
                )
                PrimaryButton(
                    text = type,
                    onClick = onSave,
                    compact = true,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MarkAttendanceScreenPreview() {
    val beneficiary = Beneficiary(
        id = 1,
//        subprojectId = 100,
        name = "Sarah Johnson",
        householdNumber = "APP001",
        age = 28,
        gender = "Female",
        phoneNo = "01710000000"
    )
    AttendanceTheme {
        MarkAttendanceScreen(
            beneficiary = beneficiary,
            selectedDate = currentTimestamp,
            onChangeDate = {},
            onSave = { /* handle save */ },
            onShowPicker = {},
            onBack = {},
            screen = Screens.MarkAttendance(),
            attendanceType = AttendanceType.Training
        )
    }
}