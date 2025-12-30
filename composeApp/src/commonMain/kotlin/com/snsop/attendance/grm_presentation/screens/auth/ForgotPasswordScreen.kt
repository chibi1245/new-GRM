package com.snsop.attendance.grm_presentation.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.snsop.attendance.grm_presentation.components.PrimaryOutlinedTextField
import com.snsop.attendance.presentation.SignInViewModel
import com.snsop.attendance.presentation.components.PrimaryButton
import com.snsop.attendance.ui.theme.AttendanceTheme
import com.snsop.attendance.ui.theme.black
import com.snsop.attendance.ui.theme.primary
import com.snsop.attendance.ui.theme.ribeyeSub

/* -----------------------------------------------------
 * REAL SCREEN (USED IN NAVIGATION)
 * ----------------------------------------------------- */

@Composable
fun ForgotPasswordScreen(
    viewModel: SignInViewModel,
    backStack: NavBackStack<NavKey>,
    modifier: Modifier
) {
    ForgotPasswordContent(
        mobileState = viewModel.forgotMobileState,
        loading = viewModel.forgotLoading,
        onBack = { backStack.removeLast() },
        onSendCode = viewModel::sendForgotPasswordCode
    )
}

/* -----------------------------------------------------
 * UI CONTENT (PREVIEW SAFE)
 * ----------------------------------------------------- */

@Composable
private fun ForgotPasswordContent(
    mobileState: TextFieldState,
    loading: Boolean,
    onBack: () -> Unit,
    onSendCode: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                containerColor = primary,
            ),
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                title = {}
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(Modifier.height(32.dp))

            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = primary,
                modifier = Modifier.size(40.dp)
            )

            Spacer(Modifier.height(8.dp))

            Text("GRM")
            Text(
                text = "GRIEVANCE REDRESS MECHANISM",
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(32.dp))

            Text(
                text = "Please type your mobile number",
                style = ribeyeSub(primary)
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "No worries, we will send you pin code in your phone number",
                textAlign = TextAlign.Center,

            )

            Spacer(Modifier.height(24.dp))

            PrimaryOutlinedTextField(
                state = mobileState,
                label = "Mobile number",
                placeholder = "Mobile number (in English)"
            )

            Spacer(Modifier.height(32.dp))

            PrimaryButton(
                text = if (loading) "Sending..." else "Send Code",
                enabled = !loading,
                onClick = onSendCode
            )
        }
    }
}

/* -----------------------------------------------------
 * PREVIEW
 * ----------------------------------------------------- */

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ForgotPasswordPreview() {
    AttendanceTheme {
        ForgotPasswordContent(
            mobileState = remember { TextFieldState("0712345678") },
            loading = false,
            onBack = {},
            onSendCode = {}
        )
    }
}
