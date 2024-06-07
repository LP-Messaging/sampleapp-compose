package com.example.external_auth.presentation.auth.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.external_auth.R
import com.example.external_auth.presentation.auth.dto.CodeCredentials
import com.example.external_auth.presentation.auth.dto.Credentials
import com.example.external_auth.presentation.auth.dto.ImplicitCredentials
import com.example.external_auth.presentation.auth.dto.UnAuthCredentials
import com.example.external_auth.presentation.auth.dto.UserEngagementDetails

@Composable
internal fun AuthFlow(
    credentials: Credentials,
    onCredentialChanged: (Credentials) -> Unit,
    onMonitoringApiClicked: () -> Unit
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

            is UnAuthCredentials -> {
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    AuthField(
                        value = credentials.appInstallId,
                        onValueChange = {
                            onCredentialChanged(credentials.copy(appInstallId = it))
                        },
                        label = "App Install id",
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(onClick = { onMonitoringApiClicked() }) {
                        Text(text = "Monitoring Api")
                    }
                }

                EngagementDetailsForm(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    details = credentials.engagementDetails,
                    onEngagementDetailsChanged = {
                        onCredentialChanged(credentials.copy(engagementDetails = it))
                    }
                )
            }
        }
    }
}

@Composable
private fun EngagementDetailsForm(
    modifier: Modifier,
    details: UserEngagementDetails,
    onEngagementDetailsChanged: (UserEngagementDetails) -> Unit
) {
    Column(modifier = modifier) {
        AuthField(
            value = details.campaignId,
            onValueChange = {
                onEngagementDetailsChanged(details.copy(campaignId = it))
            },
            label = "Campaign ID"
        )
        AuthField(
            value = details.engagementId,
            onValueChange = {
                onEngagementDetailsChanged(details.copy(engagementId = it))
            },
            label = "Engagement ID"
        )
        AuthField(
            value = details.sessionId,
            onValueChange = {
                onEngagementDetailsChanged(details.copy(sessionId = it))
            },
            label = "Session ID"
        )
        AuthField(
            value = details.contextId,
            onValueChange = {
                onEngagementDetailsChanged(details.copy(sessionId = it))
            },
            label = "Context ID"
        )
        AuthField(
            value = details.visitorId,
            onValueChange = {
                onEngagementDetailsChanged(details.copy(visitorId = it))
            },
            label = "Visitor ID"
        )
    }
}