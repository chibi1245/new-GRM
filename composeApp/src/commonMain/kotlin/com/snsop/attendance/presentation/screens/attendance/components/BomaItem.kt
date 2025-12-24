package com.snsop.attendance.presentation.screens.attendance.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
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
import com.snsop.attendance.domain.model.BomaItem
import com.snsop.attendance.presentation.components.PrimaryOutlinedCard
import com.snsop.attendance.ui.theme.AttendanceTheme
import com.snsop.attendance.ui.theme.Dimen
import com.snsop.attendance.utils.bounceOnClick
import com.snsop.attendance.utils.toDateTime
import com.snsop.attendance.utils.toTitleName


@Composable
fun BomaItemCard(
    bomaItem: BomaItem,
    onBomaItemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    PrimaryOutlinedCard(
        modifier = modifier
            .padding(horizontal = Dimen.Standard, vertical = Dimen.Medium)
            .bounceOnClick(),
        onClick = onBomaItemClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimen.Small)
                .padding(Dimen.Standard),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = bomaItem.name.toTitleName().ifBlank { "Unnamed Boma" },
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(Dimen.Small))
                Text(
                    text = "Beneficiaries: ${bomaItem.totalBeneficiary}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(Dimen.Small))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(Dimen.Tiny),
                    modifier = Modifier.padding(end = Dimen.Small)
                ) {
                    if (bomaItem.lastUpdated > 0) {
                        Text(
                            text = "Updated: ",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = bomaItem.lastUpdated.toDateTime(true),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

// Example usage with state hoisting
@Composable
@Preview(showBackground = true)
fun BomaPreview(
    bomaItems: List<BomaItem> = listOf(
        BomaItem(
            id = 5455,
            name = "Boma 1",
            totalBeneficiary = 100
        ),
        BomaItem(
            id = 1343,
            name = "Boma 3",
            totalBeneficiary = 69
        ),
    )
) {
    var expandedItemId by remember { mutableStateOf<Int?>(null) }

    AttendanceTheme {
        Column(modifier = Modifier.fillMaxWidth()) {
            bomaItems.forEach { bomaItem ->
                BomaItemCard(
                    bomaItem = bomaItem,
                    onBomaItemClick = {
                        expandedItemId = if (expandedItemId == bomaItem.id) null else bomaItem.id
                    },
                )
            }
        }
    }
}