package com.example.external_auth.presentation.views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun DefaultButton(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    maxLines: Int = 1
) {
    Button(onClick = onClick, modifier = modifier) {
        Text(text = title, modifier = Modifier.fillMaxWidth(), maxLines = maxLines, textAlign = TextAlign.Center)
    }
}