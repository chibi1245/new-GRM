package com.snsop.attendance

import android.os.Build

actual fun getPlatform(): Platform= Platform.Android.apply {
    info = "Android ${Build.VERSION.SDK_INT}"
}