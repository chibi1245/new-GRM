package com.snsop.attendance.presentation.navigation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.snsop.attendance.presentation.navigation.BottomNavItems
import com.snsop.attendance.ui.theme.AppShapes
import com.snsop.attendance.ui.theme.AttendanceTheme
import com.snsop.attendance.ui.theme.Dimen
import com.snsop.attendance.utils.bounceClick
import com.snsop.attendance.utils.singleClick

@Composable
fun FloatingBottomNav(
    pagerState: PagerState = rememberPagerState(initialPage = 0) { BottomNavItems.entries.size },
    onItemClick: (Int) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Row(
//        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(Dimen.responsiveSmallPadding())
            .padding(Dimen.StandardPlus)
            .padding(horizontal = Dimen.Medium)
            .shadow(
                elevation = Dimen.Tiny,
                shape = AppShapes.extraLargeIncreased, // use AppShapes
                clip = false,
                ambientColor = MaterialTheme.colorScheme.onSurface,
                spotColor = MaterialTheme.colorScheme.onSurface
            )
            .border(
                width = 0.2.dp,
                color = MaterialTheme.colorScheme.surfaceContainer,
                shape = AppShapes.extraLargeIncreased
            )
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = AppShapes.extraLargeIncreased
            )
    ) {
        BottomNavItems.entries.forEachIndexed { index, screen ->
            NavItem(
                screen = screen,
                selected = pagerState.currentPage == index,
                onClick = { onItemClick(index) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun NavItem(
    screen: BottomNavItems,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .padding(Dimen.Medium)
            .clip(RoundedCornerShape(Dimen.Large))
            .background(if (selected) MaterialTheme.colorScheme.primaryContainer.copy(.2f) else Color.Transparent)
            .bounceClick(onClick = singleClick(onClick),)
            .padding(Dimen.Medium) // inner padding for the icon
            .padding(Dimen.responsiveSmallPadding())
    ) {
        Icon(
            imageVector = screen.icon,
            contentDescription = screen.name,
            tint = if (selected) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.outline,
            modifier = Modifier.size(Dimen.responsiveIconSize())
        )
    }
}

@Preview
@Composable
private fun NavPre() {
    AttendanceTheme {
        FloatingBottomNav(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(Dimen.Standard)
        )
    }
}
