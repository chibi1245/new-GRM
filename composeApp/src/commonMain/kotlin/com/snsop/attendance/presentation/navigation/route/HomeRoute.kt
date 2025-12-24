package com.snsop.attendance.presentation.navigation.route

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.snsop.attendance.presentation.MainViewModel
import com.snsop.attendance.presentation.navigation.BottomNavItems
import com.snsop.attendance.presentation.navigation.Screens
import com.snsop.attendance.presentation.navigation.components.FloatingBottomNav
import com.snsop.attendance.ui.theme.Dimen
import com.snsop.attendance.ui.theme.Dimen.responsiveSecondaryPadding
import kotlinx.coroutines.launch

fun EntryProviderScope<NavKey>.homeRoute(
    viewModel: MainViewModel
) {
    entry<Screens.Home> {
        val pagerState = rememberPagerState(pageCount = { BottomNavItems.entries.size })
        val scope = rememberCoroutineScope()
        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                TopAppBar(
                    title = {
                        Text(BottomNavItems.entries[pagerState.currentPage].name)
                    },
                    expandedHeight = Dimen.ExtraLarge,
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        scrolledContainerColor = Color.Transparent
                    ),
                    scrollBehavior = scrollBehavior,
                    modifier = Modifier.graphicsLayer {
                        translationY = Dimen.MediumPlus.toPx()
                        translationX = Dimen.Small.toPx()
                    }.padding(responsiveSecondaryPadding())
                )
            },
            containerColor = Color.Transparent,
            bottomBar = {
                FloatingBottomNav(
                    pagerState = pagerState,
                    onItemClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(it)
                        }
                    }
                )
            }
        ) { innerPadding ->
            HomePagerRoute(
                pagerState = pagerState,
                viewModel = viewModel,
                modifier = Modifier.fillMaxSize().padding(top = innerPadding.calculateTopPadding()),
                bottomPadding = innerPadding.calculateBottomPadding()
            )
        }
//        BackHandler(pagerState.currentPage != 0) {
//
//        }
    }

}