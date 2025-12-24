package com.snsop.attendance.presentation.navigation.route

import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.snsop.attendance.presentation.MainViewModel
import com.snsop.attendance.presentation.base.SnackbarController
import com.snsop.attendance.presentation.navigation.BottomNavItems
import com.snsop.attendance.presentation.navigation.ConfirmAction
import com.snsop.attendance.presentation.navigation.Screens
import com.snsop.attendance.presentation.screens.home.AttendanceScreen
import com.snsop.attendance.presentation.screens.home.DashboardScreen
import com.snsop.attendance.presentation.screens.home.DownloadScreen
import dev.jordond.connectivity.compose.rememberConnectivityState
import kotlinx.coroutines.launch

@Composable
fun HomePagerRoute(
    pagerState: PagerState,
    viewModel: MainViewModel,
    modifier: Modifier,
    bottomPadding: Dp
) {
    val state = rememberConnectivityState { autoStart = true }
    val dashboardStates by viewModel.dashboardStats.collectAsStateWithLifecycle()
    val downloadUiState by viewModel.downloadUiState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    HorizontalPager(
        state = pagerState,
    ) { page ->
        when (BottomNavItems.entries[page]) {
            BottomNavItems.Dashboard -> DashboardScreen(
                userInfo = viewModel.settings.userInfo,
                isConnected = state.isConnected,
                dashboardStats = dashboardStates,
                onLogoutClick = {
                    viewModel.backStack.add(
                        Screens.Dialog.Confirmation(
                            title = "Logout",
                            message = "Are you sure you want to logout?",
                            confirmText = "Logout",
                            action = ConfirmAction.Logout,
                            isConfirmRed = true,
                        )
                    )
                },
                onDownloadBeneficiaries = {
                    scope.launch {
                        pagerState.animateScrollToPage(BottomNavItems.Download.ordinal)
                    }
                },
                onMarkAttendance = viewModel::navigateToBomaList,
                onExportData = {
                    SnackbarController.sendEvent("Not implemented yet")
                },
                modifier = modifier,
                bottomPadding = bottomPadding
            )

            BottomNavItems.Attendance -> AttendanceScreen(
                modifier = modifier,
                isConnected = state.isConnected,
                onMarkAttendance = viewModel::navigateToBomaList,
                onSyncAttendance = viewModel::navigateToSyncAttendance,
                bottomPadding = bottomPadding
            )
            BottomNavItems.Download -> DownloadScreen(
                modifier = modifier,
                uiState = downloadUiState,
                onUiStateChange = viewModel::updateDownloadUiState,
                onDownloadBeneficiaries = viewModel::downloadBeneficiaries,
                bottomPadding = bottomPadding
            )
        }
    }
}