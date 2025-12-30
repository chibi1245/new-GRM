package com.snsop.attendance.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.serialization.saved
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.snsop.attendance.data.local.AppSettings
import com.snsop.attendance.domain.model.Area
import com.snsop.attendance.domain.model.Attendance
import com.snsop.attendance.domain.model.AttendanceStates
import com.snsop.attendance.domain.model.Beneficiary
import com.snsop.attendance.domain.model.BomaItem
import com.snsop.attendance.domain.model.enums.AttendanceType
import com.snsop.attendance.domain.model.enums.Criteria
import com.snsop.attendance.domain.model.ui.DashboardStats
import com.snsop.attendance.domain.repo.GeoRepo
import com.snsop.attendance.domain.repo.MainRepo
import com.snsop.attendance.presentation.base.BaseViewModel
import com.snsop.attendance.presentation.navigation.Screens
import com.snsop.attendance.presentation.navigation.navConfiguration
import com.snsop.attendance.presentation.ui_states.DownloadScreenUiState
import com.snsop.attendance.presentation.ui_states.SyncAttendanceUiState
import com.snsop.attendance.utils.currentTimestamp
import com.snsop.attendance.utils.toTimeStamp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel(
    val settings: AppSettings,
    val geoRepo: GeoRepo,
    val mainRepo: MainRepo,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {
    val isUserLoggedIn
        get() = settings.isUserLoggedIn

    private val _dashboardStats = MutableStateFlow(DashboardStats())
    val dashboardStats = _dashboardStats.asStateFlow()

    private val _downloadUiState = MutableStateFlow(DownloadScreenUiState())
    val downloadUiState = _downloadUiState.asStateFlow()

    private val _syncAttendanceUiState = MutableStateFlow(SyncAttendanceUiState())
    val syncAttendanceUiState = _syncAttendanceUiState.asStateFlow()

    private val _attendanceList = MutableStateFlow<List<Pair<Beneficiary, Attendance>>>(emptyList())
    val attendanceList = _attendanceList.asStateFlow()

    private val _bomas = MutableStateFlow<List<BomaItem>>(emptyList())
    val bomas = _bomas.asStateFlow()

    private val _beneficiaries = MutableStateFlow<List<Beneficiary>>(emptyList())
    val beneficiaries = _beneficiaries.asStateFlow()

    val backStack by savedStateHandle.saved(
        configuration = navConfiguration
    ) {
        NavBackStack<NavKey>(Screens.Splash)

    }

    var selectedBoma = BomaItem()
    var attendanceType = AttendanceType.General
    var selectedBeneficiary = Beneficiary()
    val selectedDate = MutableStateFlow(currentTimestamp)

    init {
        loadGeo()
        loadDashboardStats()
    }

    fun loadDashboardStats() {
        launch(loading = Loading.Gone) {
            _dashboardStats.value = DashboardStats(
                totalBeneficiaries = mainRepo.getBeneficiaryCount(),
                markedToday = mainRepo.getAttendanceCount(attendanceType, currentTimestamp)
            )
        }
    }

    fun loadGeo() {
        launch(loading = Loading.Gone) {
            val geoData = geoRepo.getGeoDataFromLocal()
            _downloadUiState.value = downloadUiState.value.copy(
                geoData = geoData
            )
        }
    }

    fun updateDownloadUiState(uiState: DownloadScreenUiState) {
        launch(loading = Loading.Gone) {
            _downloadUiState.value = uiState
        }
    }

    fun downloadBeneficiaries(boma: Area.Boma, criteria: Criteria) {
        launch {
            mainRepo.downloadBeneficiaries(boma, criteria)
            _bomas.value = mainRepo.getBomaList()
            showSuccess("Beneficiaries downloaded")
        }
    }

    fun getBomaList() {
        launch(loading = Loading.Secondary) {
            _bomas.value = mainRepo.getBomaList()
        }
    }

    fun getBeneficiaries(boma: BomaItem = selectedBoma) {
        launch(loading = Loading.Secondary) {
            _beneficiaries.value = mainRepo.getBeneficiaries(boma.id)
        }
    }

    fun getAttendanceInfo() {
        launch(loading = Loading.Gone) {
            _syncAttendanceUiState.value = syncAttendanceUiState.value.copy(
                bomas = mainRepo.getBomaList(),
                type = attendanceType,
                syncedAttendanceCount = mainRepo.getSyncedAttendanceCount(attendanceType),
                unSyncedAttendanceCount = mainRepo.getUnSyncedAttendanceCount(attendanceType),
            )
        }
    }

    fun getAttendanceList(boma: BomaItem, attendanceDate: Long = currentTimestamp){
        launch(loading = Loading.Secondary) {
            selectedBoma = boma
            _attendanceList.value = mainRepo.getAttendanceList(boma, attendanceDate, attendanceType)
        }
    }

    fun logout(backStack: NavBackStack<NavKey>) {
        launch(loading = Loading.Gone) {
            settings.token = null
            val oldBackStack = backStack.toList()
            backStack.add(Screens.Login)
            backStack.removeAll(oldBackStack)
        }
    }

    fun updateSelectedDate(selectedDate: Long) {
        this.selectedDate.value = selectedDate
    }

    fun saveAttendance(attendanceStates: AttendanceStates) {
        launch {
            val attendance = attendanceStates.toAttendance(
                beneficiaryId = selectedBeneficiary.id ?: error("Beneficiary Id not found"),
                date = selectedDate.value,
            ).copy(type = attendanceType)
            selectedDate.value = currentTimestamp
            successFlow.emit(mainRepo.saveAttendance(attendance))
            backStack.removeLastOrNull()
        }
    }

    fun syncAttendance(boma: BomaItem? = null, date: Long? = null){
        launch {
            mainRepo.syncAttendance(attendanceType, boma, date)
            if (boma != null && date != null)
                getAttendanceList(boma, date)
            showSuccess("Attendance synced")
            getAttendanceInfo()
        }
    }

    fun navigateToBomaList(type: AttendanceType = AttendanceType.General) {
        attendanceType = type
        backStack.add(Screens.BomaList)
        getBomaList()
    }

    fun navigateToBeneficiaryList(boma: BomaItem) {
        selectedBoma = boma
        backStack.add(Screens.BeneficiaryList)
        getBeneficiaries(boma)
    }

    fun navigateToAttendanceList(boma: BomaItem) {
        selectedBoma = boma
        backStack.add(Screens.AttendanceList)
    }

    fun navigateToMarkAttendance(householdNumber: String) {
        backStack.removeLastOrNull()
        launch(loading = Loading.Gone) {
            val beneficiary =
                mainRepo.getBeneficiary(householdNumber) ?: error("Beneficiary not found")
            navigateToMarkAttendance(beneficiary)
        }
    }

    fun navigateToMarkAttendance(beneficiary: Beneficiary, attendance: Attendance? = null) {
        launch(loading = Loading.Gone) {
            selectedDate.value = currentTimestamp
            selectedBeneficiary = beneficiary
            if (attendance != null) {
                if (attendance.status.isSynced())
                    error("Attendance already synced")
                selectedDate.value = attendance.date.toTimeStamp()
                backStack.add(Screens.MarkAttendance(
                    type = "Update",
                    present = attendance.present,
                    ecdAttended = attendance.ecdAttended,
                    finLitAttended = attendance.finLitAttended,
                    gbvAttended = attendance.gbvAttended,
                    washAttended = attendance.washAttended
                ))
                return@launch
            }
            backStack.add(Screens.MarkAttendance())
        }
    }

    fun navigateToSyncAttendance(type: AttendanceType) {
        attendanceType = type
        backStack.add(Screens.SyncAttendance)
        getAttendanceInfo()
    }
}