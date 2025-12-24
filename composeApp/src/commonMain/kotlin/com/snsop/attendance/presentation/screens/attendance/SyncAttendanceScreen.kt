package com.snsop.attendance.presentation.screens.attendance

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.snsop.attendance.domain.model.BomaItem
import com.snsop.attendance.presentation.components.PrimaryScaffold
import com.snsop.attendance.presentation.screens.attendance.components.BomaItemCard
import com.snsop.attendance.presentation.screens.home.StatItem
import com.snsop.attendance.presentation.ui_states.SyncAttendanceUiState
import com.snsop.attendance.ui.theme.AttendanceTheme
import com.snsop.attendance.ui.theme.Dimen
import com.snsop.attendance.utils.bounceClick

@Composable
fun SyncAttendanceScreen(
    modifier: Modifier = Modifier,
    uiState: SyncAttendanceUiState,
    onSync: () -> Unit,
    onBomaItemClick: (BomaItem) -> Unit,
    onBack: () -> Unit
) {
    PrimaryScaffold(
        modifier = modifier,
        title = "Sync ${uiState.type} Attendance",
        onBack = onBack
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                Spacer(modifier = Modifier.height(Dimen.responsiveSecondaryPadding()))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(Dimen.Standard),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimen.responsiveHorizontalPadding())
                        .height(IntrinsicSize.Max)
                ) {
                    StatItem(
                        value = uiState.unSyncedAttendanceCount.toString(),
                        label = "Unsynced\nAttendance",
                        modifier = Modifier.weight(1f).fillMaxHeight()
                    )

                    StatItem(
                        value = uiState.syncedAttendanceCount.toString(),
                        label = "Synced\nAttendance",
                        valueColor = Color(0xFF4CAF50),
                        modifier = Modifier.weight(1f).fillMaxHeight()
                    )
                }
                Spacer(modifier = Modifier.height(Dimen.Medium))
                BigButton(
                    text = "Sync All",
                    onClick = onSync,
                    modifier = Modifier
                        .padding(Dimen.responsiveHorizontalPadding())
                )
                Spacer(modifier = Modifier.height(Dimen.responsiveSecondaryPadding()))
                Text(
                    text = "See By Boma",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimen.responsiveHorizontalPadding())
                        .padding(top = Dimen.Medium),
                )
            }
            if (uiState.bomas.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier.fillParentMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No Data found")
                    }
                }
            }
            items(uiState.bomas) {boma ->
                BomaItemCard(
                    bomaItem = boma,
                    onBomaItemClick = {
                        onBomaItemClick(boma)
                    },
                )
            }
            item {
                Spacer(modifier = Modifier.height(bottomPadding))
            }
        }
    }
}

@Composable
fun BigButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = Dimen.Medium)
            .bounceClick(onClick = onClick)
            .clip(RoundedCornerShape(Dimen.Standard))
            .background(MaterialTheme.colorScheme.primary.copy(.9f))
            .padding(Dimen.Standard)
    ) {
        Text(
            text = text,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SyncAttendanceScreenPrev() {
    AttendanceTheme {
        SyncAttendanceScreen(
            onBack = {},
            onBomaItemClick = {},
            onSync = {},
            uiState = SyncAttendanceUiState(
                bomas = listOf(
                    BomaItem(),
                    BomaItem(),
                    BomaItem(),
                    BomaItem()
                )
            )
        )
    }
}