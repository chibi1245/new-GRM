package com.snsop.attendance

import platform.UIKit.UIDevice

actual fun getPlatform(): Platform = Platform.iOS.apply {
    info = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}
