package com.example.external_auth.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.external_auth.presentation.auth.dto.AuthScreenEffect
import com.example.external_auth.presentation.auth.dto.CodeCredentials
import com.example.external_auth.presentation.auth.dto.Credentials
import com.example.external_auth.presentation.auth.dto.ImplicitCredentials
import com.example.external_auth.presentation.auth.dto.OpenConversationScreenEffect
import com.liveperson.common.domain.CodeParams
import com.liveperson.common.domain.ImplicitJWEParams
import com.liveperson.common.domain.ImplicitParams
import com.liveperson.common.domain.interactor.LivePersonAuthInteractor
import com.liveperson.common.domain.repository.AuthParamsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

internal class AuthViewModel(
    private val livePersonAuthInteractor: LivePersonAuthInteractor,
    private val authParamsManager: AuthParamsRepository
): ViewModel() {

    companion object {
        private const val SDK_SAMPLE_APP_ID = "com.liveperson.sdksample"
    }

    private val authMap: MutableMap<Class<out Credentials>, Credentials> = mutableMapOf()

    private val _effectsChannel = Channel<AuthScreenEffect>()
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

    fun authenticate() {
        viewModelScope.launch(Dispatchers.Main) {
            val brandId = _brandId.first()
            val credentials =  _credentials.first()
            if (brandId.isEmpty() || credentials.isEmpty) {
                return@launch
            }
            val params = when(credentials){
                is CodeCredentials -> {
                    CodeParams(credentials = credentials.code)
                }
                is ImplicitCredentials -> {
                    ImplicitParams(credentials = credentials.token)
                }
            }
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

    val authEffects: Flow<AuthScreenEffect> = _effectsChannel.receiveAsFlow()

    private val Credentials.isEmpty
        get() = when (this) {
            is CodeCredentials -> code.isEmpty()
            is ImplicitCredentials -> token.isEmpty()
        }

    private suspend fun AuthParamsRepository.getCredentials(brandId: String): Credentials {
        return when (val params = getCredentialsForBrand(brandId)) {
            is CodeParams -> CodeCredentials(params.credentials)
            is ImplicitParams -> ImplicitCredentials(params.credentials)
            is ImplicitJWEParams -> ImplicitCredentials(params.credentials)
            else -> CodeCredentials("")
        }
    }
}