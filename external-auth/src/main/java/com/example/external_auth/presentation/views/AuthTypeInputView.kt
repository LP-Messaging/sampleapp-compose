package com.example.external_auth.presentation.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.external_auth.R
import com.example.external_auth.presentation.state.AuthType
import com.example.external_auth.presentation.state.CodeType
import com.example.external_auth.presentation.state.ImplicitType
import com.example.external_auth.presentation.state.UnauthType
import com.example.external_auth.presentation.views.DefaultInput

@Composable
fun AuthTypeInputView(
    authType: AuthType,
    onAuthTypeChanged: (AuthType) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        when (authType) {
            is CodeType -> {
                DefaultInput(
                    value = authType.code,
                    onValueChanged = { onAuthTypeChanged(CodeType(it))},
                    hintRes = R.string.hint_code,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            is ImplicitType -> {
                DefaultInput(
                    value = authType.token,
                    onValueChanged = { onAuthTypeChanged(authType.copy(token = it))},
                    hintRes = R.string.hint_token,
                    modifier = Modifier.fillMaxWidth()
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = authType.isEncrypted,
                        onCheckedChange = { onAuthTypeChanged(authType.copy(isEncrypted = it))}
                    )
                    Text(
                        text = stringResource(id = R.string.text_encrypted),
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Start
                    )
                }
            }
            UnauthType -> {
                Row { /* Show nothing */ }
            }
        }
    }
}