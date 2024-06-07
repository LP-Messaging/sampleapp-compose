package com.example.external_auth.presentation.auth.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.external_auth.R

@Composable
internal fun AuthField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier.fillMaxWidth()
) {
    OutlinedTextField(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(text = label)
        }
    )
}

@Preview
@Composable
internal fun AuthFieldPreview() {
    var textValue by remember { mutableStateOf("") }
    AuthField(
        value = textValue,
        onValueChange = { textValue = it },
        label = stringResource(id = R.string.text_implicit_token)
    )

}