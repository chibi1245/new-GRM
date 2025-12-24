package com.snsop.attendance.presentation.screens.attendance

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import com.snsop.attendance.domain.model.BomaItem
import com.snsop.attendance.presentation.components.PrimaryScaffold
import com.snsop.attendance.presentation.screens.attendance.components.BomaItemCard
import com.snsop.attendance.ui.theme.AttendanceTheme

@Composable
fun BomaListScreen(
    modifier: Modifier = Modifier,
    bomas: List<BomaItem>,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onBomaItemClick: (BomaItem) -> Unit,
    onQrScan: () -> Unit,
    onBack: () -> Unit
) {
    val listState = rememberLazyListState()
    val expandedFab by remember { derivedStateOf { listState.firstVisibleItemIndex == 0 } }

    PrimaryScaffold(
        modifier = modifier,
        title = "Boma List",
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
        ){
            if (bomas.isEmpty()) {
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
                items(bomas) {boma ->
                    BomaItemCard(
                        bomaItem = boma,
                        onBomaItemClick = {
                            onBomaItemClick(boma)
                        }
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
private fun BomaListScreenPrev() {
    AttendanceTheme {
        BomaListScreen(
            bomas = listOf(
                BomaItem(),
                BomaItem(),
                BomaItem(),
                BomaItem(),
            ),
            isRefreshing = false,
            onRefresh = {},
            onBomaItemClick = {},
            onBack = {},
            onQrScan = {}
        )
    }

}