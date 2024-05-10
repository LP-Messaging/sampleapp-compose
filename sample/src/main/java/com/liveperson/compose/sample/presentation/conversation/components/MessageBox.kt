package com.liveperson.compose.sample.presentation.conversation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Column as Column

@Composable
fun MessageBox(
    input: String,
    onInputChanged: (String) -> Unit,
    onSendMessageClick: (String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .imePadding(),
        shape = RoundedCornerShape(12.dp),
        value = input,
        onValueChange = onInputChanged,
        trailingIcon = {
            TextButton(onClick = {
                onSendMessageClick(input)
            }) {
                Text("Send")
            }
        }
    )
}
@Preview
@Composable
fun MessageBoxPreview() {
    var input by remember { mutableStateOf("") }

    Column {
        MessageBox(
            input = input,
            onInputChanged = { input = it },
            onSendMessageClick = { }
        )
    }
}
