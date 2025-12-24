package com.snsop.attendance

import androidx.compose.runtime.Composable
import com.snsop.attendance.di.appModules
import com.snsop.attendance.presentation.navigation.SetupNavDisplay
import com.snsop.attendance.ui.theme.AttendanceTheme
import com.snsop.attendance.utils.log
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.compose.KoinMultiplatformApplication
import org.koin.dsl.KoinConfiguration

@Composable
fun App() {
    if(BuildKonfig.IS_DEBUG)
        Napier.base(DebugAntilog())
    getPlatform().log()
    KoinMultiplatformApplication(config = KoinConfiguration { modules(appModules) }) {
        AttendanceTheme {
            SetupNavDisplay()
        }
    }
}