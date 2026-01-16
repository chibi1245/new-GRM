package com.snsop.attendance
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OcrViewModel : ViewModel() {

    private val ocrManager = OcrManager()
    private val gemini = GeminiFormatter()

    private val _rawText = MutableStateFlow("")
    val rawText = _rawText.asStateFlow()

    private val _fields = MutableStateFlow<Map<String, String>>(emptyMap())
    val fields = _fields.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    fun scan(bitmap: Bitmap) {
        _loading.value = true

        ocrManager.scan(
            bitmap,
            onResult = { text ->
                _rawText.value = text

                viewModelScope.launch {
                    _fields.value = gemini.format(text)
                    _loading.value = false
                }
            },
            onError = {
                _loading.value = false
            }
        )
    }
}
