package com.snsop.attendance.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldLabelPosition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.tooling.preview.Preview
import com.snsop.attendance.domain.model.ui.SpinnerItem
import com.snsop.attendance.ui.theme.AppShapes
import com.snsop.attendance.ui.theme.Dimen
import com.snsop.attendance.utils.toTitleName

@Composable
fun SpinnerTextField(
    selectedItem: String? = "",
    label: String,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        state = TextFieldState(selectedItem.orEmpty()),
        readOnly = true,
        lineLimits = TextFieldLineLimits.SingleLine,
        labelPosition = TextFieldLabelPosition.Above(),
        label = {
            Text(text = label, style = MaterialTheme.typography.labelLarge)
        },
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { if (it.isFocused) onClick() },
        placeholder = { Text("Select $label") },
        trailingIcon = {
            ExposedDropdownMenuDefaults.TrailingIcon(expanded = false)
        },
        shape = AppShapes.medium,
        colors = OutlinedTextFieldDefaults.colors()
    )
}

@Composable
@Preview(showBackground = true)
fun SearchableSpinnerDialog(
    type: String = "",
    onItemSelected: (SpinnerItem?) -> Unit = {},
    items: List<SpinnerItem> = listOf(),
    preFilteredItems: List<SpinnerItem> = listOf()
) {
    val state = rememberTextFieldState()
    val (showPreFilteredItems, setShowPreFilteredItems) = remember { mutableStateOf(true) }
    val filteredItems = (if (showPreFilteredItems) preFilteredItems else items)
        .filter { it.name.contains(state.text, ignoreCase = true) }

    BasicAlertDialog(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(.7f)
            .clip(AppShapes.large)
            .background(MaterialTheme.colorScheme.surface),
        onDismissRequest = { onItemSelected(null) }
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = "Select $type",
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.onPrimary
                )
                IconButton(
                    onClick = { onItemSelected(null) },
                    modifier = Modifier.align(Alignment.CenterEnd),
                    colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.onPrimary)
                ) {
                    Icon(Icons.Rounded.Close, contentDescription = "Close")
                }
            }
            SearchField(
                type = type,
                state = state,
                modifier = Modifier.padding(Dimen.Medium)
            )
            LazyColumn {
                item {
                    AnimatedSelector(
                        name = "Show Filtered",
                        description = "Show $type based on other fields",
                        isPositive = showPreFilteredItems,
                        onToggle = setShowPreFilteredItems,
                        modifier = Modifier
                            .padding(horizontal = Dimen.Medium)
                            .background(
                                color = MaterialTheme.colorScheme.surfaceContainerLow,
                                shape = MaterialTheme.shapes.large
                            )
                            .padding(horizontal = Dimen.Medium)
                            .padding(start = Dimen.Medium)

                    )
                }
                items(filteredItems) {
                    DropdownMenuItem(
                        text = { Text(it.name.toTitleName()) },
                        onClick = {
                            state.setTextAndPlaceCursorAtEnd(it.name)
                            onItemSelected(it)
                            state.clearText()
                        },
                        contentPadding = PaddingValues(horizontal = Dimen.Large)
                    )
                }
            }
        }

    }
}