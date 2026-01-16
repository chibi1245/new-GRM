package com.snsop.attendance

import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/* ---------------------------------------------------
   OCR SCREEN
--------------------------------------------------- */

@Composable
fun OcrScreen(
    viewModel: OcrViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val context = LocalContext.current

    val fields by viewModel.fields.collectAsState()
    val loading by viewModel.loading.collectAsState()

    val picker =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri ?: return@rememberLauncherForActivityResult

            val bitmap =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    ImageDecoder.decodeBitmap(
                        ImageDecoder.createSource(context.contentResolver, uri)
                    )
                } else {
                    MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
                }

            viewModel.scan(bitmap)
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Button(
            onClick = { picker.launch("image/*") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Scan Document")
        }

        Spacer(Modifier.height(16.dp))

        if (loading) {
            CircularProgressIndicator()
            Spacer(Modifier.height(16.dp))
        }

        if (fields.isNotEmpty()) {

            val entries = fields.entries.toList()

            // 🔹 First value = document header (School / Country / Institution)
            val headerTitle = entries.first().value

            SectionCard(title = headerTitle) {
                entries.drop(1).forEach { (label, value) ->
                    KeyValueRow(
                        label = label,
                        value = value
                    )
                }
            }
        }
    }
}

/* ---------------------------------------------------
   UI COMPONENTS
--------------------------------------------------- */

@Composable
fun SectionCard(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(12.dp))
            content()
        }
    }
}

@Composable
fun KeyValueRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.End
        )
    }
}

/* ---------------------------------------------------
   PREVIEW
--------------------------------------------------- */

@Preview(showBackground = true)
@Composable
private fun OcrScreenPreview() {
    val fakeFields = mapOf(
        "INSTITUTION" to "DAYSTAR UNIVERSITY",
        "SURNAME" to "OTIENO",
        "GIVEN NAME" to "DOLLIE UVETTAH",
        "STUDENT NO" to "23-1788",
        "COURSE" to "BSC COMPUTER SCIENCE"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        SectionCard(title = fakeFields.values.first()) {
            fakeFields.entries.drop(1).forEach {
                KeyValueRow(it.key, it.value)
            }
        }
    }
}
