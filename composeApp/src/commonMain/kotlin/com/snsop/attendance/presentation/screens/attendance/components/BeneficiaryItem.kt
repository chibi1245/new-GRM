package com.snsop.attendance.presentation.screens.attendance.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.snsop.attendance.presentation.components.CoilImage
import com.snsop.attendance.presentation.components.PrimaryOutlinedCard
import com.snsop.attendance.ui.theme.AttendanceTheme
import com.snsop.attendance.ui.theme.Dimen
import com.snsop.attendance.utils.bounceOnClick
import com.snsop.attendance.utils.toTitleName

@Composable
fun BeneficiaryItem(
    name: String,
    gender: String,
    id: String,
    photo: String? = null,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    PrimaryOutlinedCard(
        modifier = modifier
            .padding(horizontal = Dimen.Standard, vertical = Dimen.Medium)
            .bounceOnClick(),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimen.Standard),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            // --- Avatar circle with initials ---
            CoilImage(name = name, photo = photo)

            Spacer(modifier = Modifier.width(Dimen.Standard))

            // --- Name, gender chip, ID ---
            Column(modifier = Modifier.weight(1f)) {

                Text(
                    text = name.toTitleName(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(Dimen.Small))

                // Gender Chip
                Surface(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = RoundedCornerShape(Dimen.Medium)
                ) {
                    Text(
                        text = gender,
                        modifier = Modifier.padding(
                            horizontal = Dimen.Medium,
                            vertical = Dimen.Tiny
                        ),
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Spacer(modifier = Modifier.height(Dimen.Small))

                Text(
                    text = "ID: $id",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // --- Right arrow icon ---
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                contentDescription = "Details",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(Dimen.IconSizeLarge)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BeneficiaryItemPrev() {
    AttendanceTheme {
        BeneficiaryItem(
            name = "John Doe",
            gender = "Male",
            id = "12345",
            onClick = {}
        )
    }
}