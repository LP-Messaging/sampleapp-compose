package com.liveperson.compose.sample.presentation.conversation.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.liveperson.compose.sample.R

@Composable
fun MessageBox(
    input: String,
    onInputChanged: (String) -> Unit,
    onSendMessageClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        value = input,
        onValueChange = onInputChanged,
        trailingIcon = {
            TextButton(onClick = {
                onSendMessageClick(input)
            }) {
                Text(
                    text = stringResource(id = R.string.text_send),
                    modifier = Modifier.wrapContentSize()
                )
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
