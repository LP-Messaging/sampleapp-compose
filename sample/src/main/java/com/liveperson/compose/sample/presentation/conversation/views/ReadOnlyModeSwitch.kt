package com.liveperson.compose.sample.presentation.conversation.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.liveperson.compose.sample.R

@Composable
fun ReadOnlyModeSwitch(
    isReadOnlyMode: Boolean,
    changeReadOnlyMode: (Boolean) -> Unit
) {
    Row(
        Modifier
            .clip(RoundedCornerShape(12.dp))
            .padding(start = 8.dp)
            .background(Color.LightGray, RoundedCornerShape(12.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.padding(6.dp),
            text = stringResource(id = R.string.text_change_readonly_mode)
        )
        Switch(
            checked = isReadOnlyMode,
            onCheckedChange = changeReadOnlyMode
        )
    }
}
@Preview
@Composable
fun ReadOnlyModeSwitchPreview() {
    var isReadOnlyMode by remember { mutableStateOf(true) }

    Column {
        ReadOnlyModeSwitch(
            isReadOnlyMode = isReadOnlyMode,
            changeReadOnlyMode = { newValue ->
                // Handle the change of read-only mode here
                isReadOnlyMode = newValue
            }
        )
    }
}

