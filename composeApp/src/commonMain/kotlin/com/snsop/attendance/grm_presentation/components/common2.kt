package com.snsop.attendance.grm_presentation.components


import androidx.compose.foundation.layout.*

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBackIos
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.SubcomposeAsyncImageContent
import com.snsop.attendance.ui.theme.AttendanceTheme
import com.snsop.attendance.ui.theme.Dimen
import com.snsop.attendance.utils.bounceClick
import com.snsop.attendance.utils.bounceOnClick
import com.snsop.attendance.utils.singleClick
import kotlin.io.encoding.Base64

/* -------------------------------------------------------------------------
 * TEXT COMPONENTS
 * ------------------------------------------------------------------------- */

@Composable
fun HeadlineLargeText(text: String, color: Color = MaterialTheme.colorScheme.onBackground) {
    Text(text, style = MaterialTheme.typography.headlineLarge, color = color)
}

@Composable
fun BodyLargeText(text: String) {
    Text(text, style = MaterialTheme.typography.bodyLarge)
}

/* -------------------------------------------------------------------------
 * GENERIC OUTLINED TEXTFIELD
 * ------------------------------------------------------------------------- */

@Composable
fun PrimaryOutlinedTextField(
    state: TextFieldState,
    label: String,
    placeholder: String,
    modifier: Modifier = Modifier.fillMaxWidth(),
    isError: Boolean = false,
    errorText: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    OutlinedTextField(
        value = state.text.toString(),
        onValueChange = { state.edit { replace(0, state.text.length, it) } },
        modifier = modifier,
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        trailingIcon = trailingIcon,
        isError = isError,
        supportingText = {
            if (errorText.isNotEmpty()) Text(errorText, color = MaterialTheme.colorScheme.error)
        },
        keyboardOptions = keyboardOptions,
        singleLine = true
    )
}

/* -------------------------------------------------------------------------
 * PASSWORD FIELD  (STATE VERSION)
 * ------------------------------------------------------------------------- */

@Composable
fun PrimaryOutlinedPasswordField(
    state: TextFieldState,
    label: String,
    placeholder: String,
    modifier: Modifier = Modifier.fillMaxWidth(),
    isError: Boolean = false,
    errorText: String = "",
    showPassword: Boolean = false,
    onShowPasswordToggle: () -> Unit = {},
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onKeyboardAction: () -> Unit = {}
) {
    OutlinedTextField(
        value = state.text.toString(),
        onValueChange = { state.edit { replace(0, state.text.length, it) } },
        modifier = modifier,
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        singleLine = true,
        isError = isError,
        supportingText = {
            if (errorText.isNotEmpty()) Text(errorText, color = MaterialTheme.colorScheme.error)
        },
        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = onShowPasswordToggle) {
                Icon(
                    if (showPassword) Icons.Rounded.Visibility else Icons.Rounded.VisibilityOff,
                    contentDescription = null
                )
            }
        },
        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions(onDone = { onKeyboardAction() })
    )
}

/* -------------------------------------------------------------------------
 * PASSWORD FIELD  (STRING VERSION)
 * ------------------------------------------------------------------------- */

@Composable
fun PrimaryOutlinedPasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    errorText: String = "",
    isError: Boolean = false,
    showPassword: Boolean = false,
    onShowPasswordToggle: () -> Unit = {}
) {
    PrimaryOutlinedPasswordField(
        state = rememberTextFieldState(value),
        label = label,
        placeholder = placeholder,
        errorText = errorText,
        isError = isError,
        showPassword = showPassword,
        onShowPasswordToggle = onShowPasswordToggle
    )

    // keep value sync
    LaunchedEffect(value) {
        onValueChange(value)
    }
}

/* -------------------------------------------------------------------------
 * BUTTON
 * ------------------------------------------------------------------------- */

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier.fillMaxWidth()
) {
    Button(
        onClick = singleClick(onClick),
        modifier = modifier
            .height(Dimen.responsiveButtonHeight())
            .bounceOnClick()
    ) {
        Text(text)
    }
}

/* -------------------------------------------------------------------------
 * REMEMBER ME
 * ------------------------------------------------------------------------- */

@Composable
fun RememberMeCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.bounceClick { onCheckedChange(!checked) }
    ) {
        Checkbox(checked = checked, onCheckedChange = onCheckedChange)
        Text("Remember me")
    }
}
