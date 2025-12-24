package com.snsop.attendance.presentation.navigation

import androidx.navigation3.runtime.NavKey
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule

@Serializable
object Screens{
    @Serializable data object Login : NavKey
    @Serializable data object Home : NavKey
    @Serializable data object QrScanner : NavKey
    @Serializable data object BomaList : NavKey
    @Serializable data object BeneficiaryList : NavKey
    @Serializable data class MarkAttendance(
        val type: String = "Save",
        val present: Boolean = false,
        val ecdAttended: Boolean = false,
        val finLitAttended: Boolean = false,
        val gbvAttended: Boolean = false,
        val washAttended: Boolean = false
    ) : NavKey
    @Serializable data object SyncAttendance : NavKey
    @Serializable data object AttendanceList : NavKey

    @Serializable
    object Dialog {
        @Serializable data class DatePicker(val date: Long) : NavKey
        @Serializable data class Error(
            val body: String,
            val title: String = "Warning"
        ) : NavKey
        @Serializable data class Confirmation(
            val isConfirmRed: Boolean = false,
            val message: String,
            val title: String = "Confirmation",
            val confirmText: String = "Confirm",
            val cancelText: String = "Cancel",
            val action : ConfirmAction,
        ) : NavKey
    }
}

enum class ConfirmAction {
    Logout,
    Exit,
}

// Create a SerializersModule to define polymorphism
val navKeySerializersModule = SerializersModule {
    polymorphic(NavKey::class, Screens.Login::class, Screens.Login.serializer())
    polymorphic(NavKey::class, Screens.Home::class, Screens.Home.serializer())
    polymorphic(NavKey::class, Screens.QrScanner::class, Screens.QrScanner.serializer())
    polymorphic(NavKey::class, Screens.BomaList::class, Screens.BomaList.serializer())
    polymorphic(NavKey::class, Screens.BeneficiaryList::class, Screens.BeneficiaryList.serializer())
    polymorphic(NavKey::class, Screens.MarkAttendance::class, Screens.MarkAttendance.serializer())
    polymorphic(NavKey::class, Screens.SyncAttendance::class, Screens.SyncAttendance.serializer())
    polymorphic(NavKey::class, Screens.AttendanceList::class, Screens.AttendanceList.serializer())
    polymorphic(NavKey::class, Screens.Dialog.DatePicker::class, Screens.Dialog.DatePicker.serializer())
    polymorphic(NavKey::class, Screens.Dialog.Error::class, Screens.Dialog.Error.serializer())
    polymorphic(NavKey::class, Screens.Dialog.Confirmation::class, Screens.Dialog.Confirmation.serializer())
}

val navConfiguration = SavedStateConfiguration {
    serializersModule = navKeySerializersModule
}