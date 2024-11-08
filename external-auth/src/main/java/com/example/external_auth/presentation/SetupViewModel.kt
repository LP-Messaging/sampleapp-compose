package com.example.external_auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.external_auth.presentation.effects.SetupEffect
import com.example.external_auth.presentation.state.AuthType
import com.example.external_auth.presentation.state.SetupState
import com.example.external_auth.presentation.state.UnauthType
import com.example.external_auth.presentation.state.UserCampaignInfo
import com.example.external_auth.presentation.state.dialog.SDEDialogParams
import com.example.external_auth.presentation.utils.asAuthParams
import com.example.external_auth.presentation.utils.asConsumerCampaignInfo
import com.example.external_auth.presentation.utils.asMonitoringParams
import com.example.external_auth.presentation.utils.asUserCampaign
import com.liveperson.common.Failure
import com.liveperson.common.Success
import com.liveperson.common.domain.interactor.LivePersonAuthInteractor
import com.liveperson.common.domain.interactor.LivePersonMonitoringInteractor
import com.liveperson.common.utils.getMessageOrDefault
import com.liveperson.monitoring.sdk.responses.LPEngagementResponse
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class SetupViewModel(
    private val messagingInteractor: LivePersonAuthInteractor,
    private val monitoringInteractor: LivePersonMonitoringInteractor,
) : ViewModel() {

    companion object {
        private const val APP_ID = "com.liveperson.sample.app"
    }

    private val _state = MutableStateFlow(SetupState())
    private val _effects = Channel<SetupEffect>()

    val state: StateFlow<SetupState> = _state.asStateFlow()

    val effects: Flow<SetupEffect> = _effects.receiveAsFlow()

    override fun onCleared() {
        _effects.close()
        super.onCleared()
    }

    fun onBrandIdChanged(brandId: String) {
        _state.update { it.copy(brandId = brandId) }
    }

    fun onAppInstallIdChanged(appInstallId: String) {
        _state.update { it.copy(appInstallId = appInstallId) }
    }

    fun onAuthTypeChanged(authType: AuthType?) {
        _state.update { it.copy(authType = authType) }
    }

    fun onCampaignInfoChanged(userCampaignInfo: UserCampaignInfo) {
        _state.update { it.copy(userCampaignInfo = userCampaignInfo) }
    }

    fun changeSDEDialogAppearance(shouldShow: Boolean) {
        _state.update { it.copy(showSDEDialog = shouldShow) }
    }

    fun initialize() {
        viewModelScope.launch {
            val state = _state.first()
            val brandId = state.brandId
            val appInstallId = state.appInstallId
            when (val result = messagingInteractor.initialize(brandId, APP_ID, appInstallId)) {
                is Success -> {
                    onAuthTypeChanged(UnauthType)
                }
                is Failure -> {
                    val message = result.data.getMessageOrDefault("Failed to initialize")
                    _effects.send(SetupEffect.FailureEffect(message))
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            val state = _state.first()
            val brandId = state.brandId
            when (messagingInteractor.logout(brandId, APP_ID)) {
                is Success -> {
                    onAuthTypeChanged(null)
                    onCampaignInfoChanged(UserCampaignInfo())
                }
                is Failure -> {
                    _effects.send(SetupEffect.FailureEffect("Failed to logout"))
                }
            }
        }
    }

    fun getEngagement(sdeDialogParams: SDEDialogParams) {
        viewModelScope.launch {
            val authParams = _state.first().authType?.asAuthParams() ?: return@launch
            val identities = listOf(sdeDialogParams.consumerId to "")
            val monitoringParams = sdeDialogParams.asMonitoringParams()
            val result = monitoringInteractor.getEngagement(identities, monitoringParams, authParams)
            when (result) {
                is Success<LPEngagementResponse> -> {
                    onCampaignInfoChanged(result.data.asUserCampaign())
                }
                is Failure<Throwable> -> {
                    val message = result.data.getMessageOrDefault("Failed to initialize")
                    _effects.send(SetupEffect.FailureEffect(message))
                }
            }
        }
    }

    fun showConversation() {
        viewModelScope.launch {
            val effect = _state.first().run {
                val params = authType!!.asAuthParams()
                val details = userCampaignInfo.asConsumerCampaignInfo()
                SetupEffect.NavigateToConversationEffect(brandId, APP_ID, appInstallId, params, details)
            }
            _effects.send(effect)
        }
    }
}