package com.snsop.attendance.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.snsop.attendance.domain.model.ui.ActionItem
import com.snsop.attendance.ui.theme.Dimen
import com.snsop.attendance.utils.bounceOnClick
import com.snsop.attendance.utils.singleClick

@Composable
fun ActionCard(actionItem: ActionItem, modifier: Modifier = Modifier) {
    PrimaryOutlinedCard(
        modifier = modifier.bounceOnClick(),
        borderColor = actionItem.statusColor,
        onClick = singleClick(actionItem.onClick),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimen.Standard),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(Dimen.Huge)
                        .clip(RoundedCornerShape(Dimen.Standard))
                        .background(actionItem.iconBackgroundColor),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = actionItem.icon,
                        contentDescription = actionItem.title,
                        tint = Color.White,
                        modifier = Modifier.size(Dimen.IconSizeLarge)
                    )
                }

                Spacer(modifier = Modifier.width(Dimen.Standard))

                Column {
                    Text(
                        text = actionItem.title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(Dimen.Small))
                    Text(
                        text = actionItem.subtitle,
                        fontSize = 12.sp,
                        color = Color.Gray,
                        lineHeight = 16.sp
                    )
                    Spacer(modifier = Modifier.height(Dimen.Medium))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(actionItem.statusColor)
                        )
                        Spacer(modifier = Modifier.width(Dimen.Medium))
                        Text(
                            text = actionItem.status,
                            fontSize = 12.sp,
                            color = actionItem.statusColor,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Icon(
                imageVector = Icons.Rounded.ChevronRight,
                contentDescription = "Navigate",
                tint = Color.Gray,
                modifier = Modifier.size(Dimen.IconSizeLarge)
            )
        }
    }
}