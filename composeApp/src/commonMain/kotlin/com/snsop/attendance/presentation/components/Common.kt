package com.snsop.attendance.presentation.components

import ThemeExtensions.themeGradient
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBackIos
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedSecureTextField
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldLabelPosition
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.SubcomposeAsyncImageContent
import com.snsop.attendance.ui.theme.AppShapes
import com.snsop.attendance.ui.theme.Dimen
import com.snsop.attendance.utils.bounceClick
import com.snsop.attendance.utils.bounceOnClick
import com.snsop.attendance.utils.singleClick
import com.snsop.attendance.utils.toDateModern
import com.snsop.attendance.utils.toInitial
import kotlin.io.encoding.Base64

// TextComponents.kt
@Composable
fun HeadlineLargeText(
    text: String,
    color: Color = MaterialTheme.colorScheme.onBackground,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.headlineLarge,
        color = color,
        modifier = modifier
    )
}

@Composable
fun BodyLargeText(
    text: String,
    color: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        color = color,
        modifier = modifier
    )
}

@Composable
fun BodyMediumText(
    text: String,
    color: Color = MaterialTheme.colorScheme.onSurface,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        color = color,
        modifier = modifier
    )
}

@Composable
fun ButtonText(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.SemiBold,
        modifier = modifier.padding(vertical = Dimen.Small)
    )
}

@Composable
fun LabelText(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium.copy(
            fontWeight = FontWeight.Medium
        ),
        color = MaterialTheme.colorScheme.onSurface,
        modifier = modifier
    )
}

@Composable
fun ErrorText(
    text: String,
    modifier: Modifier = Modifier
) {
    if (text.isNotEmpty()) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.error,
            modifier = modifier
        )
    }
}

// FormComponents.kt
@Composable
fun PrimaryOutlinedTextField(
    state: TextFieldState,
    modifier: Modifier = Modifier.fillMaxWidth(),
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    labelPosition: TextFieldLabelPosition = TextFieldLabelPosition.Above(),
    label: String,
    placeholder: String,
    errorText: String = "",
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    inputTransformation: InputTransformation? = null,
    outputTransformation: OutputTransformation? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onKeyboardAction: KeyboardActionHandler? = null,
    lineLimits: TextFieldLineLimits = TextFieldLineLimits.Default,
    onTextLayout: (Density.(getResult: () -> TextLayoutResult?) -> Unit)? = null,
    scrollState: ScrollState = rememberScrollState(),
    shape: Shape = MaterialTheme.shapes.medium,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(),
    contentPadding: PaddingValues = OutlinedTextFieldDefaults.contentPadding(),
    interactionSource: MutableInteractionSource? = null,
) {
    OutlinedTextField(
        state = state,
        labelPosition = labelPosition,
        label = { LabelText(label) },
        placeholder = { Text(placeholder, color = MaterialTheme.colorScheme.outline) },
        isError = isError,
        supportingText = { ErrorText(text = errorText) },
        modifier = modifier,
        shape = shape,
        lineLimits = lineLimits,
        keyboardOptions = keyboardOptions,
        onKeyboardAction = onKeyboardAction,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        prefix = prefix,
        suffix = suffix,
        inputTransformation = inputTransformation,
        outputTransformation = outputTransformation,
        onTextLayout = onTextLayout,
        scrollState = scrollState,
        colors = colors,
        contentPadding = contentPadding,
        interactionSource = interactionSource
    )
}

@Composable
fun PrimaryOutlinedPasswordField(
    state: TextFieldState,
    modifier: Modifier = Modifier.fillMaxWidth(),
    enabled: Boolean = true,
    textStyle: TextStyle = LocalTextStyle.current,
    labelPosition: TextFieldLabelPosition = TextFieldLabelPosition.Above(),
    label: String,
    placeholder: String,
    errorText: String,
    showPassword: Boolean = false,
    onShowPasswordToggle: () -> Unit = {},
    leadingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    inputTransformation: InputTransformation? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onKeyboardAction: KeyboardActionHandler? = null,
    onTextLayout: (Density.(getResult: () -> TextLayoutResult?) -> Unit)? = null,
    shape: Shape = MaterialTheme.shapes.medium,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(),
    contentPadding: PaddingValues = OutlinedTextFieldDefaults.contentPadding(),
    interactionSource: MutableInteractionSource? = null,
) {
    OutlinedSecureTextField(
        state = state,
        labelPosition = labelPosition,
        label = { LabelText(label) },
        placeholder = { Text(placeholder, color = MaterialTheme.colorScheme.outline) },
        isError = isError,
        supportingText = { ErrorText(text = errorText) },
        keyboardOptions = keyboardOptions,
        onKeyboardAction = onKeyboardAction,
        modifier = modifier,
        enabled = enabled,
        textStyle = textStyle,
        leadingIcon = leadingIcon,
        trailingIcon = {
            IconButton(
                onClick = onShowPasswordToggle,
                modifier = Modifier.size(Dimen.IconSizeMedium)
            ) {
                Icon(
                    imageVector = if (showPassword) Icons.Rounded.Visibility else Icons.Rounded.VisibilityOff,
                    contentDescription = if (showPassword) "Hide password" else "Show password",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        prefix = prefix,
        suffix = suffix,
        inputTransformation = inputTransformation,
        textObfuscationMode = if (showPassword) TextObfuscationMode.Visible else TextObfuscationMode.RevealLastTyped,
        onTextLayout = onTextLayout,
        shape = shape,
        colors = colors,
        contentPadding = contentPadding,
        interactionSource = interactionSource
    )
}

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    compact: Boolean = false,
    enabled: Boolean = true,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary
) {
    Button(
        onClick = singleClick(onClick),
        modifier = modifier
            .fillMaxWidth()
            .height(Dimen.responsiveButtonHeight() * if (compact) 0.85f else (1f))
            .bounceOnClick(),
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        elevation = if (compact) null else ButtonDefaults.buttonElevation(
            defaultElevation = Dimen.CardElevation,
            pressedElevation = Dimen.Standard
        ),
        enabled = enabled
    ) {
        ButtonText(text = text)
    }
}

@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    compact: Boolean = false,
    enabled: Boolean = true,
    containerColor: Color = MaterialTheme.colorScheme.secondary,
    contentColor: Color = MaterialTheme.colorScheme.primary
) {
    OutlinedButton(
        onClick = singleClick(onClick),
        modifier = modifier
            .fillMaxWidth()
            .height(Dimen.responsiveButtonHeight() * if (compact) 0.85f else (1f))
            .bounceOnClick(),
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        border = BorderStroke(
            width = Dimen.One,
            color = MaterialTheme.colorScheme.primary.copy(if (enabled) 1f else 0.1f)
        ),
        elevation = if (compact) null else ButtonDefaults.buttonElevation(
            defaultElevation = Dimen.CardElevation,
            pressedElevation = Dimen.HugePlus
        ),
        enabled = enabled
    ) {
        ButtonText(text = text)
    }
}


@Composable
fun RememberMeCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.bounceClick(keepRipple = false) { onCheckedChange(!checked) }
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.primary,
                checkmarkColor = MaterialTheme.colorScheme.onPrimary
            ),
            modifier = Modifier
                .scale(.8f)
                .offset(x = -Dimen.MediumPlus)
        )
        BodyMediumText(
            text = "Remember me",
            modifier = Modifier.offset(x = -Dimen.Standard)
        )
    }
}

@Composable
fun SearchField(
    type: String,
    state: TextFieldState,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        state = state,
        modifier = modifier
            .padding(Dimen.responsiveSecondaryHorizontalPadding())
            .fillMaxWidth(),
        placeholder = { Text("Search by $type…") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = "Search",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        lineLimits = TextFieldLineLimits.SingleLine,
        shape = AppShapes.extraExtraLarge,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            focusedPlaceholderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent
        )
    )
}

@Composable
fun DatePickerField(
    value: Long = 0L,
    onShowPicker: () -> Unit = {},
    label: String = "Select Date...",
    modifier: Modifier = Modifier
) {
    Text(
        text = label,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(Dimen.Small)
    )
    OutlinedCard(
        modifier = modifier
            .padding(vertical = Dimen.Small)
            .bounceClick(keepRipple = false, onClick = onShowPicker),
        border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary),
        shape = MaterialTheme.shapes.medium,
    ) {
        Row(
            modifier = Modifier.padding(Dimen.Small),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = value.toDateModern(),
                modifier = Modifier.padding(Dimen.Standard),
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                imageVector = Icons.Rounded.CalendarMonth, // Calendar icon
                contentDescription = "Calendar",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.width(Dimen.Standard))
        }
    }
}

@Preview
@Composable
fun PrimaryOutlinedCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    elevation: CardElevation = CardDefaults.cardElevation(defaultElevation = Dimen.CardElevation),
    borderColor: Color = MaterialTheme.colorScheme.primary,
    border: BorderStroke = BorderStroke(Dimen.CardBorderWidth, borderColor),
    shape: Shape = MaterialTheme.shapes.large,
    content: @Composable ColumnScope.() -> Unit,
) {
    val content: @Composable ColumnScope.() -> Unit = {
        Column(modifier = Modifier.padding(Dimen.responsiveSmallPadding())) {
            content()
        }
    }
    val modifier = modifier
            .fillMaxWidth(Dimen.cardWidthFraction())
            .padding(Dimen.responsiveSmallPadding())

    if (onClick == null) {
        Card(
            elevation = elevation,
            border = border,
            shape = shape,
            content = content,
            modifier = modifier
        )
    } else {
        Card(
            onClick = singleClick(onClick),
            elevation = elevation,
            border = border,
            shape = shape,
            content = content,
            modifier = modifier
        )
    }
}

@Composable
fun PrimaryAppBar(
    title: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    contentColor: Color = MaterialTheme.colorScheme.onBackground,
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = contentColor,
            navigationIconContentColor = contentColor,
            actionIconContentColor = contentColor,
        ),
        navigationIcon = {
            IconButton(
                onClick = singleClick(onBack),
                modifier = Modifier.bounceOnClick()
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBackIos,
                    contentDescription = "Back"
                )
            }
        },
        actions = actions
    )
}

data class PrimaryScaffoldValues(
    val paddingValues: PaddingValues,
    val bottomPadding: Dp
)

@Composable
fun PrimaryScaffold(
    modifier: Modifier = Modifier,
    title: String,
    onBack: () -> Unit,
    contentColor: Color = MaterialTheme.colorScheme.onBackground,
    actions: @Composable RowScope.() -> Unit = {},
    fab: @Composable (() -> Unit) = {},
    content: @Composable PrimaryScaffoldValues.() -> Unit
) {
    Scaffold(
        topBar = {
            PrimaryAppBar(
                title = title,
                onBack = onBack,
                contentColor = contentColor,
                actions = actions
            )
        },
        floatingActionButton = {
            Box(modifier = Modifier.padding(Dimen.responsiveSecondaryPadding())) {
                fab()
            }
        },
        containerColor = Color.Transparent,
        modifier = modifier
            .fillMaxSize()
            .themeGradient()
    ) { innerPadding ->
        content(
            PrimaryScaffoldValues(
                paddingValues = PaddingValues(
                    top = innerPadding.calculateTopPadding(),
                    bottom = 0.dp,
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current)
                ),
                bottomPadding = innerPadding.calculateBottomPadding()
            )
        )
    }
}


@Composable
fun CoilImage(
    name: String?,
    photo: String?,
    modifier: Modifier = Modifier
) {
    val base64ImageByteArray = photo?.let { Base64.decode(it) }
    SubcomposeAsyncImage(
        model = base64ImageByteArray,
        contentDescription = "Profile Image",
        modifier = modifier
            .size(Dimen.ExtraLarge + Dimen.MediumPlus)
            .background(MaterialTheme.colorScheme.primaryContainer, CircleShape),
        loading = {
            ContainedLoadingIndicator(
                modifier = Modifier.size(60.dp)
            )
        },
        error = {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = name?.toInitial() ?: "NA",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        },
        success = {
            SubcomposeAsyncImageContent(
                contentScale = ContentScale.Crop,
                modifier = Modifier.clip(CircleShape)
            )
        }
    )
}





