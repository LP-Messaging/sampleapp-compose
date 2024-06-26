package com.example.external_auth.presentation.auth

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.external_auth.R
import com.example.external_auth.presentation.auth.components.AuthButtons
import com.example.external_auth.presentation.auth.components.AuthField
import com.example.external_auth.presentation.auth.components.AuthFlow
import com.example.external_auth.presentation.auth.components.AuthenticationTypeLayout
import com.example.external_auth.presentation.auth.components.MonitoringApiDialog
import com.example.external_auth.presentation.auth.dto.OpenConversationScreenEffect
import com.example.external_auth.presentation.auth.dto.ShowToastMessageEffect
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

const val AuthScreenPath = "auth"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    modifier: Modifier,
    onOpenConversationScreen: (String) -> Unit = {}
) {
    val viewModel: AuthViewModel = koinViewModel()

    val brandId by viewModel.brandId.collectAsState(Dispatchers.Main.immediate)

    val credentials by viewModel.credentials.collectAsState(Dispatchers.Main.immediate)

    val userMonitoringParams by viewModel.userMonitoringParams.collectAsState(Dispatchers.Main.immediate)

    val context = LocalContext.current

    var showDialog by rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.authEffects.collectLatest {
            when (it) {
                is OpenConversationScreenEffect -> onOpenConversationScreen(it.brandId)
                is ShowToastMessageEffect -> Toast.makeText(context, it.resId, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    Column(
        modifier = modifier.padding(horizontal = 8.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        AuthField(
            value = brandId,
            onValueChange = viewModel::setBrandId,
            label = stringResource(R.string.text_brand_id)
        )

        Spacer(modifier = Modifier.height(24.dp))

        AuthenticationTypeLayout(
            modifier = Modifier.fillMaxWidth(),
            credentials = credentials,
            onCredentialChanged = viewModel::setCredentials,
        )
        Spacer(modifier = Modifier.height(12.dp))

        AuthFlow(
            credentials = credentials,
            onCredentialChanged = viewModel::setCredentials,
            onMonitoringApiClicked = {
                showDialog = true
            }
        )
        Spacer(modifier = Modifier.height(24.dp))
        Spacer(modifier = Modifier.weight(1f))

        AuthButtons(
            authenticate = {
                viewModel.authenticate()
            },
            logout = {
                viewModel.logout()
            }
        )

        if (showDialog) {
            MonitoringApiDialog(
                params = userMonitoringParams,
                onMonitoringParamsChanged = viewModel::setMonitoringParams,
                onDismissRequest = { showDialog = false },
                onGetEngagementClicked = {
                    viewModel.requestEngagementDetails()
                },
                onSendSdeClicked = {
                    viewModel.sendSde()
                }
            )
        }
    }
}