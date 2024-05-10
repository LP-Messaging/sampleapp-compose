package com.example.external_auth.presentation.auth.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.external_auth.R
import com.example.external_auth.presentation.auth.dto.CodeCredentials
import com.example.external_auth.presentation.auth.dto.Credentials
import com.example.external_auth.presentation.auth.dto.ImplicitCredentials

@Composable
internal fun AuthFlow(
    credentials: Credentials,
    onCredentialChanged: (Credentials) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        when (credentials) {
            is CodeCredentials -> {
                AuthField(
                    value = credentials.code,
                    onValueChange = {
                        onCredentialChanged(credentials.copy(code = it))
                    },
                    label = stringResource(R.string.text_auth_code)
                )
            }

            is ImplicitCredentials -> {
                AuthField(
                    value = credentials.token,
                    onValueChange = {
                        onCredentialChanged(credentials.copy(token = it))
                    },
                    label = stringResource(R.string.text_implicit_token)
                )
            }

        }
    }
}