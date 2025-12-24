package com.snsop.attendance

@Suppress("EnumEntryName")
enum class Platform(var info: String = "") {
    Android,
    iOS
}

expect fun getPlatform(): Platform
