package com.example.external_auth.presentation

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.external_auth.R
import com.example.external_auth.presentation.effects.SetupEffect
import com.example.external_auth.presentation.state.SetupState
import com.example.external_auth.presentation.views.AuthTypeInputView
import com.example.external_auth.presentation.views.AuthTypeView
import com.example.external_auth.presentation.views.CampaignInfoView
import com.example.external_auth.presentation.views.DefaultButton
import com.example.external_auth.presentation.views.DefaultInput
import com.example.external_auth.presentation.views.dialog.SDEDialog
import com.liveperson.common.domain.AuthParams
import com.liveperson.common.domain.ConsumerCampaignInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetupScreen(
    modifier: Modifier = Modifier,
    onShowConversation: (brandId: String, appId: String, appInstallId: String, authParams: AuthParams, campaign: ConsumerCampaignInfo) -> Unit
) {

    val viewModel: SetupViewModel = koinViewModel()
    val state: SetupState by viewModel.state.collectAsState(Dispatchers.Main.immediate)

    Column(modifier = modifier) {

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = stringResource(id = R.string.title_brand_data))

        DefaultInput(
            value = state.brandId,
            onValueChanged = viewModel::onBrandIdChanged,
            modifier = Modifier.fillMaxWidth(),
            hintRes = R.string.hint_brand_id
        )

        DefaultInput(
            value = state.appInstallId,
            onValueChanged = viewModel::onAppInstallIdChanged,
            modifier = Modifier.fillMaxWidth(),
            hintRes = R.string.hint_app_install_id
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DefaultButton(
                title = stringResource(id = R.string.button_initialize),
                onClick = viewModel::initialize,
                modifier = Modifier.weight(0.5f)
            )
            AnimatedVisibility(visible = state.authType != null, modifier = Modifier.weight(0.5f)) {
                DefaultButton(
                    title = stringResource(id = R.string.button_log_out),
                    onClick = viewModel::logout,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        val authType = state.authType
        AnimatedVisibility(visible = authType != null) {
            if (authType != null) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.height(20.dp))

                    AuthTypeView(
                        authType = authType,
                        onAuthTypeChanged = viewModel::onAuthTypeChanged,
                        modifier = Modifier.fillMaxWidth()
                    )
                    AuthTypeInputView(
                        authType = authType,
                        onAuthTypeChanged = viewModel::onAuthTypeChanged,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        AnimatedVisibility(visible = state.appInstallId.isNotBlank()) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.height(20.dp))
                CampaignInfoView(
                    campaignInfo = state.userCampaignInfo,
                    onCampaignInfoChanged = viewModel::onCampaignInfoChanged,
                    modifier = Modifier.fillMaxWidth()
                )

                AnimatedVisibility(visible = state.authType != null) {
                    DefaultButton(
                        title = stringResource(id = R.string.button_get_enagement),
                        onClick = { viewModel.changeSDEDialogAppearance(true) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        AnimatedVisibility(visible = authType != null) {
            Spacer(modifier = Modifier.height(20.dp))
            DefaultButton(
                title = stringResource(id = R.string.button_show_conversaion),
                onClick = viewModel::showConversation,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    if (state.showSDEDialog) {
        SDEDialog(
            onSDESubmitted = viewModel::getEngagement,
            onDismissRequest = { viewModel.changeSDEDialogAppearance(false) },
            modifier = Modifier.fillMaxWidth()
        )
    }

    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        viewModel.effects.collectLatest {
            when (it) {
                is SetupEffect.FailureEffect -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
                is SetupEffect.NavigateToConversationEffect -> {
                    onShowConversation(it.brandId, it.appId, it.appInstallId, it.authParams, it.campaign)
                }
            }
        }
    }
}