package com.snsop.attendance.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.snsop.attendance.Features
import com.snsop.attendance.domain.model.Area
import com.snsop.attendance.domain.model.Area.Boma
import com.snsop.attendance.domain.model.Area.County
import com.snsop.attendance.domain.model.Area.Payam
import com.snsop.attendance.domain.model.Area.State
import com.snsop.attendance.domain.model.GeoData
import com.snsop.attendance.domain.model.asAreaLists
import com.snsop.attendance.domain.model.enums.Criteria
import com.snsop.attendance.domain.model.getParents
import com.snsop.attendance.presentation.base.SnackbarController
import com.snsop.attendance.presentation.components.AnimatedSelector
import com.snsop.attendance.presentation.components.PrimaryButton
import com.snsop.attendance.presentation.components.PrimaryOutlinedCard
import com.snsop.attendance.presentation.components.SearchableSpinnerDialog
import com.snsop.attendance.presentation.components.SpinnerTextField
import com.snsop.attendance.presentation.ui_states.DownloadScreenUiState
import com.snsop.attendance.ui.theme.AttendanceTheme
import com.snsop.attendance.ui.theme.Dimen
import com.snsop.attendance.utils.log
import com.snsop.attendance.utils.toTitleName
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun DownloadScreen(
    modifier: Modifier = Modifier,
    uiState: DownloadScreenUiState = DownloadScreenUiState(),
    onUiStateChange: (DownloadScreenUiState) -> Unit = {},
    onDownloadBeneficiaries: (Boma, criteria: Criteria) -> Unit = { _, _ -> },
    bottomPadding: Dp
) {
    var selectedAreas by remember { mutableStateOf<List<Area>?>(null) }
    var selectedCriteria by remember { mutableStateOf(Criteria.LIPW) }
    var selectedFilteredAreas by remember { mutableStateOf<List<Area>?>(null) }
    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(Dimen.Standard)
    ) {
        PrimaryOutlinedCard {
            Column(
                modifier = Modifier.padding(Dimen.StandardPlus),
                verticalArrangement = Arrangement.spacedBy(Dimen.Medium)
            ) {
                if(Features.needCriteria) {
                    AnimatedSelector(
                        name = "Criteria",
                        items = Criteria.entries.map { it.name },
                        position = selectedCriteria.ordinal,
                        onToggle = {
                            selectedCriteria = Criteria.entries[it]
                            scope.launch {
                                if (!selectedCriteria.isLIPW()) {
                                    SnackbarController.sendEvent("Criteria is not allowed")
                                    delay(850L)
                                    selectedCriteria = Criteria.LIPW
                                }
                            }
                        }
                    )
                }
                uiState.geoData.asAreaLists().forEach {
                    val area = it.firstOrNull() ?: return@forEach
                    SpinnerTextField(
                        selectedItem = when (area) {
                            is State -> uiState.state
                            is County -> uiState.county
                            is Payam -> uiState.payam
                            is Boma -> uiState.boma
                        }?.name?.toTitleName(),
                        label = area.type(),
                        onClick = {
                            area.log("onClick Selected")
                            selectedFilteredAreas = when (area) {
                                is State -> uiState.geoData.states
                                is County -> if (uiState.state == null)
                                    uiState.geoData.counties
                                else
                                    uiState.geoData.stateToCounties[uiState.state.id] ?: uiState.geoData.counties

                                is Payam -> if (uiState.county == null)
                                    uiState.geoData.payams
                                else
                                    uiState.geoData.countyToPayams[uiState.county.id] ?: uiState.geoData.payams

                                is Boma -> if (uiState.payam == null)
                                    uiState.geoData.bomas
                                else
                                    uiState.geoData.payamToBomas[uiState.payam.id] ?: uiState.geoData.bomas
                            }
                            selectedAreas = when (area) {
                                is State -> uiState.geoData.states
                                is County -> uiState.geoData.counties
                                is Payam -> uiState.geoData.payams
                                is Boma -> uiState.geoData.bomas
                            }
                        }
                    )
                }

                Spacer(modifier = Modifier.height(Dimen.Medium))

                // Button
                PrimaryButton(
                    text = "Download",
                    onClick = {
                        uiState.boma?.also {
                            onDownloadBeneficiaries(it, selectedCriteria)
                        } ?: also {
                            SnackbarController.sendEvent("Please select a Boma")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(Dimen.Tiny))
            }
        }
        Spacer(modifier = Modifier.height(bottomPadding))
    }
    if (selectedAreas != null && selectedFilteredAreas != null) {
        SearchableSpinnerDialog(
            type = selectedAreas?.firstOrNull()?.type().orEmpty(),
            onItemSelected = onItemSelected@{
                selectedAreas = null
                selectedFilteredAreas = null
                focusManager.clearFocus()
                if (it as? Area? == null) return@onItemSelected
                val (newBoma, newPayam, newCounty, newState) =
                    it.getParents(uiState.geoData)

                onUiStateChange(
                    uiState.copy(
                        boma = newBoma,
                        payam = newPayam,
                        county = newCounty,
                        state = newState
                    )
                )
            },
            items = selectedAreas ?: return,
            preFilteredItems = selectedFilteredAreas ?: return,
        )
    }
}

@Preview
@Composable
private fun DownloadScreenPre() {
    AttendanceTheme {
        DownloadScreen(
            modifier = Modifier.fillMaxSize().background(Color.White),
            uiState = DownloadScreenUiState(
                geoData = GeoData(
                    states = listOf(State(1, 1, "Tehran")),
                    counties = listOf(County(1, 1, "Tehran")),
                    payams = listOf(Payam(1, 1, "Tehran")),
                    bomas = listOf(Boma(1, 1, "Tehran"))
                )
            ),
            bottomPadding = Dimen.Medium
        )
    }
}
