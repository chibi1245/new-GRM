package com.snsop.attendance
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.snsop.attendance.presentation.navigation.Screens

@Composable
expect fun OcrScreenEntry()
fun EntryProviderScope<NavKey>.ocrRoute() {
    entry<Screens.Ocr> {
        OcrScreenEntry()
    }
}
