package com.example.external_auth.presentation.auth.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.external_auth.R

@Composable
fun AuthButtons(authenticate: () -> Unit, logout: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = authenticate,
        ) {
            Text(text = stringResource(R.string.text_authenticate))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(Color.Gray),
            onClick = logout,
        ) {
            Text(text = stringResource(R.string.text_logout))
        }
    }
}

@Preview
@Composable
fun AuthButtonsPreview() {
    AuthButtons(
        authenticate = { },
        logout = { }
    )
}