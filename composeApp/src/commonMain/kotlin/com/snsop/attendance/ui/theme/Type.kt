package com.snsop.attendance.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import attendance.composeapp.generated.resources.Res
import attendance.composeapp.generated.resources.balsamiq_bold
import attendance.composeapp.generated.resources.balsamiq_regular
import attendance.composeapp.generated.resources.cosmic_bold
import attendance.composeapp.generated.resources.cosmic_regular
import org.jetbrains.compose.resources.Font


@Composable
fun bodyFontFamily() = FontFamily(
    Font(Res.font.cosmic_regular, weight = FontWeight.Normal),
    Font(Res.font.cosmic_bold, weight = FontWeight.Bold)
)

@Composable
fun displayFontFamily() = FontFamily(
    Font(Res.font.balsamiq_regular, weight = FontWeight.Normal),
    Font(Res.font.balsamiq_bold, weight = FontWeight.Bold)
)

val baseline = Typography()

@Composable
fun appTypography() = Typography(
    displayLarge = baseline.displayLarge.copy(fontFamily = displayFontFamily()),
    displayMedium = baseline.displayMedium.copy(fontFamily = displayFontFamily()),
    displaySmall = baseline.displaySmall.copy(fontFamily = displayFontFamily()),
    headlineLarge = baseline.headlineLarge.copy(fontFamily = displayFontFamily()),
    headlineMedium = baseline.headlineMedium.copy(fontFamily = displayFontFamily()),
    headlineSmall = baseline.headlineSmall.copy(fontFamily = displayFontFamily()),
    titleLarge = baseline.titleLarge.copy(fontFamily = displayFontFamily()),
    titleMedium = baseline.titleMedium.copy(fontFamily = displayFontFamily()),
    titleSmall = baseline.titleSmall.copy(fontFamily = displayFontFamily()),
    bodyLarge = baseline.bodyLarge.copy(fontFamily = bodyFontFamily()),
    bodyMedium = baseline.bodyMedium.copy(fontFamily = bodyFontFamily()),
    bodySmall = baseline.bodySmall.copy(fontFamily = bodyFontFamily()),
    labelLarge = baseline.labelLarge.copy(fontFamily = bodyFontFamily()),
    labelMedium = baseline.labelMedium.copy(fontFamily = bodyFontFamily()),
    labelSmall = baseline.labelSmall.copy(fontFamily = bodyFontFamily()),
)@Composable
fun ribeyeTitle(color: Color) = TextStyle(
        fontFamily = displayFontFamily(),
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
        color = color
    )

/**
 * Flutter:
 * TextStyle ribeyesub(Color color)
 */
@Composable
fun ribeyeSub(color: Color) = TextStyle(
    fontFamily = displayFontFamily(),
    fontSize = 16.sp,
    fontWeight = FontWeight.Bold,
    color = color
)


@Composable
fun rethinkFont(color: Color) = TextStyle(
    fontFamily = bodyFontFamily(),
    fontSize = 13.sp,
    fontWeight = FontWeight.Normal,
    color = color
)