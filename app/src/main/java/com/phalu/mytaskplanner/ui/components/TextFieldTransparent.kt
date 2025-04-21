package com.phalu.mytaskplanner.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun TextFieldTransparent(
    textValue: String,
    onTextChanged: (String) -> Unit,
    label: String,
    textColor: Color = MaterialTheme.colorScheme.onPrimary,
    imeAction: ImeAction = ImeAction.Done,
    shape: Shape = RoundedCornerShape(8.dp),
    modifier: Modifier = Modifier

) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    TextField(
        value = textValue,
        onValueChange = onTextChanged,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .border(1.dp, textColor, shape)// Apply border with shape
            .background(Color.Transparent, shape),
        label = {
            Label(
                text = label,
                textStyle = MaterialTheme.typography.labelSmall
            )
        },
        colors = TextFieldDefaults.colors(
            disabledTextColor = textColor,
            focusedTextColor = textColor,
            unfocusedTextColor = textColor,
            cursorColor = textColor,
            focusedLabelColor = textColor,
            disabledLabelColor = textColor,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = imeAction // Set the IME action to "Done"
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide() // Hide the keyboard when "Done" is pressed
                focusManager.clearFocus() // Optionally, clear focus to hide the keyboard
            },
            onNext = {
                focusManager.moveFocus(FocusDirection.Next)
            }
        )
    )
}