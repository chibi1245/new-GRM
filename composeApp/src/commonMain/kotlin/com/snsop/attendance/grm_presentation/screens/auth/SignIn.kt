package com.snsop.attendance.grm_presentation.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.snsop.attendance.presentation.SignInViewModel
import com.snsop.attendance.grm_presentation.components.*
import com.snsop.attendance.presentation.components.PrimaryButton
import com.snsop.attendance.ui.theme.AttendanceTheme
import com.snsop.attendance.ui.theme.Dimen

@Composable
fun SignInScreen(
    viewModel: SignInViewModel,
    backStack: NavBackStack<NavKey>,
    modifier: Modifier = Modifier.fillMaxSize()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { backStack.pop() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                title = {},
            )
        }
    ) { padding ->

        SignInContent(
            modifier = modifier.padding(padding),
            loader = viewModel.loader,
            obscureText = viewModel.obscureText,
            complainantRememberMe = viewModel.complainantRememberMe,
            adminRememberMe = viewModel.adminRememberMe,
            onTogglePassword = viewModel::togglePasswordVisibility,
            onComplainantRememberMeChange = viewModel::onComplainantRememberMeChange,
            onAdminRememberMeChange = viewModel::onAdminRememberMeChange,
            onComplainantLogin = { user, pass ->
                viewModel.mobileComplainant = user
                viewModel.pinComplainant = pass
                viewModel.signInComplainant(backStack)
            },
            onAdminLogin = { user, pass ->
                viewModel.userAdmin = user
                viewModel.pinAdmin = pass
                viewModel.signInAdmin(backStack)
            },
            onForgotPassword = { /* navigate */ },
            onSignUp = { /* navigate */ }
        )
    }
}

private fun NavBackStack<NavKey>.pop() {
    TODO("Not yet implemented")
}

@Composable
fun SignInContent(
    loader: Boolean,
    obscureText: Boolean,
    complainantRememberMe: Boolean,
    adminRememberMe: Boolean,
    onTogglePassword: () -> Unit,
    onComplainantRememberMeChange: (Boolean) -> Unit,
    onAdminRememberMeChange: (Boolean) -> Unit,
    onComplainantLogin: (String, String) -> Unit,
    onAdminLogin: (String, String) -> Unit,
    onForgotPassword: () -> Unit,
    onSignUp: () -> Unit,
    modifier: Modifier = Modifier.fillMaxSize()
) {
    var selectedTab by remember { mutableIntStateOf(0) }

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = Dimen.Large),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(Modifier.height(24.dp))

        Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(40.dp))
        Text("GRM", style = MaterialTheme.typography.headlineLarge)
        Spacer(Modifier.height(4.dp))
        Text(
            "GRIEVANCE REDRESS MECHANISM",
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(24.dp))

        TabRow(selectedTabIndex = selectedTab) {
            Tab(selected = selectedTab == 0, onClick = { selectedTab = 0 }) {
                Text("Complainant", modifier = Modifier.padding(16.dp))
            }
            Tab(selected = selectedTab == 1, onClick = { selectedTab = 1 }) {
                Text("Administrative", modifier = Modifier.padding(16.dp))
            }
        }

        Spacer(Modifier.height(24.dp))

        if (selectedTab == 0) {
            ComplainantLogin(
                loader = loader,
                obscureText = obscureText,
                rememberMe = complainantRememberMe,
                onTogglePassword = onTogglePassword,
                onRememberMeChange = onComplainantRememberMeChange,
                onLoginClick = onComplainantLogin,
                onForgotPassword = onForgotPassword,
                onSignUp = onSignUp
            )
        } else {
            AdminLogin(
                loader = loader,
                obscureText = obscureText,
                rememberMe = adminRememberMe,
                onTogglePassword = onTogglePassword,
                onRememberMeChange = onAdminRememberMeChange,
                onLoginClick = onAdminLogin,
                onForgotPassword = onForgotPassword,
                onSignUp = onSignUp
            )
        }
    }
}

@Composable
private fun ComplainantLogin(
    loader: Boolean,
    obscureText: Boolean,
    rememberMe: Boolean,
    onTogglePassword: () -> Unit,
    onRememberMeChange: (Boolean) -> Unit,
    onLoginClick: (String, String) -> Unit,
    onForgotPassword: () -> Unit,
    onSignUp: () -> Unit
) {
    val username = remember { TextFieldState() }
    val password = remember { TextFieldState() }

    Column {

        Text(
            "To submit a grievance,\nlogin using your mobile number and the pin number sent to your mobile",
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(16.dp))

        PrimaryOutlinedTextField(
            state = username,
            label = "Username",
            placeholder = "Username"
        )

        Spacer(Modifier.height(12.dp))

        PrimaryOutlinedPasswordField(
            state = password,
            label = "Password",
            placeholder = "Password",
            showPassword = !obscureText,
            onShowPasswordToggle = onTogglePassword
        )

        RememberMeCheckbox(
            checked = rememberMe,
            onCheckedChange = onRememberMeChange,

        )

        Spacer(Modifier.height(16.dp))

        PrimaryButton(
            text = if (loader) "Logging in..." else "Login",
            enabled = !loader,
            onClick = { onLoginClick(username.text.toString(), password.text.toString()) }
        )

        SignUpRow(onSignUp)
    }
}

@Composable
private fun AdminLogin(
    loader: Boolean,
    obscureText: Boolean,
    rememberMe: Boolean,
    onTogglePassword: () -> Unit,
    onRememberMeChange: (Boolean) -> Unit,
    onLoginClick: (String, String) -> Unit,
    onForgotPassword: () -> Unit,
    onSignUp: () -> Unit
) {
    val username = remember { TextFieldState() }
    val password = remember { TextFieldState() }

    Column {

        Text("OFFICER'S PANEL", textAlign = TextAlign.Center)

        Spacer(Modifier.height(16.dp))

        PrimaryOutlinedTextField(
            state = username,
            label = "Login using User ID or Username",
            placeholder = "Username"
        )

        Spacer(Modifier.height(12.dp))

        PrimaryOutlinedPasswordField(
            state = password,
            label = "Pin code (in English)",
            placeholder = "Password",
            showPassword = !obscureText,
            onShowPasswordToggle = onTogglePassword
        )

        RememberMeCheckbox(
            checked = rememberMe,
            onCheckedChange = onRememberMeChange,

        )

        Spacer(Modifier.height(16.dp))

        PrimaryButton(
            text = if (loader) "Logging in..." else "Login",
            enabled = !loader,
            onClick = { onLoginClick(username.text.toString(), password.text.toString()) }
        )

        SignUpRow(onSignUp)
    }
}

@Composable
private fun SignUpRow(onSignUp: () -> Unit) {
    Spacer(Modifier.height(16.dp))
    Row(horizontalArrangement = Arrangement.Center) {
        Text("Don't have an account?")
        TextButton(onClick = onSignUp) {
            Text(" Sign Up")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LoginScreenPreview() {
    AttendanceTheme {
        SignInContent(
            loader = false,
            obscureText = true,
            complainantRememberMe = true,
            adminRememberMe = false,
            onTogglePassword = {},
            onComplainantRememberMeChange = {},
            onAdminRememberMeChange = {},
            onComplainantLogin = { _, _ -> },
            onAdminLogin = { _, _ -> },
            onForgotPassword = {},
            onSignUp = {}
        )
    }
}
