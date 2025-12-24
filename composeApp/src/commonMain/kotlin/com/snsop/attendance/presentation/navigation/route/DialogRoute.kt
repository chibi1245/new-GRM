package com.snsop.attendance.presentation.navigation.route

import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.ui.window.DialogProperties
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.scene.DialogSceneStrategy
import com.snsop.attendance.presentation.MainViewModel
import com.snsop.attendance.presentation.navigation.ConfirmAction
import com.snsop.attendance.presentation.navigation.Screens
import com.snsop.attendance.utils.currentTimestamp

fun EntryProviderScope<NavKey>.dialogRoute(
    viewModel: MainViewModel
){
    entry<Screens.Dialog.Error>(
        metadata = DialogSceneStrategy.dialog(DialogProperties())
    ) { data ->
        AlertDialog(
            title = { Text(text = data.title) },
            text = {
                SelectionContainer {
                    Text(text = data.body)
                }
            },
            onDismissRequest = viewModel.backStack::removeLastOrNull,
            confirmButton = {
                TextButton(
                    onClick = viewModel.backStack::removeLastOrNull,
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                ) {
                    Text(text = "Close")
                }
            }
        )
    }

    entry<Screens.Dialog.Confirmation>(
        metadata = DialogSceneStrategy.dialog(DialogProperties())
    ) { data ->
        AlertDialog(
            title = { Text(text = data.title) },
            text = {
                SelectionContainer {
                    Text(text = data.message, style = MaterialTheme.typography.bodyMedium)
                }
           },
            onDismissRequest = viewModel.backStack::removeLastOrNull,
            confirmButton = {
                TextButton(
                    onClick = {
                        when (data.action) {
                            ConfirmAction.Exit ->
                                viewModel.backStack.clear()
                            ConfirmAction.Logout ->
                                viewModel.logout(viewModel.backStack)
                        }
                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = if (data.isConfirmRed)
                            MaterialTheme.colorScheme.error
                        else
                            MaterialTheme.colorScheme.onPrimaryContainer
                    )
                ) {
                    Text(text = data.confirmText)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = viewModel.backStack::removeLastOrNull,
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                ) {
                    Text(text = data.cancelText)
                }
            }
        )
    }

    entry<Screens.Dialog.DatePicker>(
        metadata = DialogSceneStrategy.dialog(DialogProperties())
    ) {
        val datePickerState =
            rememberDatePickerState(initialSelectedDateMillis = it.date)
        DatePickerDialog(
            onDismissRequest = viewModel.backStack::removeLastOrNull, // Close the dialog when dismissed
            confirmButton = {
                TextButton(onClick = {
                    val selectedDate = datePickerState.selectedDateMillis ?: currentTimestamp
                    viewModel.updateSelectedDate(selectedDate)  // Update the selected date
                    viewModel.backStack.removeLastOrNull()
                }) {
                    Text("Okay")
                }
            },
            dismissButton = {
                TextButton(onClick = viewModel.backStack::removeLastOrNull) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}