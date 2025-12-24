package com.snsop.attendance.presentation.screens.attendance

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CloudSync
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.snsop.attendance.domain.model.Attendance
import com.snsop.attendance.domain.model.Beneficiary
import com.snsop.attendance.domain.model.enums.AttendanceType
import com.snsop.attendance.domain.model.enums.SyncStatus
import com.snsop.attendance.presentation.components.PrimaryScaffold
import com.snsop.attendance.presentation.components.SearchField
import com.snsop.attendance.presentation.screens.attendance.components.AttendanceItem
import com.snsop.attendance.ui.theme.AttendanceTheme
import com.snsop.attendance.ui.theme.Dimen
import com.snsop.attendance.utils.singleClick
import com.snsop.attendance.utils.toDateModern

@Composable
fun AttendanceListScreen(
    modifier: Modifier = Modifier,
    type: AttendanceType,
    attendanceList: List<Pair<Beneficiary, Attendance>>,
    selectedDate: Long,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onSync: () -> Unit,
    onAttendanceItemClick: (Beneficiary, Attendance) -> Unit,
    onShowPicker: () -> Unit,
    onBack: () -> Unit
) {
    val searchState = rememberTextFieldState()
    val filteredItems = attendanceList
        .filter { it.first.name?.contains(searchState.text, ignoreCase = true) == true }
    PrimaryScaffold(
        modifier = modifier,
        title = "${type.name} Attendance List",
        onBack = onBack
    ) {
        PullToRefreshBox(
            modifier = Modifier.padding(paddingValues),
            isRefreshing = isRefreshing,
            onRefresh = onRefresh,
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = Dimen.Standard),
                verticalArrangement = Arrangement.spacedBy(Dimen.MediumPlus),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    SearchField(
                        type = "Name",
                        state = searchState,
                        modifier = Modifier.padding(Dimen.Medium)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(Dimen.responsiveHorizontalPadding())
                    ) {
                        DateLayout(
                            selectedDate = selectedDate,
                            onDateClick = onShowPicker,
                            modifier = Modifier
                                .weight(1f)
                        )
                        IconButton(onClick = singleClick(onSync)){
                            Icon(imageVector = Icons.Rounded.CloudSync, contentDescription = "Date Picker")
                        }
                    }
                    if (filteredItems.isEmpty()) {
                        Box(
                            modifier = Modifier.fillParentMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("No Data found")
                        }
                    }
                }
                items(filteredItems) { (beneficiary, attendance ) ->
                    AttendanceItem(
                        attendance = attendance,
                        beneficiary = beneficiary,
                        onClick = {
                            onAttendanceItemClick(beneficiary, attendance)
                        }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(Dimen.Small))
                }
            }
        }
    }
}

@Composable
fun DateLayout(
    selectedDate: Long,
    onDateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            Text(text = "Date: ", color = MaterialTheme.colorScheme.primary)
            Text(text = selectedDate.toDateModern())
        }
        IconButton(onClick = singleClick(onDateClick)) {
            Icon(imageVector = Icons.Rounded.DateRange, contentDescription = "Date Picker")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AttendanceListScreenPrev() {
    AttendanceTheme {
        AttendanceListScreen(
            selectedDate = 0L,
            attendanceList = listOf(
                Beneficiary(
                    id = 1,
                    name = "John Doe",
                    householdNumber = "HH001",
                    age = 35,
                    gender = "Male"
                ) to Attendance(
                    beneficiaryId = 1,
                    ecdAttended = true,
                    finLitAttended = true,
                    gbvAttended = true,
                    washAttended = false,
                    present = true,
                    date = "2025-11-21",
                    status = SyncStatus.Saved
                ),
                Beneficiary(
                    id = 2,
                    name = "Jane Smith",
                    householdNumber = "HH002",
                    age = 28,
                    gender = "Female"
                ) to Attendance(
                    beneficiaryId = 2,
                    ecdAttended = false,
                    finLitAttended = true,
                    gbvAttended = false,
                    washAttended = false,
                    present = true,
                    date = "2025-11-21",
                    status = SyncStatus.Synced
                )
            ),
            onAttendanceItemClick = { _,_ ->},
            onShowPicker = {},
            onBack = {},
            isRefreshing = false,
            onRefresh = {},
            onSync = {},
            type = AttendanceType.General
        )
    }
}