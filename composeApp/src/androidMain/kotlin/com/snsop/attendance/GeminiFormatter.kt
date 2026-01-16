package com.snsop.attendance

import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class GeminiFormatter {

    // 🔐 API key (keep simple for now)
    private val apiKey = "AIzaSyBzjRVRtxU9k3Ix14bYV9jHUn8EIh_369w"

    suspend fun format(rawText: String): Map<String, String> =
        withContext(Dispatchers.IO) {

            val model = GenerativeModel(
                modelName = "gemini-1.5-flash",
                apiKey = apiKey
            )

            val prompt = """
                You are an OCR post-processing engine.

                Extract ALL meaningful information from the text.
                Preserve original wording.
                Do NOT invent values.
                Do NOT explain.

                Return ONLY valid JSON.

                OCR TEXT:
                $rawText
            """.trimIndent()

            val response = model.generateContent(prompt)
            val jsonText = response.text ?: "{}"

            parseJsonSafely(jsonText)
        }

    private fun parseJsonSafely(json: String): Map<String, String> {
        return try {
            val obj = JSONObject(json)
            val map = mutableMapOf<String, String>()

            obj.keys().forEach { key ->
                map[key.replace("_", " ").uppercase()] =
                    obj.getString(key)
            }

            map
        } catch (e: Exception) {
            emptyMap()
        }
    }
}
