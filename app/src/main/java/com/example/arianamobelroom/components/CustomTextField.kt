package com.example.arianamobelroom.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    text: String,
    label: String,
    isSingleLine: Boolean,
    onTextChange: (String) -> Unit,
    imeAction: ImeAction,
    keyboardActions: KeyboardActions,
    iconId: Int,
    iconModifier: Modifier = Modifier
) {
    TextField(
        modifier = modifier,
        value = text,
        onValueChange = { onTextChange(it) },
        label = { Text(label) },
        singleLine = isSingleLine,
        leadingIcon = {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = "Tf Icon",
                modifier = iconModifier
            )
        },
        keyboardOptions = KeyboardOptions(imeAction = imeAction, keyboardType = KeyboardType.Text),
        keyboardActions = keyboardActions
    )

}