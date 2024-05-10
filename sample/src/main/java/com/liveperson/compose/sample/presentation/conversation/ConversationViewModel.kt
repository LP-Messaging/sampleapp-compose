package com.liveperson.compose.sample.presentation.conversation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liveperson.common.data.liveperson.toAuthParams
import com.liveperson.common.domain.interactor.LPHybridCommandsInteractor
import com.liveperson.common.domain.repository.AuthParamsRepository
import com.liveperson.compose.common_ui.wrapper.LPArguments
import com.liveperson.infra.ConversationViewParams
import com.liveperson.messaging.hybrid.commands.messaging.UrlData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ConversationViewModel(
    private val hybridCommandsInteractor: LPHybridCommandsInteractor,
    private val authParamsRepository: AuthParamsRepository
) : ViewModel() {

    companion object {
        private const val SDK_MAKER_FCM_APP_ID: String = "com.liveperson.messaging.test"
    }

    private val _currentBrandId: MutableStateFlow<String> = MutableStateFlow("")

    val lpArguments = _currentBrandId.filter { it.isNotBlank() }
        .map { brandId ->
            val appId = SDK_MAKER_FCM_APP_ID
            val credentials = authParamsRepository.getCredentialsForBrand(brandId) ?: return@map null
            val conversationParams = ConversationViewParams(true)
            LPArguments(brandId, appId, credentials.toAuthParams(), conversationParams)
        }
        .flowOn(Dispatchers.IO)
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

    fun sendMessage(message: String) {
        viewModelScope.launch(Dispatchers.IO) {
            hybridCommandsInteractor.sendMessage(message)
        }
    }

    fun sendMessageWithUrl(message: String, url: UrlData) {
        viewModelScope.launch(Dispatchers.IO) {
            hybridCommandsInteractor.sendMessageWithUrl(message = message, urlData = url)
        }
    }

    fun openCamera() {
        viewModelScope.launch { hybridCommandsInteractor.openCamera() }
    }

    fun openGallery() {
        viewModelScope.launch { hybridCommandsInteractor.openGallery() }
    }

    fun shareFile() {
        viewModelScope.launch { hybridCommandsInteractor.fileSharingOpenFileChooser() }
    }

    fun changeReadOnlyMode(isReadOnly: Boolean) {
        viewModelScope.launch { hybridCommandsInteractor.changeReadOnlyMode(isReadOnly = isReadOnly) }
    }

    fun showConversation(brandId: String) {
        _currentBrandId.value = brandId
    }
}