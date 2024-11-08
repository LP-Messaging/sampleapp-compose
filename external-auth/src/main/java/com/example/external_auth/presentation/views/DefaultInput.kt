package com.example.external_auth.presentation.views

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

@Composable
fun DefaultInput(
    value: String,
    onValueChanged: (String) -> Unit,
    hintRes: Int,
    modifier: Modifier = Modifier,
    maxLines: Int = 1
) {
    val context = LocalContext.current
    val hint = remember(hintRes) {
        context.getString(hintRes)
    }
    DefaultInput(
        value = value,
        onValueChanged = onValueChanged,
        hint = hint,
        modifier = modifier,
        maxLines = maxLines
    )
}

@Composable
fun DefaultInput(
    value: String,
    onValueChanged: (String) -> Unit,
    hint: String? = null,
    modifier: Modifier = Modifier,
    maxLines: Int = 1
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChanged,
        label = {
            if (hint != null) {
                Text(text = hint)
            }
        },
        maxLines = maxLines
    )
}