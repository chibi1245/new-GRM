package com.snsop.attendance.presentation.screens.attendance

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.QrCodeScanner
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.snsop.attendance.domain.model.Beneficiary
import com.snsop.attendance.presentation.components.PrimaryScaffold
import com.snsop.attendance.presentation.components.SearchField
import com.snsop.attendance.presentation.screens.attendance.components.BeneficiaryItem
import com.snsop.attendance.ui.theme.AttendanceTheme
import com.snsop.attendance.ui.theme.Dimen

@Composable
fun BeneficiaryListScreen(
    modifier: Modifier = Modifier,
    beneficiaries: List<Beneficiary>,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onBeneficiaryClick: (Beneficiary) -> Unit,
    onQrScan: () -> Unit,
    onBack: () -> Unit
) {
    val listState = rememberLazyListState()
    val expandedFab by remember { derivedStateOf { listState.firstVisibleItemIndex == 0 } }

    val searchState = rememberTextFieldState()
    val filteredItems = beneficiaries
        .filter { it.name?.contains(searchState.text, ignoreCase = true) == true }
    PrimaryScaffold(
        modifier = modifier,
        title = "Beneficiary List",
        onBack = onBack,
        fab = {
            ExtendedFloatingActionButton(
                text = { Text("Scan QR") },
                icon = {
                    Icon(
                        imageVector = Icons.Rounded.QrCodeScanner,
                        contentDescription = "Scan QR Code"
                    )
                },
                onClick = onQrScan,
                expanded = expandedFab,
            )
        }
    ) {
        PullToRefreshBox(
            modifier = Modifier.padding(paddingValues),
            isRefreshing = isRefreshing,
            onRefresh = onRefresh,
        ) {
            if (filteredItems.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No Data found")
                }
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = listState,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    SearchField(
                        type = "Name",
                        state = searchState,
                        modifier = Modifier.padding(Dimen.Medium)
                    )
                }
                items(filteredItems) { beneficiary ->
                    BeneficiaryItem(
                        name = beneficiary.name ?: "---",
                        gender = beneficiary.gender ?: "Unknown",
                        id = beneficiary.householdNumber ?: "---",
                        photo = beneficiary.photoData,
                        onClick = { onBeneficiaryClick(beneficiary) }
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(bottomPadding))
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun BeneficiaryListScreenPreview() {

    val dummyBeneficiaries = listOf(
        Beneficiary(
            id = 1,
//            subprojectId = 100,
            name = "Sarah Johnson",
            householdNumber = "APP001",
            age = 28,
            gender = "Female",
            phoneNo = "01710000000",
        ),
        Beneficiary(
            id = 2,
//            subprojectId = 101,
            name = "Michael Smith",
            householdNumber = "APP002",
            age = 34,
            gender = "Male",
            phoneNo = "01820000000",
        ),
        Beneficiary(
            id = 3,
//            subprojectId = 102,
            name = "Amina Kaw",
            householdNumber = "APP003",
            age = 22,
            gender = "Female",
            phoneNo = "01630000000",
        ),
        Beneficiary(
            id = 1,
//            subprojectId = 100,
            name = "Sarah Johnson",
            householdNumber = "APP001",
            age = 28,
            gender = "Female",
            phoneNo = "01710000000",
        ),
        Beneficiary(
            id = 1,
//            subprojectId = 100,
            name = "Sarah Johnson",
            householdNumber = "APP001",
            age = 28,
            gender = "Female",
            phoneNo = "01710000000",
        ),
        Beneficiary(
            id = 1,
//            subprojectId = 100,
            name = "Sarah Johnson",
            householdNumber = "APP001",
            age = 28,
            gender = "Female",
            phoneNo = "01710000000",
        ),
        Beneficiary(
            id = 1,
//            subprojectId = 100,
            name = "Sarah Johnson",
            householdNumber = "APP001",
            age = 28,
            gender = "Female",
            phoneNo = "01710000000",
        ),
        Beneficiary(
            id = 1,
//            subprojectId = 100,
            name = "Sarah Johnson",
            householdNumber = "APP001",
            age = 28,
            gender = "Female",
            phoneNo = "01710000000",
        ),

        )

    AttendanceTheme {
        BeneficiaryListScreen(
            beneficiaries = dummyBeneficiaries,
            onBeneficiaryClick = {},
            onBack = {},
            isRefreshing = false,
            onRefresh = {},
            onQrScan = {}
        )
    }
}



