package com.snsop.attendance.di

import com.snsop.attendance.BuildKonfig
import com.snsop.attendance.data.local.AppSettings
import com.snsop.attendance.utils.isMinusOne
import com.snsop.attendance.utils.log
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.plugin
import io.ktor.client.request.accept
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import ro.cosminmihu.ktor.monitor.ContentLength
import ro.cosminmihu.ktor.monitor.KtorMonitorLogging
import ro.cosminmihu.ktor.monitor.RetentionPeriod

val networkModule = module {
    single {
        val settings = get<AppSettings>()
        HttpClient {
            install(Logging) {
                level = LogLevel.ALL
                logger = object : Logger {
                    override fun log(message: String) {
                        message.log("Ktor")
                    }
                }
            }
            install(KtorMonitorLogging) {
//                sanitizeHeader { header -> header == "Authorization" }
//                filter { request -> !request.url.host.contains("cosminmihu.ro") }
                showNotification = true
                retentionPeriod = RetentionPeriod.OneWeek
                maxContentLength = ContentLength.Full
            }
            install(DefaultRequest) {
                url(BuildKonfig.BASE_URL)
                accept(ContentType.Application.Json)
                settings.token?.let {
                    header(HttpHeaders.Authorization, "Bearer $it")
                }
                settings.userInfo.id.takeIf { !it.isMinusOne() }.let {
                    header("X-User-Id", it)
                }
                settings.userInfo.role.takeIf { it.isNotEmpty() }.let {
                    header("X-Role-Id", it)
                }
            }
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    encodeDefaults = true
                    explicitNulls = false
                })
            }
        }.apply {
            plugin(HttpSend).intercept { request ->
                execute(request).apply {
                    if (response.status == HttpStatusCode.Unauthorized)
                        settings.token = null
                }
            }
        }
    }
}