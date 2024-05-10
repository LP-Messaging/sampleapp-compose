package com.liveperson.compose.sample.presentation.conversation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ActionButton(
    onSendMessageClick: (String) -> Unit,
    buttonText: String,
) {
    Button(onClick = {
        onSendMessageClick(buttonText)
    }) {
        Text(text = buttonText)
    }
    Spacer(modifier = Modifier.width(8.dp))
}

@Preview
@Composable
fun PreviewConversationScreen() {
    Row {
        ActionButton({}, "Send Text1")
        ActionButton({}, "Send Text2")
        ActionButton({}, "Send Text3")
    }
}
