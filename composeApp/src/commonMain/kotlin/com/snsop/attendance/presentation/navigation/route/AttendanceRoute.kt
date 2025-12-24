package com.snsop.attendance.presentation.navigation.route

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.snsop.attendance.presentation.MainViewModel
import com.snsop.attendance.presentation.navigation.Screens
import com.snsop.attendance.presentation.screens.attendance.AttendanceListScreen
import com.snsop.attendance.presentation.screens.attendance.BeneficiaryListScreen
import com.snsop.attendance.presentation.screens.attendance.BomaListScreen
import com.snsop.attendance.presentation.screens.attendance.MarkAttendanceScreen
import com.snsop.attendance.presentation.screens.attendance.QrScannerScreen
import com.snsop.attendance.presentation.screens.attendance.SyncAttendanceScreen
import com.snsop.attendance.utils.log

fun EntryProviderScope<NavKey>.attendanceRoute(
    viewModel: MainViewModel,
) {
    entry<Screens.QrScanner> {
        QrScannerScreen(
            onNavigate = viewModel::navigateToMarkAttendance,
            onBack = viewModel.backStack::removeLastOrNull
        )
    }
    entry<Screens.BomaList> {
        val bomas by viewModel.bomas.collectAsStateWithLifecycle()
        val isRefreshing by viewModel.loadingSecondaryFlow.collectAsStateWithLifecycle()
        BomaListScreen(
            bomas = bomas,
            isRefreshing = isRefreshing,
            onRefresh = viewModel::getBomaList,
            onBomaItemClick = viewModel::navigateToBeneficiaryList,
            onQrScan = {
                viewModel.backStack.add(Screens.QrScanner)
            },
            onBack = viewModel.backStack::removeLastOrNull
        )
    }
    entry<Screens.BeneficiaryList> {
        val beneficiaries by viewModel.beneficiaries.collectAsStateWithLifecycle()
        val isRefreshing by viewModel.loadingSecondaryFlow.collectAsStateWithLifecycle()
        BeneficiaryListScreen(
            beneficiaries = beneficiaries,
            isRefreshing = isRefreshing,
            onRefresh = viewModel::getBeneficiaries,
            onBeneficiaryClick = viewModel::navigateToMarkAttendance,
            onQrScan = {
                viewModel.backStack.add(Screens.QrScanner)
            },
            onBack = viewModel.backStack::removeLastOrNull
        )
    }
    entry<Screens.MarkAttendance> { markAttendance ->
        val beneficiary = viewModel.selectedBeneficiary
        val selectedDate by viewModel.selectedDate.collectAsStateWithLifecycle()
        MarkAttendanceScreen(
            beneficiary = beneficiary,
            attendanceType = viewModel.attendanceType,
            screen = markAttendance,
            selectedDate = selectedDate,
            onChangeDate = viewModel::updateSelectedDate,
            onSave = viewModel::saveAttendance,
            onShowPicker = {
                viewModel.backStack.add(Screens.Dialog.DatePicker(selectedDate))
            },
            onBack = viewModel.backStack::removeLastOrNull,
        )
    }
    entry<Screens.SyncAttendance> {
        val uiState by viewModel.syncAttendanceUiState.collectAsStateWithLifecycle()
        SyncAttendanceScreen(
            uiState = uiState,
            onSync = viewModel::syncAttendance,
            onBomaItemClick = viewModel::navigateToAttendanceList,
            onBack = viewModel.backStack::removeLastOrNull
        )
    }
    entry<Screens.AttendanceList> {
        val attendanceList by viewModel.attendanceList.collectAsStateWithLifecycle()
        attendanceList.size.log("attendanceList size")
        val selectedDate by viewModel.selectedDate.collectAsStateWithLifecycle()
        val isRefreshing by viewModel.loadingSecondaryFlow.collectAsStateWithLifecycle()
        LaunchedEffect(selectedDate) {
            viewModel.getAttendanceList(viewModel.selectedBoma, selectedDate)
        }
        AttendanceListScreen(
            attendanceList = attendanceList,
            type = viewModel.attendanceType,
            selectedDate = selectedDate,
            isRefreshing = isRefreshing,
            onRefresh = {
                viewModel.getAttendanceList(viewModel.selectedBoma, selectedDate)
            },
            onAttendanceItemClick = viewModel::navigateToMarkAttendance,
            onShowPicker = {
                viewModel. backStack.add(Screens.Dialog.DatePicker(selectedDate))
            },
            onSync = {
                viewModel.syncAttendance(viewModel.selectedBoma, selectedDate)
            },
            onBack = viewModel.backStack::removeLastOrNull
        )
    }
}