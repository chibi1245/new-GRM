package com.snsop.attendance.presentation.screens.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.contentType
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import attendance.composeapp.generated.resources.Res
import attendance.composeapp.generated.resources.ic_logo
import com.snsop.attendance.presentation.components.BodyLargeText
import com.snsop.attendance.presentation.components.HeadlineLargeText
import com.snsop.attendance.presentation.components.PrimaryButton
import com.snsop.attendance.presentation.components.PrimaryOutlinedPasswordField
import com.snsop.attendance.presentation.components.PrimaryOutlinedTextField
import com.snsop.attendance.presentation.components.RememberMeCheckbox
import com.snsop.attendance.ui.theme.AppShapes
import com.snsop.attendance.ui.theme.AttendanceTheme
import com.snsop.attendance.ui.theme.Dimen
import org.jetbrains.compose.resources.painterResource

@Composable
fun LoginScreen(
    oldUserName: String = "",
    oldPassword: String = "",
    onSignIn: (userName: String, pass: String, rememberMe: Boolean) -> Unit = { _, _, _ -> },
    modifier: Modifier = Modifier.fillMaxSize()
) {
    val focusManager = LocalFocusManager.current
    val userNameState = rememberTextFieldState(oldUserName)
    val passState = rememberTextFieldState(oldPassword)
    var userNameError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    var showPassword by rememberSaveable { mutableStateOf(false) }
    var rememberMe by rememberSaveable { mutableStateOf(true) }

    // Clear errors when text changes
    LaunchedEffect(userNameState.text) {
        userNameError = ""
    }

    LaunchedEffect(passState.text) {
        passwordError = ""
    }

    fun onLogin() {
        focusManager.clearFocus()
        if (userNameState.text.isBlank())
            userNameError = "Username cannot be empty"
        else if (passState.text.isBlank())
            passwordError = "Password cannot be empty"
        else
            onSignIn(userNameState.text.toString(), passState.text.toString(), rememberMe)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .verticalScroll(rememberScrollState())
    )
    {
        Card(
            modifier = Modifier
                .fillMaxWidth(Dimen.cardWidthFraction())
                .padding(Dimen.responsiveHorizontalPadding())
                .padding(vertical = Dimen.Medium)
                .padding(bottom = Dimen.Huge)
                .imePadding(),
            shape = AppShapes.large,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface
            ),
            border = BorderStroke(Dimen.CardBorderWidthThick, MaterialTheme.colorScheme.primary),
            elevation = CardDefaults.cardElevation(Dimen.CardElevation)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(Dimen.responsivePadding())
            )
            {
                Image(
                    painter = painterResource(Res.drawable.ic_logo),
                    contentDescription = "Brand Logo",
                    modifier = Modifier
                        .size(Dimen.responsiveLogoSize())
                        .padding(bottom = Dimen.MediumPlus)
                )

                HeadlineLargeText(text = "Welcome Back", modifier = Modifier.padding(bottom = 4.dp))
                BodyLargeText(
                    text = "Sign in to your account",
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                PrimaryOutlinedTextField(
                    state = userNameState,
                    label = "Email or Username",
                    placeholder = "Enter your email or username",
                    isError = userNameError.isNotEmpty(),
                    errorText = userNameError,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    lineLimits = TextFieldLineLimits.SingleLine,
                    modifier = Modifier
                        .fillMaxWidth()
                        .semantics { contentType = ContentType.Username },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Email,
                            contentDescription = "Email",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                )

                PrimaryOutlinedPasswordField(
                    state = passState,
                    label = "Password",
                    placeholder = "Enter your password",
                    isError = passwordError.isNotEmpty(),
                    errorText = passwordError,
                    showPassword = showPassword,
                    onShowPasswordToggle = { showPassword = !showPassword },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Go
                    ),
                    onKeyboardAction = { performDefaultAction ->
                        onLogin()
                        performDefaultAction()
                    },
                    modifier = Modifier.fillMaxWidth()
                        .semantics { contentType = ContentType.Password },
                )

                RememberMeCheckbox(
                    checked = rememberMe,
                    onCheckedChange = { rememberMe = it },
                    modifier = Modifier.fillMaxWidth().padding(bottom = Dimen.Small)
                )

                PrimaryButton(
                    text = "Sign In",
                    onClick = ::onLogin,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}


@Preview
@Composable
private fun SignInScreenPre() {
    AttendanceTheme {
        LoginScreen(modifier = Modifier.fillMaxSize().background(Color.White))
    }
}