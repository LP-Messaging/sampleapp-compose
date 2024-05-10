package com.example.external_auth.presentation.auth.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.external_auth.R
import com.example.external_auth.presentation.auth.dto.CodeCredentials
import com.example.external_auth.presentation.auth.dto.Credentials
import com.example.external_auth.presentation.auth.dto.ImplicitCredentials

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AuthenticationTypeLayout(
    modifier: Modifier,
    credentials: Credentials,
    onCredentialChanged: (Credentials) -> Unit,
) {
    Text(text = stringResource(R.string.title_choose_authentication_flow))
    SingleChoiceSegmentedButtonRow(modifier = modifier) {
        SegmentedButton(
            shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2),
            onClick = {
                if (credentials !is CodeCredentials) {
                    onCredentialChanged(CodeCredentials(""))
                }
            },
            selected = credentials is CodeCredentials
        ) {
            Text(stringResource(R.string.text_code))
        }
        SegmentedButton(
            shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2),
            onClick = {
                if (credentials !is ImplicitCredentials) {
                    onCredentialChanged(ImplicitCredentials(""))
                }
            },
            selected = credentials is ImplicitCredentials
        ) {
            Text(stringResource(R.string.text_implicit))
        }
    }
}