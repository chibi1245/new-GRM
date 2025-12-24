package com.snsop.attendance.grm_presentation.components.animation
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.coroutines.delay

@Composable
fun FadeInWidget(
    delay: Double = 0.0,
    content: @Composable () -> Unit
) {
    val visible = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay((delay * 1000).toLong())
        visible.value = true
    }

    AnimatedVisibility(
        visible = visible.value,
        enter = fadeIn()
    ) {
        content()
    }
}
