package com.example.external_auth.presentation.auth

import android.util.Log
import com.liveperson.monitoring.sdk.MonitoringParams

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.external_auth.presentation.auth.dto.AuthScreenEffect
import com.example.external_auth.presentation.auth.dto.CodeCredentials
import com.example.external_auth.presentation.auth.dto.Credentials
import com.example.external_auth.presentation.auth.dto.ImplicitCredentials
import com.example.external_auth.presentation.auth.dto.OpenConversationScreenEffect
import com.example.external_auth.presentation.auth.dto.UnAuthCredentials
import com.example.external_auth.presentation.auth.dto.UserEngagementDetails
import com.example.external_auth.presentation.auth.dto.UserMonitoringParams
import com.liveperson.common.Failure
import com.liveperson.common.Success
import com.liveperson.common.domain.AuthParams
import com.liveperson.common.domain.CodeParams
import com.liveperson.common.domain.EngagementDetails
import com.liveperson.common.domain.ImplicitJWEParams
import com.liveperson.common.domain.ImplicitJWTParams
import com.liveperson.common.domain.UnAuthParams
import com.liveperson.common.domain.interactor.LivePersonAuthInteractor
import com.liveperson.common.domain.interactor.LivePersonMonitoringInteractor
import com.liveperson.common.domain.repository.AuthParamsRepository
import com.liveperson.monitoring.sdk.responses.LPEngagementResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.json.JSONArray

internal class AuthViewModel(
    private val livePersonAuthInteractor: LivePersonAuthInteractor,
    private val livePersonMonitoringInteractor: LivePersonMonitoringInteractor,
    private val authParamsManager: AuthParamsRepository
): ViewModel() {

    companion object {
        private const val SDK_SAMPLE_APP_ID = "com.liveperson.sdksample"
    }

    private val authMap: MutableMap<Class<out Credentials>, Credentials> = mutableMapOf()

    private val _effectsChannel = Channel<AuthScreenEffect>()
    private val _monitoringParams = MutableStateFlow(UserMonitoringParams("", "", "", ""))
    private val _brandId = MutableStateFlow("")
    private val _credentials = MutableStateFlow<Credentials>(CodeCredentials(""))

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val activeBrandId = authParamsManager.getLatestBrandId()
            if (!activeBrandId.isNullOrEmpty()) {
                setBrandId(activeBrandId)
                setCredentials(authParamsManager.getCredentials(activeBrandId))
            }
        }
    }

    fun setBrandId(brandId: String) {
        _brandId.value = brandId
    }

    fun setCredentials(credentials: Credentials) {
        val currentCredentials = if (credentials.isEmpty) {
            authMap[credentials.javaClass] ?: credentials
        } else {
            credentials
        }
        _credentials.value = currentCredentials
        authMap[credentials.javaClass] = currentCredentials
    }

    fun setMonitoringParams(monitoringParams: UserMonitoringParams) {
        _monitoringParams.value = monitoringParams
    }

    fun sendSde() {

    }

    fun requestEngagementDetails() {
        viewModelScope.launch(Dispatchers.Main) {
            val brandId = _brandId.value
            val appInstallId = (_credentials.value as? UnAuthCredentials)?.appInstallId ?: ""
            when (val result = livePersonAuthInteractor.initialize(brandId, SDK_SAMPLE_APP_ID, appInstallId)) {
                is Success<*> -> {
                    Log.e("AUTH", "initialize() ${result.data}")
                }
                is Failure<Throwable> -> {
                    Log.e("AUTH", "initialize() ${result.data.stackTraceToString()}")
                    return@launch
                }
            }

            val identities = listOf("" to null)
            val monitoringParams = buildMonitoringParams()
            when (val result = livePersonMonitoringInteractor.getEngagement(identities, monitoringParams)) {
                is Success<LPEngagementResponse> -> {
                    _credentials.update {
                        if (it is UnAuthCredentials) {
                            it.copy(engagementDetails = result.data.asUserEngagementDetails())
                        } else {
                            it
                        }
                    }
                }
                is Failure<Throwable> -> {
                    Log.e("AUTH", "requestEngagementDetails() ${result.data.stackTraceToString()}")
                }
            }
        }
    }

    fun authenticate() {
        viewModelScope.launch(Dispatchers.Main) {
            val brandId = _brandId.first()
            val credentials =  _credentials.first()
            if (brandId.isEmpty() || credentials.isEmpty) {
                return@launch
            }
            val params = credentials.asAuthPrams()
            val latestBrandId = authParamsManager.getLatestBrandId()
            val latestAuthParams = authParamsManager.getCredentialsForBrand(latestBrandId ?: "")

            if (latestBrandId != _brandId.value || latestAuthParams != params) {
                if (!latestBrandId.isNullOrEmpty()) {
                    livePersonAuthInteractor.logout(latestBrandId, SDK_SAMPLE_APP_ID)
                }
            }

            authParamsManager.setLatestBrandId(brandId)
            authParamsManager.saveCredentials(brandId, params)

            _effectsChannel.send(OpenConversationScreenEffect(brandId = brandId))
        }
    }
    fun logout() {
        viewModelScope.launch(Dispatchers.Main) {
            val latestBrandId = authParamsManager.getLatestBrandId()
            if (!latestBrandId.isNullOrEmpty()) {
                livePersonAuthInteractor.logout(latestBrandId, SDK_SAMPLE_APP_ID)
            }
            authParamsManager.clearData()
        }
    }

    val brandId = _brandId.asStateFlow()

    val credentials = _credentials.asStateFlow()

    val userMonitoringParams = _monitoringParams.asStateFlow()

    val authEffects: Flow<AuthScreenEffect> = _effectsChannel.receiveAsFlow()

    private val Credentials.isEmpty
        get() = when (this) {
            is CodeCredentials -> code.isEmpty()
            is ImplicitCredentials -> token.isEmpty()
            is UnAuthCredentials -> appInstallId.isEmpty()
        }

    private suspend fun AuthParamsRepository.getCredentials(brandId: String): Credentials {
        return when (val params = getCredentialsForBrand(brandId)) {
            is CodeParams -> CodeCredentials(params.credentials)
            is ImplicitJWTParams -> ImplicitCredentials(params.credentials)
            is ImplicitJWEParams -> ImplicitCredentials(params.credentials)
            else -> CodeCredentials("")
        }
    }

    private fun Credentials.asAuthPrams(): AuthParams {
        return when (this) {
            is CodeCredentials -> {
                CodeParams(credentials = code)
            }
            is ImplicitCredentials -> {
                ImplicitJWTParams(credentials = token)
            }
            is UnAuthCredentials -> {
                UnAuthParams(
                    appInstallId,
                    EngagementDetails(
                        engagementDetails.campaignId,
                        engagementDetails.engagementId,
                        engagementDetails.sessionId,
                        engagementDetails.visitorId,
                        engagementDetails.contextId,
                    ),
                )
            }
        }
    }

    private suspend fun buildMonitoringParams(): MonitoringParams {
        val entryPoints = runCatching { JSONArray(_monitoringParams.first().entryPoints) }.getOrNull()
        val pageId = _monitoringParams.first().pageId.takeIf { it.isNotEmpty() }
        return MonitoringParams(pageId = pageId, entryPoints = entryPoints)
    }

    private fun LPEngagementResponse.asUserEngagementDetails(): UserEngagementDetails {
        val details = engagementDetailsList?.firstOrNull()
        return UserEngagementDetails(
            engagementId = details?.engagementId ?: "",
            sessionId = sessionId ?: "",
            campaignId = details?.campaignId ?: "",
            contextId = details?.contextId ?: "",
            visitorId = visitorId ?: ""
        )
    }
}