package com.snsop.attendance.presentation.screens.attendance

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FlashOff
import androidx.compose.material.icons.rounded.FlashOn
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.snsop.attendance.presentation.components.PrimaryScaffold
import com.snsop.attendance.ui.theme.AttendanceTheme
import com.snsop.attendance.ui.theme.Dimen
import com.snsop.attendance.utils.singleClick
import org.publicvalue.multiplatform.qrcode.CameraPosition
import org.publicvalue.multiplatform.qrcode.CodeType
import org.publicvalue.multiplatform.qrcode.ScannerWithPermissions

@Composable
fun QrScannerScreen(
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit,
    onBack: () -> Unit
) {
    var flashlightOn by remember { mutableStateOf(false) }

    PrimaryScaffold(
        modifier = modifier,
        title = "Scan Beneficiary QR",
        onBack = onBack
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(bottom = bottomPadding + 100.dp)
        ) {
            Box(
                modifier = Modifier
                    .padding(Dimen.LargePlus)
                    .size(200.dp)
                    .clip(MaterialTheme.shapes.large)
                    .background(Color.DarkGray)
            ) {
                ScannerWithPermissions(
                    onScanned = { qrCode ->
                        qrCode.isNotBlank().also {
                            if (it) onNavigate(qrCode)
                        }
                    },
                    types = listOf(CodeType.QR),
                    cameraPosition = CameraPosition.BACK,
                    enableTorch = flashlightOn
                )
//            QrScanner(
//                modifier = Modifier.fillMaxSize(),
//                flashlightOn = flashlightOn,
//                cameraLens = CameraLens.Back,
//                openImagePicker = openImagePicker,
//                onCompletion = {
//                    qrCode = it
//                },
//                imagePickerHandler = {
//                    openImagePicker = it
//                },
//                onFailure = {
//                    SnackbarController.sendEvent(it.ifBlank { "Invalid qr code" })
//                }
//            )
            }
            Button(onClick = singleClick {
                flashlightOn = !flashlightOn
            }){
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(Dimen.Medium)
                ) {
                    Text(
                        text = if (flashlightOn) "Flash On" else "Flash Off",
                        style = MaterialTheme.typography.titleSmall
                    )
                    Icon(
                        imageVector = if (flashlightOn) Icons.Rounded.FlashOn else Icons.Rounded.FlashOff,
                        contentDescription = "flash",
                    )
                }
            }
        }


    }
}


@Preview
@Composable
private fun QrScannerViewPrev() {
    AttendanceTheme {
        QrScannerScreen(
            onNavigate = {},
            onBack = {}
        )
    }
}