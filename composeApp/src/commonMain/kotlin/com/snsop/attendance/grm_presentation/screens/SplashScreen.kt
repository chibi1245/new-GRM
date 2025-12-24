package com.snsop.attendance.grm_presentation.screens
import ButtonLoader
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale

import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import attendance.composeapp.generated.resources.Res
import attendance.composeapp.generated.resources.ic_logo
import kotlinx.coroutines.delay

import com.snsop.attendance.grm_presentation.components.animation.FadeInWidget
import com.snsop.attendance.grm_presentation.components.animation.SlideUpWidget
import com.snsop.attendance.ui.theme.AttendanceTheme

import com.snsop.attendance.ui.theme.black45
import com.snsop.attendance.ui.theme.primary
import com.snsop.attendance.ui.theme.ribeyeTitle
import com.snsop.attendance.ui.theme.white
import org.jetbrains.compose.resources.painterResource

@Composable
fun SplashScreen(
    onNavigateToOnBoard: () -> Unit
) {
    LaunchedEffect(Unit) {
        delay(2000)
        onNavigateToOnBoard()
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = white
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                // 🔹 Fade-in logo
                FadeInWidget(delay = 0.1) {
                    Image(
                        painter = painterResource(Res.drawable.ic_logo),
                        contentDescription = "App Logo",
                        modifier = Modifier
                            .size(160.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))

                // 🔹 Fade-in title text
                FadeInWidget(delay = 0.4) {
                    Text(
                        text = "Grievance Redress\nMechanism",
                        textAlign = TextAlign.Center,
                        style = ribeyeTitle(primary).copy(
                            shadow = Shadow(
                                color = black45,
                                blurRadius = 6f
                            )
                        )
                    )
                }

                Spacer(modifier = Modifier.height(60.dp))

                // 🔹 Slide-up loader
                SlideUpWidget(delay = 0.8) {
                    ButtonLoader(
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
private fun SplashScreenPreview() {
    AttendanceTheme {
        SplashScreen(    onNavigateToOnBoard = {})
    }
}